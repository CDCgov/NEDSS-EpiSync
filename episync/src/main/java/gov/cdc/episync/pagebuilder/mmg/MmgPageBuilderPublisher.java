package gov.cdc.episync.pagebuilder.mmg;

import gov.cdc.episync.framework.*;
import gov.cdc.episync.framework.EpisyncPublishResult.PublishResultCode;
import gov.cdc.episync.mmg.MmgDocument;
import gov.cdc.episync.mmg.MmgPublisher;
import gov.cdc.episync.mmg.MmgData;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service("mmg") @RequiredArgsConstructor
public class MmgPageBuilderPublisher extends MmgPublisher {
    private final MmgPageBuilderRouter router;

    private static final String QUESTION_OID = "2.16.840.1.114222.4.5.232";

    private static final String NA = "N/A";
    private static final String PHINQUESTION = "PHINQUESTION";
    private static final String LN = "LN";
    private static final String QUESTION_OID_SYSTEM_PHIN = "PHIN Questions";
    private static final String QUESTION_OID_SYSTEM_LN = "LOINC";

    Logger logger = LoggerFactory.getLogger(MmgPageBuilderRouter.class);

    @Override
    public EpisyncPublishResult publishDocument(MmgDocument document) throws EpisyncPublishException {
        try {
            EpisyncRouteResult routeResult = router.routeData(build(document));
            return new EpisyncPublishResult(PublishResultCode.SUCCESS, routeResult.getResultMessage());
        } catch (EpisyncRouterException e) {
            throw new EpisyncPublishException(e.getMessage());
        }
    }

    private MmgTemplate getTemplate(InputStream stream) {
        try {
            return Episync.getMapper().readValue(stream, MmgTemplate.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private EpisyncData<String, List<Dictionary<String, String>>> build(MmgDocument document) {
        MmgData<String, List<Dictionary<String, String>>> episyncData = new MmgData<>();
        Dictionary<String, String> tmpData = new Hashtable<>();

        MmgTemplate template = getTemplate(document.getJson());
        try {
            logger.info("Start building template {}", template.getName());

            tmpData.put("id", template.getId());
            tmpData.put("type", template.getType());
            tmpData.put("name", template.getName());
            tmpData.put("shortName", template.getShortName());
            tmpData.put("description", template.getDescription());
            tmpData.put("profileIdentifier", template.getProfileIdentifier());
            Optional.ofNullable(document.getUid()).ifPresent(uid -> tmpData.put("nbs_uid", String.valueOf(uid)));

            episyncData.put("template", Collections.singletonList(tmpData));

            List<Dictionary<String, String>> blocks = new ArrayList<>();
            for (MmgBlock b : template.getBlocks()) {
                List<MmgElement> elements = b.getElements();
                logger.info("Start building block {}", b.getName());
                blocks.add(build(b));

                List<Dictionary<String, String>> questions = new ArrayList<>(elements.size());
                if (elements.isEmpty()) {
                    logger.info("Successfully built block {}", b.getName());
                } else {
                    logger.info("Start building {} questions", elements.size());
                    for (MmgElement e : elements) {
                        questions.add(build(e));
                    }
                    logger.info("Successfully built block {} with {} questions", b.getName(), questions.size());
                }

                episyncData.put(b.getId(), questions);
            }
            episyncData.put(template.getId(), blocks);
        } catch (NullPointerException ex) {
            throw new NoSuchElementException("Cannot build template data for " + tmpData.get("name"));
        }

        try {
            List<Dictionary<String, String>> vsets = new ArrayList<>();
            for (MmgValueSet vs : template.getValueSets()) {
                logger.info("Building value set {}", vs.getValueSet().getValueSetCode());
                vsets.add(build(vs));

                List<MmgConcept> concepts = vs.getConcepts();
                List<Dictionary<String, String>> values = new ArrayList<>(concepts.size());

                logger.info("Start building {} concepts", concepts.size());
                for (MmgConcept c : concepts) {
                    values.add(build(c));
                }

                logger.info("Successfully built {} concepts for value set {}", values.size(), vs.getValueSet().getValueSetCode());
                episyncData.put(vs.getValueSet().getValueSetCode().toUpperCase(), values);
            }

            episyncData.put("values", vsets);
        } catch (NullPointerException ex) {
            throw new NoSuchElementException(ex.getMessage());
        }
        logger.info("Successfully built template {}", template.getName());

        return episyncData;
    }

    private Dictionary<String, String> build(MmgBlock b) {
        Dictionary<String, String> blockData = new Hashtable<>();
        blockData.put("id", b.getId());
        blockData.put("name", b.getName());
        blockData.put("type", b.getType());
        return blockData;
    }

    private Dictionary<String, String> build(MmgElement e) {
        Dictionary<String, String> qmData = new Hashtable<>();
        try {
            MmgElement.Hl7 hl7map = e.getMappings().getHl7v251();

            String identifier = hl7map.getIdentifier();

            String legacyCodeSystem = e.getLegacyCodeSystem();
            String codeSystem = e.getCodeSystem();
            codeSystem = getCodeSystem(codeSystem, legacyCodeSystem);

            qmData.put("identifier", getIdentifier(e));
            qmData.put("question_oid", QUESTION_OID);
            if (!codeSystem.isEmpty()) qmData.put("question_oid_system", codeSystem);
            qmData.put("data_type", e.getDataType());
            qmData.put("name", e.getName());
            qmData.put("desc_txt", e.getDescription());

            qmData.put("value_set_code", Optional.ofNullable(e.getValueSetCode()).orElse("").toUpperCase());
            if (!identifier.startsWith(NA)) qmData.put("identifier_nnd", identifier);
            qmData.put("required_nnd", hl7map.getUsage().equals("R") ? "R" : "O");
            qmData.put("data_type_nnd", hl7map.getDataType());
            qmData.put("hl7_segment_field", hl7map.getSegmentType() + "-" + hl7map.getFieldPosition() + "." +
                    (hl7map.getComponentPosition() > 0 ? hl7map.getComponentPosition() : "0"));

            Optional<MmgElement.DefaultValue> defaultVal = Optional.ofNullable(e.getDefaultValue());
            defaultVal.ifPresent(val -> qmData.put("default", val.getValue()));
        } catch (NullPointerException ex) {
            throw new NoSuchElementException("Cannot convert element data for element " +
                    qmData.get("identifier"));
        }
        return qmData;
    }

    private Dictionary<String, String> build(MmgValueSet vset) {
        MmgValueSet.MmgValueSetVersion ver = vset.getValueSetVersion();
        MmgValueSet.MmgValueSetInfo vs = vset.getValueSet();
        Dictionary<String, String> vsData = new Hashtable<>();
        vsData.put("id", vs.getValueSetId());
        vsData.put("oid", vs.getValueSetOid());
        vsData.put("name", vs.getValueSetName());
        vsData.put("code", vs.getValueSetCode().toUpperCase());
        vsData.put("status_date", vs.getStatusDate());
        vsData.put("definition", Optional.ofNullable(vs.getDefinitionText()).orElse(vs.getValueSetName()));
        vsData.put("authority", vs.getAssigningAuthorityId());
        vsData.put("version", String.valueOf(ver.getValueSetVersionNumber()));
        return vsData;
    }

    private Dictionary<String, String> build(MmgConcept c) {
        Dictionary<String, String> cData = new Hashtable<>();
        cData.put("id", c.getValueSetConceptId());
        cData.put("name", c.getCodeSystemConceptName());
        cData.put("status_date", c.getValueSetConceptStatusDate());
        cData.put("preferred", c.getCdcPreferredDesignation());
        cData.put("oid", c.getCodeSystemOid());
        cData.put("code", c.getConceptCode());
        cData.put("identifier", c.getHL70396Identifier());
        return cData;
    }

    private String getCodeSystem(String system, String legacy) {
        return system.equals(LN) ? QUESTION_OID_SYSTEM_LN
                : legacy.equals(PHINQUESTION) ? QUESTION_OID_SYSTEM_PHIN : "";
    }

    private String getIdentifier(MmgElement e) {
        MmgElement.Hl7 hl7map = e.getMappings().getHl7v251();
        String legacyIdentifier = hl7map.getLegacyIdentifier();
        return legacyIdentifier.isEmpty() || legacyIdentifier.startsWith(NA) ?
                hl7map.getIdentifier().replace("N/A: ", "").replaceAll("[\\s-]", "_")
                : legacyIdentifier;
    }
}

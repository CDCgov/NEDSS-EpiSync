package gov.cdc.episync.mmg.pagebuilder;

import gov.cdc.episync.framework.*;
import gov.cdc.episync.framework.EpisyncPublishResult.PublishResultCode;
import gov.cdc.episync.mmg.MMGDocument;
import gov.cdc.episync.mmg.MMGPublisher;
import gov.cdc.episync.mmg.MmgData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service("mmg") @RequiredArgsConstructor
public class MMGPageBuilderPublisher extends MMGPublisher {
    private final MMGPageBuilderRouter router;

    private static final String QUESTION_OID = "2.16.840.1.114222.4.5.232";
    private static final String QUESTION_OID_SYSTEM = "PHIN Questions";


    @Override
    public EpisyncPublishResult publishDocument(MMGDocument document) throws EpisyncPublishException {
        MmgTemplate template = getTemplate(document.getJson());

        try {
            EpisyncRouteResult routeResult = router.routeData(build(template));
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

    private EpisyncData<String, List<Dictionary<String, String>>> build(MmgTemplate template) {
        MmgData<String, List<Dictionary<String, String>>> episyncData = new MmgData<>();

        Dictionary<String, String> tmpData = new Hashtable<>();
        tmpData.put("id", template.getId());
        tmpData.put("type", template.getType());
        tmpData.put("name", template.getName());
        tmpData.put("shortName", template.getShortName());
        tmpData.put("description", template.getDescription());
        tmpData.put("profileIdentifier", template.getProfileIdentifier());

        episyncData.put("template", Collections.singletonList(tmpData));

        List<Dictionary<String, String>> blocks = new ArrayList<>();
        for (MmgBlock b: template.getBlocks()) {
            List<MmgElement> elements = b.getElements();
            blocks.add(build(b));

            List<Dictionary<String, String>> questions = new ArrayList<>(elements.size());
            for (MmgElement e: elements) {
                questions.add(build(e));
            }
            episyncData.put(b.getId(), questions);
        }
        episyncData.put(template.getId(), blocks);

        List<Dictionary<String, String>> vsets = new ArrayList<>();
        for (MmgValueSet vs: template.getValueSets()) {
            vsets.add(build(vs));

            List<MmgConcept> concepts = vs.getConcepts();
            List<Dictionary<String, String>> values = new ArrayList<>(concepts.size());
            for (MmgConcept c: concepts) {
                values.add(build(c));
            }
            episyncData.put(vs.getValueSet().getValueSetCode().toUpperCase(), values);
        }
        episyncData.put("values", vsets);

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
        qmData.put("identifier", e.getMappings().getHl7v251().getLegacyIdentifier());
        qmData.put("question_oid", QUESTION_OID);
        qmData.put("question_oid_system", QUESTION_OID_SYSTEM);
        qmData.put("data_type", e.getDataType());
        qmData.put("name", e.getName());
        qmData.put("short_name", e.getShortName());
        qmData.put("desc_txt", e.getDescription());

        qmData.put("value_set_code", Optional.ofNullable(e.getValueSetCode()).orElse("").toUpperCase());
        qmData.put("identifier_nnd", e.getMappings().getHl7v251().getIdentifier());
        qmData.put("required_nnd", e.getMappings().getHl7v251().getUsage().equals("R") ? "R" : "O");
        qmData.put("data_type_nnd", e.getMappings().getHl7v251().getDataType());
        qmData.put("hl7_segment_field", e.getMappings().getHl7v251().getSegmentType());
        return qmData;
    }

    private Dictionary<String, String> build(MmgValueSet vset) {
        MmgValueSet.MmgValueSetInfo vs = vset.getValueSet();
        Dictionary<String, String> vsData = new Hashtable<>();
        vsData.put("id", vs.getValueSetId());
        vsData.put("oid", vs.getValueSetOid());
        vsData.put("name", vs.getValueSetName());
        vsData.put("code", vs.getValueSetCode().toUpperCase());
        vsData.put("status_date", vs.getStatusDate());
        vsData.put("definition", vs.getDefinitionText());
        vsData.put("authority", vs.getAssigningAuthorityId());
        return vsData;
    }

    private Dictionary<String, String> build(MmgConcept c) {
        Dictionary<String, String> cData = new Hashtable<>();
        cData.put("id", c.getValueSetConceptId());
        cData.put("name", c.getCodeSystemConceptName());
        cData.put("status_date", c.getValueSetConceptStatusDate());
        cData.put("def_text", Optional.ofNullable(c.getValueSetConceptDefinitionText()).orElse(""));
        cData.put("preferred", c.getCdcPreferredDesignation());
        cData.put("oid", c.getCodeSystemOid());
        cData.put("code", c.getConceptCode());
        cData.put("identifier", c.getHL70396Identifier());

        return cData;
    }
}

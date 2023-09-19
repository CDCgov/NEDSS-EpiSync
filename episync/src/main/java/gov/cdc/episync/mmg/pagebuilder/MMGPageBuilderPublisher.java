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
            EpisyncRouteResult routeResult = router.routeData(buildData(template));
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

    private EpisyncData<String, List<Dictionary<String, String>>> buildData(MmgTemplate template) {
        MmgData<String, List<Dictionary<String, String>>> episyncData = new MmgData<>();
        Dictionary<String, List<Dictionary<String, String>>> data = episyncData.getData();

        Dictionary<String, String> tmpData = new Hashtable<>();
        tmpData.put("id", template.getId());
        tmpData.put("type", template.getType());
        tmpData.put("name", template.getName());
        tmpData.put("shortName", template.getShortName());
        tmpData.put("description", template.getDescription());
        tmpData.put("profileIdentifier", template.getProfileIdentifier());

        data.put("template", Collections.singletonList(tmpData));

        List<Dictionary<String, String>> blocks = new ArrayList<>();
        for (MmgBlock b: template.getBlocks()) {
            List<MmgElement> elements = b.getElements();
            blocks.add(buildBlockData(b));

            List<Dictionary<String, String>> questions = new ArrayList<>(elements.size());
            for (MmgElement e: elements) {
                questions.add(buildQuestionData(e));
            }
            data.put(b.getId(), questions);
            data.put(b.getGuideId(), blocks);
        }

        /*List<MmgElement> elements = template.getBlocks().stream().flatMap(b -> b.getElements().stream()).collect(Collectors.toList());
        List<Dictionary<String, String>> questions = new ArrayList<>(elements.size());
        for (MmgElement e: elements) {
            questions.add(buildQuestionData(e));
        }

        data.put("questions", questions);*/
        return episyncData;
    }

    private Dictionary<String, String> buildBlockData(MmgBlock b) {
        Dictionary<String, String> blockData = new Hashtable<>();
        blockData.put("id", b.getId());
        blockData.put("name", b.getName());
        blockData.put("type", b.getType());
        return blockData;
    }

    private Dictionary<String, String> buildQuestionData(MmgElement e) {
        Dictionary<String, String> qmData = new Hashtable<>();
        qmData.put("identifier", e.getMappings().getHl7v251().getLegacyIdentifier());
        qmData.put("question_oid", QUESTION_OID);
        qmData.put("question_oid_system", QUESTION_OID_SYSTEM);
        qmData.put("data_type", e.getDataType());
        qmData.put("name", e.getName());
        qmData.put("short_name", e.getShortName());
        qmData.put("desc_txt", e.getDescription());

        qmData.put("value_set_code", e.getValueSetCode() == null ? "" : e.getValueSetCode());
        qmData.put("identifier_nnd", e.getMappings().getHl7v251().getIdentifier());
        qmData.put("required_nnd", e.getMappings().getHl7v251().getUsage().equals("R") ? "R" : "O");
        qmData.put("data_type_nnd", e.getMappings().getHl7v251().getDataType());
        qmData.put("hl7_segment_field", e.getMappings().getHl7v251().getSegmentType());
        return qmData;
    }
}

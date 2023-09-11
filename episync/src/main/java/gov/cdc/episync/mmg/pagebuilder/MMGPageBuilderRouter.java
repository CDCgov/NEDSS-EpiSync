package gov.cdc.episync.mmg.pagebuilder;

import gov.cdc.episync.framework.*;
import gov.cdc.nbs.questionbank.entity.odse.WaNndMetadata;
import gov.cdc.nbs.questionbank.entity.odse.WaQuestion;
import gov.cdc.nbs.questionbank.entity.odse.WaTemplate;
import gov.cdc.nbs.questionbank.entity.odse.WaUiMetadata;
import gov.cdc.nbs.questionbank.repository.odse.WaNndMetadataRepository;
import gov.cdc.nbs.questionbank.repository.odse.WaQuestionRepository;
import gov.cdc.nbs.questionbank.repository.odse.WaTemplateRepository;
import gov.cdc.nbs.questionbank.repository.odse.WaUiMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class MMGPageBuilderRouter implements EpisyncRouter<String, List<Dictionary<String, String>>> {
    private final WaTemplateRepository repository;
    private final WaQuestionRepository questionRepository;
    private final WaUiMetadataRepository uiRepository;
    private final WaNndMetadataRepository nndRepository;

    private static final String ACTIVE = "Active";
    private static final String ENTRY_METHOD = "USER";
    private static final String QUESTION_TYPE = "PHIN";
    private static final String SOURCE = "CDC";


    @Override
    public EpisyncRouteResult routeData(EpisyncData<String, List<Dictionary<String, String>>> data) throws EpisyncRouterException {

        WaTemplate template = new WaTemplate();
        convert(data, template);
        WaTemplate saved = repository.save(template);

        List<WaQuestion> questions = new ArrayList<>();
        List<WaUiMetadata> uiMetadata = new ArrayList<>();
        convert(saved.getWaTemplateUid(), data, questions, uiMetadata);
        List<WaQuestion> savedQ = questionRepository.saveAll(questions);
        List<WaUiMetadata> savedUi = uiRepository.saveAll(uiMetadata);

        Map<String, Long> uiMap = savedUi.stream().collect(Collectors.toMap(WaUiMetadata::getQuestionIdentifier, WaUiMetadata::getWaUiMetadataUid));
        List<WaNndMetadata> nndMetadata = new ArrayList<>();
        convert(saved.getWaTemplateUid(), uiMap, data, nndMetadata);
        List<WaNndMetadata> savedNnd = nndRepository.saveAll(nndMetadata);

        String msg = "template_id:" + saved.getWaTemplateUid() + " template_name:" + saved.getTemplateNm() + " questions:" + savedQ.size();

        return new EpisyncRouteResult(EpisyncRouteResult.RouteResultCode.SUCCESS, msg);
    }

    private void convert(EpisyncData<String, List<Dictionary<String, String>>> data, WaTemplate result) {
        Dictionary<String, String> tmpData = data.data().get("template").listIterator().next();

        result.setTemplateType(tmpData.get("type"));
        result.setXmlPayload("XML Payload");
        result.setFormCd("PG_" + tmpData.get("name"));
        result.setBusObjType("INV");
        result.setDatamartNm(tmpData.get("shortName"));
        result.setRecordStatusCd(ACTIVE);
        result.setRecordStatusTime(Instant.now());
        result.setLastChgTime(Instant.now());
        result.setLastChgUserId(10000000L);
        result.setDescTxt(tmpData.get("description"));
        result.setTemplateNm(tmpData.get("name"));
        result.setPublishIndCd('F');
        result.setAddTime(Instant.now());
        result.setAddUserId(10000000L);
        result.setNndEntityIdentifier(tmpData.get("profileIdentifier"));
    }

    private void convert(Long templateId, EpisyncData<String, List<Dictionary<String, String>>> data,
                         List<WaQuestion> questions, List<WaUiMetadata> uiMetadata) {

        List<Dictionary<String, String>> elements = data.data().get("questions");
        for (Dictionary<String, String> qmData: elements) {
            WaQuestion q = new WaQuestion();
            WaUiMetadata ui = new WaUiMetadata();

            q.setQuestionIdentifier(qmData.get("identifier") + "-CDC");
            q.setQuestionOid(qmData.get("question_oid"));
            q.setQuestionOidSystemTxt(qmData.get("question_oid_system"));
            q.setDataType(qmData.get("data_type").toUpperCase());
            q.setQuestionLabel(qmData.get("name"));
            q.setVersionCtrlNbr(1);
            q.setAddUserId(10000000L);
            q.setAddTime(Instant.now());
            q.setLastChgUserId(10000000L);
            q.setLastChgTime(Instant.now());
            q.setQuestionNm(qmData.get("name"));
            q.setDescTxt(qmData.get("desc_txt"));
            q.setRptAdminColumnNm(qmData.get("name"));
            q.setQuestionIdentifierNnd(qmData.get("identifier_nnd"));
            q.setQuestionLabelNnd(qmData.get("name"));
            q.setQuestionRequiredNnd(qmData.get("required_nnd"));
            q.setQuestionDataTypeNnd(qmData.get("data_type_nnd"));
            q.setHl7SegmentField(qmData.get("hl7_segment_field"));
            q.setRecordStatusCd(ACTIVE);
            q.setRecordStatusTime(Instant.now());

            q.setNbsUiComponentUid(1000L);
            q.setStandardQuestionIndCd("F");
            q.setEntryMethod(ENTRY_METHOD);
            q.setStandardNndIndCd("F");

            q.setQuestionType(QUESTION_TYPE);
            q.setLegacyQuestionIdentifier(qmData.get("identifier"));
            q.setSourceNm(SOURCE);
            questions.add(q);

            ui.setWaTemplateUid(templateId);
            ui.setNbsUiComponentUid(1000L);
            ui.setEnableInd("T");
            ui.setDisplayInd("T");
            ui.setRequiredInd("F");
            ui.setVersionCtrlNbr(1);
            ui.setAddTime(Instant.now());
            ui.setAddUserId(10000000L);
            ui.setDataType(qmData.get("data_type").toUpperCase());

            ui.setQuestionIdentifier(qmData.get("identifier") + "-CDC");
            ui.setQuestionOid(qmData.get("question_oid"));
            ui.setQuestionOidSystemTxt(qmData.get("question_oid_system"));

            ui.setDescTxt(qmData.get("desc_txt"));
            ui.setQuestionType(QUESTION_TYPE);

            ui.setPublishIndCd("T");
            ui.setStandardQuestionIndCd("F");
            ui.setEntryMethod(ENTRY_METHOD);
            ui.setStandardNndIndCd("F");

            ui.setQuestionNm(qmData.get("name"));
            uiMetadata.add(ui);
        }
    }

    private void convert(Long templateId, Map<String, Long> uiMap, EpisyncData<String, List<Dictionary<String, String>>> data,
                               List<WaNndMetadata> nndMetadata) {
        List<Dictionary<String, String>> elements = data.data().get("questions");
        for (Dictionary<String, String> qmData: elements) {
            WaNndMetadata nnd = new WaNndMetadata();

            String identifier = qmData.get("identifier") + "-CDC";

            nnd.setWaTemplateUid(templateId);
            nnd.setQuestionIdentifierNnd(qmData.get("identifier_nnd"));
            nnd.setQuestionLabelNnd(qmData.get("name"));
            nnd.setQuestionRequiredNnd(qmData.get("required_nnd"));
            nnd.setQuestionDataTypeNnd(qmData.get("data_type_nnd"));
            nnd.setHl7SegmentField(qmData.get("hl7_segment_field"));
            nnd.setAddUserId(10000000L);
            nnd.setAddTime(Instant.now());
            nnd.setLastChgUserId(10000000L);
            nnd.setLastChgTime(Instant.now());
            nnd.setRecordStatusCd(ACTIVE);
            nnd.setRecordStatusTime(Instant.now());
            nnd.setQuestionIdentifier(identifier);
            nnd.setWaUiMetadataUid(uiMap.get(identifier));

            nndMetadata.add(nnd);
        }
    }
}

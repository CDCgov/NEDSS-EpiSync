package gov.cdc.episync.mmg.pagebuilder;

import gov.cdc.episync.framework.*;
import gov.cdc.nbs.questionbank.entity.odse.WaNndMetadata;
import gov.cdc.nbs.questionbank.entity.odse.WaQuestion;
import gov.cdc.nbs.questionbank.entity.odse.WaTemplate;
import gov.cdc.nbs.questionbank.entity.odse.WaUiMetadata;
import gov.cdc.nbs.questionbank.repository.odse.WaQuestionRepository;
import gov.cdc.nbs.questionbank.repository.odse.WaTemplateRepository;
import gov.cdc.nbs.questionbank.repository.odse.WaUiMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service @RequiredArgsConstructor
public class MMGPageBuilderRouter implements EpisyncRouter<String, List<Dictionary<String, String>>> {
    private final WaTemplateRepository repository;
    private final WaQuestionRepository questionRepository;
    private final WaUiMetadataRepository uiRepository;

    private static final Long PB_USER = 10000000L;

    private static final String ACTIVE = "Active";
    private static final String ENTRY_METHOD = "USER";
    private static final String QUESTION_TYPE = "PHIN";
    private static final String SOURCE = "CDC";

    private static final Long PAGE = 1002L;
    private static final Long TAB = 1010L;
    private static final Long SECTION = 1015L;
    private static final Long SUBSEC = 1016L;

    private static final Long CODED = 1007L;
    private static final Long INPUT = 1008L;
    private static final Long TEXTAREA = 1009L;

    @Override
    public EpisyncRouteResult routeData(EpisyncData<String, List<Dictionary<String, String>>> data) throws EpisyncRouterException {

        WaTemplate template = convert(data);
        WaTemplate saved = repository.save(template);

        List<WaQuestion> questions = convert(data, template.getLocalId());

        List<WaQuestion> existingQuestions = questionRepository.findByIdentifiers(
                questions.stream().map(WaQuestion::getQuestionIdentifier).collect(Collectors.toList()));
        Set<String> existingIdentifiers = existingQuestions.stream()
                .map(WaQuestion::getQuestionIdentifier).collect(Collectors.toSet());
        List<WaQuestion> newQuestions = questions.stream()
                .filter(question -> !existingIdentifiers.contains(question.getQuestionIdentifier()))
                .collect(Collectors.toList());

        Map<String, WaQuestion> questionMap = Stream.concat(newQuestions.stream(), existingQuestions.stream())
                .collect(Collectors.toMap(WaQuestion::getQuestionIdentifier, Function.identity()));

        List<WaUiMetadata> uiMetadata = convert(data, saved, questionMap);

        List<WaQuestion> savedQ = questionRepository.saveAll(newQuestions);
        List<WaUiMetadata> savedUi = uiRepository.saveAll(uiMetadata);


        Map<Long, WaQuestion> uiMap = savedUi.stream().filter(ui -> ui.getDataType() != null)
                .collect(Collectors.toMap(WaUiMetadata::getWaUiMetadataUid, ui -> questionMap.get(ui.getQuestionIdentifier())));
        List<WaNndMetadata> nndMetadata = convert(saved.getWaTemplateUid(), uiMap);

        //List<WaNndMetadata> savedNnd = nndRepository.saveAll(nndMetadata);

        String msg = "template_id:" + saved.getWaTemplateUid() + " template_name:" + saved.getTemplateNm() + " questions:" + questionMap.size();

        return new EpisyncRouteResult(EpisyncRouteResult.RouteResultCode.SUCCESS, msg);
    }

    private WaTemplate convert(EpisyncData<String, List<Dictionary<String, String>>> data) {
        WaTemplate result = new WaTemplate();

        Dictionary<String, String> tmpData = data.data().get("template").listIterator().next();

        result.setTemplateType(tmpData.get("type"));
        result.setXmlPayload("XML Payload");
        result.setFormCd("PG_" + tmpData.get("name"));
        result.setBusObjType("IXS");
        result.setDatamartNm(tmpData.get("shortName"));
        result.setRecordStatusCd(ACTIVE);
        result.setRecordStatusTime(Instant.now());
        result.setLastChgTime(Instant.now());
        result.setLastChgUserId(PB_USER);
        result.setLocalId(tmpData.get("id"));
        result.setDescTxt(tmpData.get("description"));
        result.setTemplateNm(tmpData.get("name"));
        result.setPublishIndCd('F');
        result.setAddTime(Instant.now());
        result.setAddUserId(PB_USER);
        result.setNndEntityIdentifier(tmpData.get("profileIdentifier"));

        return result;
    }

    private List<WaQuestion> convert(EpisyncData<String, List<Dictionary<String, String>>> data, String localId) {
        List<WaQuestion> questions = new ArrayList<>();
        List<Dictionary<String, String>> blocks = data.data().get(localId);

        for (Dictionary<String, String> blockData: blocks) {
            List<Dictionary<String, String>> elements = data.data().get(blockData.get("id"));
            for (Dictionary<String, String> qmData : elements) {
                WaQuestion q = new WaQuestion();

                q.setQuestionIdentifier(qmData.get("identifier"));
                q.setQuestionOid(qmData.get("question_oid"));
                q.setQuestionOidSystemTxt(qmData.get("question_oid_system"));
                q.setDataType(qmData.get("data_type").toUpperCase());
                q.setQuestionLabel(trunkEncode(qmData.get("name")));
                q.setQuestionToolTip(trunkEncode(qmData.get("desc_txt")));
                q.setVersionCtrlNbr(1);
                q.setAddUserId(PB_USER);
                q.setAddTime(Instant.now());
                q.setLastChgUserId(PB_USER);
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

                q.setNbsUiComponentUid(q.getDataType().equals("CODED") ? CODED : INPUT);
                q.setStandardQuestionIndCd("F");
                q.setEntryMethod(ENTRY_METHOD);
                q.setStandardNndIndCd("F");

                q.setQuestionType(QUESTION_TYPE);
                q.setLegacyQuestionIdentifier(qmData.get("identifier"));
                q.setSourceNm(SOURCE);
                questions.add(q);
            }
        }
        return questions;
    }

    private List<WaUiMetadata> convert(EpisyncData<String, List<Dictionary<String, String>>> data, WaTemplate template,
                         Map<String, WaQuestion> questions) {

        List<WaUiMetadata> uiMetadata = new ArrayList<>();

        int order = 0;
        int uiNum = 0;
        Long templateId = template.getWaTemplateUid();

        WaUiMetadata page = getMetaData(templateId, PAGE, template.getTemplateNm(), ++order, ++uiNum);
        page.setQuestionIdentifier("MMG_UI_" + uiNum++);
        uiMetadata.add(page);

        WaUiMetadata tab = getMetaData(templateId, TAB, "MMG", ++order, ++uiNum);
        uiMetadata.add(tab);

        WaUiMetadata sec = getMetaData(templateId, SECTION, "Blocks", ++order, ++uiNum);
        uiMetadata.add(sec);

        List<Dictionary<String, String>> blocks = data.data().get(template.getLocalId());

        int blk = 0;
        for (Dictionary<String, String> blockData: blocks) {

            List<Dictionary<String, String>> elements = data.data().get(blockData.get("id"));
            boolean blockRepeat = blockData.get("type").equalsIgnoreCase("repeat");
            int batchWidth = 0;

            if (!elements.isEmpty()) {
                WaUiMetadata subsec = getMetaData(templateId, SUBSEC, blockData.get("name"), ++order, ++uiNum);
                if (blockRepeat) {
                    subsec.setQuestionGroupSeqNbr(++blk);
                    subsec.setBlockNm("BLOCK_" + blk);
                    batchWidth = 100 / elements.size();
                }
                uiMetadata.add(subsec);

                for (Dictionary<String, String> qmData : elements) {
                    WaUiMetadata ui = new WaUiMetadata();

                    String identifier = qmData.get("identifier");
                    WaQuestion q = questions.get(identifier);

                    ui.setWaTemplateUid(templateId);

                    String fieldSize = Optional.ofNullable(q.getFieldSize()).orElse("");
                    ui.setFieldSize(fieldSize.length() > 2 ? null : fieldSize);

                    long componentUid = q.getNbsUiComponentUid();
                    String dataType = q.getDataType();

                    if (componentUid == CODED && q.getCodeSetGroupId() == null) {
                        componentUid = fieldSize.length() > 2 ? TEXTAREA : INPUT;
                        dataType = "TEXT";
                    }
                    ui.setNbsUiComponentUid(componentUid);
                    ui.setQuestionLabel(trunkEncode(qmData.get("name")));
                    ui.setQuestionToolTip(q.getQuestionToolTip());
                    ui.setOrderNbr("system".equalsIgnoreCase(q.getEntryMethod()) ? 0 : ++order);
                    ui.setEnableInd("T");
                    ui.setDisplayInd("T");
                    ui.setRequiredInd(q.getQuestionRequiredNnd());
                    ui.setVersionCtrlNbr(q.getVersionCtrlNbr());

                    ui.setMask(q.getMask());
                    ui.setEntryMethod(q.getEntryMethod());
                    ui.setCodeSetGroupId(q.getCodeSetGroupId());

                    ui.setAddTime(Instant.now());
                    ui.setAddUserId(PB_USER);
                    ui.setDataType(dataType);

                    ui.setQuestionIdentifier(identifier);
                    ui.setQuestionOid(q.getQuestionOid());
                    ui.setQuestionOidSystemTxt(q.getQuestionOidSystemTxt());
                    ui.setQuestionUnitIdentifier(q.getQuestionUnitIdentifier());
                    ui.setUnitParentIdentifier(q.getUnitParentIdentifier());

                    ui.setGroupNm(q.getGroupNm());
                    ui.setSubGroupNm(q.getSubGroupNm());

                    ui.setDescTxt(q.getDescTxt());
                    ui.setQuestionType(q.getQuestionType());

                    ui.setRecordStatusCd(ACTIVE);
                    ui.setRecordStatusTime(Instant.now());
                    ui.setPublishIndCd("T");
                    ui.setStandardQuestionIndCd(q.getStandardQuestionIndCd());
                    ui.setStandardNndIndCd(q.getStandardNndIndCd());

                    ui.setQuestionNm(q.getQuestionNm());

                    if (blockRepeat) {
                        ui.setQuestionGroupSeqNbr(blk);
                        ui.setBatchTableAppearIndCd("Y");
                        ui.setBatchTableHeader(ui.getQuestionLabel());
                        ui.setBatchTableColumnWidth(batchWidth);
                        ui.setBlockNm("BLOCK_" + blk);
                    }
                    uiMetadata.add(ui);
                }
            }
        }

        return uiMetadata;
    }

    private List<WaNndMetadata> convert(Long templateId, Map<Long, WaQuestion> uiMap) {
        List<WaNndMetadata> nndMetadata = new ArrayList<>();

        for (Entry<Long, WaQuestion> e : uiMap.entrySet()) {
            WaQuestion question = e.getValue();
            WaNndMetadata nnd = new WaNndMetadata();

            nnd.setWaTemplateUid(templateId);
            nnd.setQuestionIdentifierNnd(question.getQuestionIdentifier());
            nnd.setQuestionLabelNnd(question.getQuestionLabel());
            nnd.setQuestionRequiredNnd(question.getQuestionRequiredNnd());
            nnd.setQuestionDataTypeNnd(question.getQuestionDataTypeNnd());
            nnd.setHl7SegmentField(question.getHl7SegmentField());
            nnd.setAddUserId(PB_USER);
            nnd.setAddTime(Instant.now());
            nnd.setLastChgUserId(PB_USER);
            nnd.setLastChgTime(Instant.now());
            nnd.setRecordStatusCd(ACTIVE);
            nnd.setRecordStatusTime(Instant.now());
            nnd.setQuestionIdentifier(question.getQuestionIdentifier());
            nnd.setWaUiMetadataUid(e.getKey());

            nndMetadata.add(nnd);
        }
        return nndMetadata;
    }

    private WaUiMetadata getMetaData(Long templateId, Long componentUid, String label, int order, int uiNum) {
        WaUiMetadata sec = new WaUiMetadata();
        sec.setQuestionIdentifier("MMG_UI_" + uiNum);
        sec.setWaTemplateUid(templateId);
        sec.setQuestionLabel(label);
        sec.setNbsUiComponentUid(componentUid);
        sec.setOrderNbr(order);
        sec.setEnableInd("T");
        sec.setDisplayInd("T");
        sec.setRequiredInd("F");
        sec.setVersionCtrlNbr(1);
        sec.setAddTime(Instant.now());
        sec.setAddUserId(PB_USER);
        sec.setRecordStatusCd(ACTIVE);
        sec.setRecordStatusTime(Instant.now());
        sec.setPublishIndCd("T");
        sec.setStandardQuestionIndCd("F");
        sec.setStandardNndIndCd("F");

        return sec;
    }

    private static String trunkEncode(String str) {
        if (str.length() > 200) {
            str = str.split("\\.")[0];
        }
        return str.replaceAll("[\"’]", "'");
    }
}

package gov.cdc.episync.mmg.pagebuilder;

import gov.cdc.episync.framework.*;
import gov.cdc.nbs.questionbank.entity.odse.WaQuestion;
import gov.cdc.nbs.questionbank.entity.odse.WaTemplate;
import gov.cdc.nbs.questionbank.entity.odse.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.srte.Codeset;
import gov.cdc.nbs.questionbank.entity.srte.CodesetGroupMetadata;
import gov.cdc.nbs.questionbank.repository.odse.WaQuestionRepository;
import gov.cdc.nbs.questionbank.repository.odse.WaTemplateRepository;
import gov.cdc.nbs.questionbank.repository.odse.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.repository.srte.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.repository.srte.CodesetGroupMetadataRepository;
import gov.cdc.nbs.questionbank.repository.srte.CodesetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles the routing of data from Episync to PageBuilder.
 * <p>
 * Accepts {@link EpisyncData} in the form of dictionary lists and persists it to the
 * PageBuilder database.
 * </p>
 *
 * @see EpisyncRouter
 */
@Service @RequiredArgsConstructor
public class MMGPageBuilderRouter implements EpisyncRouter<String, List<Dictionary<String, String>>> {
    private final WaTemplateRepository repository;
    private final CodesetGroupMetadataRepository groupRepository;
    private final CodesetRepository codesetRepository;
    private final CodeValueGeneralRepository valueRepository;
    private final WaQuestionRepository questionRepository;
    private final WaUiMetadataRepository uiRepository;

    private static final Long PB_USER = 10000000L;

    private static final String BUS_OBJ_TYPE = "INV";

    public static final String CREATE_CLASS_CD= "code_value_general";
    public static final String SOURCE_DOMAIN= "VADS";
    public static final String VALUESET_TYPE= "PHIN";
    public static final String VALUESET_STATUS= "Published";

    private static final String DATA_LOCATION = "NBS_CASE_ANSWER.ANSWER_TXT";
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

    /**
     * Routes the provided data to the PageBuilder system. The data is saved
     * in the PageBuilder database.
     *
     * @param data The data to be routed and persisted, represented as an
     *             implementation of {@link EpisyncData}.
     * @return The result of data routing.
     */
    @Override
    public EpisyncRouteResult routeData(EpisyncData<String, List<Dictionary<String, String>>> data) throws EpisyncRouterException {

        List<String> codes = data.get("values").stream()
                .map(v -> v.get("code")).collect(Collectors.toList());

        List<CodesetGroupMetadata> existingGroups = groupRepository.findAllByVadsValueSetCode(codes);
        Set<String> existingCodes = existingGroups.stream()
                .map(CodesetGroupMetadata::getVadsValueSetCode).collect(Collectors.toSet());
        List<String> newCodes = codes.stream()
                .filter(code -> !existingCodes.contains(code)).collect(Collectors.toList());

        List<CodesetGroupMetadata> newGroups = convert(data, newCodes, groupRepository.getMaxCodesetGroupId());
        if (!newGroups.isEmpty()) {
            Map<String, Long> groupMap = newGroups.stream()
                    .collect(Collectors.toMap(CodesetGroupMetadata::getVadsValueSetCode, CodesetGroupMetadata::getCodeSetGroupId, Math::max));

            List<Codeset> codeSets = convert(data, groupMap);
            List<CodeValueGeneral> values = convert(data, codeSets);

            List<CodesetGroupMetadata> savedGroups = groupRepository.saveAll(newGroups);
            List<Codeset> savedCodes = codesetRepository.saveAll(codeSets);
            List<CodeValueGeneral> savedValues = valueRepository.saveAll(values);
        }

        Map<String, Long> groupMap = Stream.concat(newGroups.stream(), existingGroups.stream())
                .collect(Collectors.toMap(CodesetGroupMetadata::getVadsValueSetCode, CodesetGroupMetadata::getCodeSetGroupId, Math::max));

        WaTemplate template = convert(data);

        List<WaQuestion> questions = convert(data, groupMap, template.getLocalId());
        List<WaQuestion> existingQuestions = questionRepository.findByIdentifiers(
                questions.stream().map(WaQuestion::getQuestionIdentifier).collect(Collectors.toList()));
        Set<String> existingIdentifiers = existingQuestions.stream()
                .map(WaQuestion::getQuestionIdentifier).collect(Collectors.toSet());
        List<WaQuestion> newQuestions = questions.stream()
                .filter(question -> !existingIdentifiers.contains(question.getQuestionIdentifier()))
                .collect(Collectors.toList());

        Map<String, WaQuestion> questionMap = Stream.concat(newQuestions.stream(), existingQuestions.stream())
                .collect(Collectors.toMap(WaQuestion::getQuestionIdentifier, Function.identity()));
        groupMap = questions.stream().filter(q -> q.getCodeSetGroupId() != null).collect(Collectors.toMap(WaQuestion::getQuestionIdentifier, WaQuestion::getCodeSetGroupId));

        WaTemplate saved = repository.save(template);
        List<WaUiMetadata> uiMetadata = convert(data, saved, questionMap, groupMap);
        List<WaQuestion> savedQ = questionRepository.saveAll(newQuestions);
        List<WaUiMetadata> savedUi = uiRepository.saveAll(uiMetadata);

        String msg = "template_id:" + saved.getWaTemplateUid() + " template_name:" + saved.getTemplateNm() + " questions:" + questionMap.size();

        return new EpisyncRouteResult(EpisyncRouteResult.RouteResultCode.SUCCESS, msg);
    }

    private WaTemplate convert(EpisyncData<String, List<Dictionary<String, String>>> data) {
        WaTemplate result = new WaTemplate();

        Dictionary<String, String> tmpData = data.get("template").listIterator().next();

        result.setTemplateType(tmpData.get("type"));
        result.setXmlPayload("XML Payload");
        result.setFormCd("PG_" + tmpData.get("name"));
        result.setBusObjType(BUS_OBJ_TYPE);
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

    private List<CodesetGroupMetadata> convert(EpisyncData<String, List<Dictionary<String, String>>> data,
                                               List<String> codes, Long groupId) {
        List<CodesetGroupMetadata> groups = new ArrayList<>();
        List<Dictionary<String, String>> values = data.get("values");
        for (Dictionary<String, String> vs: values) {
            String valueSetCode = vs.get("code");
            if (codes.contains(valueSetCode)) {
                CodesetGroupMetadata group = new CodesetGroupMetadata();
                groupId += 10;
                group.setCodeSetNm(valueSetCode);
                group.setCodeSetGroupId(groupId);
                group.setVadsValueSetCode(valueSetCode);
                group.setCodeSetDescTxt(vs.get("definition"));
                group.setCodeSetShortDescTxt(vs.get("name"));
                group.setLdfPicklistIndCd("Y");
                group.setPhinStdValInd("Y");

                groups.add(group);
            }
        }

        return groups;
    }

    private List<Codeset> convert(EpisyncData<String, List<Dictionary<String, String>>> data,
                                  Map<String, Long> groupMap) {
        List<Codeset> codesets = new ArrayList<>();

        List<Dictionary<String, String>> values = data.get("values");
        for (Dictionary<String, String> vs: values) {
            String valueSetCode = vs.get("code");

            if (groupMap.containsKey(valueSetCode)) {

                Codeset c = new Codeset();

                c.setCodeSetNm(valueSetCode);
                c.setAssigningAuthorityCd("2.16.840.1.114222");
                c.setAssigningAuthorityDescTxt("Centers for Disease Control and Prevention");
                c.setCodeSetDescTxt(vs.get("definition"));
                c.setClassCd(CREATE_CLASS_CD);
                c.setEffectiveFromTime(LocalDateTime.parse(vs.get("status_date")).toInstant(ZoneOffset.UTC));
                c.setModifiableInd("Y");
                c.setSourceDomainNm(SOURCE_DOMAIN);
                c.setStatusCd("A");
                c.setStatusToTime(c.getEffectiveFromTime());
                c.setCodeSetGroupId(groupMap.get(valueSetCode));
                c.setValueSetNm(vs.get("name"));
                c.setLdfPicklistIndCd("Y");
                c.setValueSetCode(valueSetCode);
                c.setValueSetTypeCd(VALUESET_TYPE);
                c.setValueSetOid(vs.get("oid"));
                c.setValueSetStatusCd(VALUESET_STATUS);
                c.setValueSetStatusTime(c.getStatusToTime());
                c.setAddTime(Instant.now());
                c.setAddUserId(PB_USER);

                codesets.add(c);
            }
        }

        return codesets;
    }

    private List<CodeValueGeneral> convert(EpisyncData<String, List<Dictionary<String, String>>> data, List<Codeset> codesets) {
        List<CodeValueGeneral> values = new ArrayList<>();
        for (Codeset codeset : codesets) {
            List<Dictionary<String, String>> concepts = data.get(codeset.getValueSetCode());
            for (Dictionary<String, String> concept : concepts) {
                CodeValueGeneral value = new CodeValueGeneral();

                value.setCodeSetNm(codeset.getCodeSetNm());
                value.setCode(concept.get("code"));
                value.setCodeDescTxt(concept.get("name"));
                value.setCodeShortDescTxt(concept.get("name"));

                value.setCodeSystemCd(concept.get("oid"));
                value.setCodeSystemDescTxt(concept.get("identifier"));
                value.setEffectiveFromTime(LocalDateTime.parse(concept.get("status_date")).toInstant(ZoneOffset.UTC));

                value.setIndentLevelNbr(1);
                value.setModifiableInd("Y");
                value.setStatusCd("A");
                value.setStatusTime(value.getEffectiveFromTime());
                value.setConceptTypeCd(VALUESET_TYPE);

                value.setConceptCode(concept.get("code"));
                value.setConceptNm(concept.get("name"));
                value.setConceptPreferredNm(concept.get("preferred"));

                value.setConceptStatusCd(VALUESET_STATUS);
                value.setConceptStatusTime(value.getEffectiveFromTime());
                value.setAddTime(Instant.now());
                value.setAddUserId(PB_USER);

                values.add(value);
            }
        }
        return values;
    }

    private List<WaQuestion> convert(EpisyncData<String, List<Dictionary<String, String>>> data,
                                     Map<String, Long> groupMap, String localId) {
        List<WaQuestion> questions = new ArrayList<>();
        List<Dictionary<String, String>> blocks = data.get(localId);

        for (Dictionary<String, String> blockData: blocks) {
            List<Dictionary<String, String>> elements = data.get(blockData.get("id"));
            for (Dictionary<String, String> qmData : elements) {
                WaQuestion q = new WaQuestion();

                String valueSetCode = qmData.get("value_set_code");

                q.setCodeSetGroupId(groupMap.computeIfAbsent(valueSetCode, k -> null));
                q.setDataLocation(DATA_LOCATION);
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
                         Map<String, WaQuestion> questions, Map<String, Long> groupMap) {

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

        List<Dictionary<String, String>> blocks = data.get(template.getLocalId());

        int blk = 0;
        for (Dictionary<String, String> blockData: blocks) {

            List<Dictionary<String, String>> elements = data.get(blockData.get("id"));
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
                    Long groupId = groupMap.get(identifier);

                    ui.setWaTemplateUid(templateId);

                    String fieldSize = Optional.ofNullable(q.getFieldSize()).orElse("");
                    ui.setFieldSize(fieldSize.length() > 2 ? null : fieldSize);

                    long componentUid = q.getNbsUiComponentUid();
                    String dataType = q.getDataType();

                    if (componentUid == CODED && groupId == null) {
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
                    ui.setCodeSetGroupId(groupId);

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

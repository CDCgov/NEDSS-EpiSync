package gov.cdc.episync.mmg.pagebuilder;

import gov.cdc.episync.framework.*;
import gov.cdc.nbs.questionbank.entity.odse.*;
import gov.cdc.nbs.questionbank.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.srte.Codeset;
import gov.cdc.nbs.questionbank.entity.srte.CodesetGroupMetadata;
import gov.cdc.nbs.questionbank.service.PageService;
import gov.cdc.nbs.questionbank.service.ValueSetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final ValueSetService valueSetService;
    private final PageService pageService;

    private static final Long PB_USER = 10000000L;

    private static final String BUS_OBJ_TYPE = "INV";

    public static final String CREATE_CLASS_CD= "code_value_general";
    public static final String SOURCE_DOMAIN= "VADS";
    public static final String VALUESET_TYPE= "PHIN";

    private static final String DATA_LOCATION = "NBS_CASE_ANSWER.ANSWER_TXT";
    private static final String GROUP_NM = "GROUP_INV";
    private static final String SUB_GROUP_NM = "INV";
    private static final String ACTIVE = "Active";
    private static final String ENTRY_USER = "USER";
    private static final String ENTRY_SYSTEM = "SYSTEM";
    private static final String QUESTION_TYPE = "PHIN";
    private static final String SOURCE = "CDC";

    private static final Long PAGE = 1002L;
    private static final Long TAB = 1010L;
    private static final Long SECTION = 1015L;
    private static final Long SUBSEC = 1016L;

    private static final Long CODED = 1007L;
    private static final Long INPUT = 1008L;
    private static final Long TEXTAREA = 1009L;

    Logger logger = LoggerFactory.getLogger(MMGPageBuilderRouter.class);

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

        Map<String, Long> groupMap = processValues(data);

        logger.info("Start processing template and question metadata");
        WaTemplate template = convert(data);
        List<WaQuestion> questions = convert(data, groupMap, template.getLocalId());

        List<WaQuestion> existingQuestions = pageService.findByIdentifiers(
                questions.stream().map(WaQuestion::getQuestionIdentifier).collect(Collectors.toList()));
        Set<String> existingIdentifiers = existingQuestions.stream()
                .map(WaQuestion::getQuestionIdentifier).collect(Collectors.toSet());
        List<WaQuestion> newQuestions =  new ArrayList<>(questions.stream()
                .filter(question -> !existingIdentifiers.contains(question.getQuestionIdentifier()))
                .collect(Collectors.toMap(WaQuestion::getQuestionIdentifier, Function.identity(), (v1, v2) -> v1)) // some MMGs contain dupes
                .values());

        if (!newQuestions.isEmpty()) {
            logger.info("Storing new questions...");
            try {
                pageService.save(newQuestions);
                logger.info("Successfully stored {} new questions", newQuestions.size());
            } catch (Exception e) {
                throw new EpisyncRouterException("Cannot store question data; " + e.getMessage());
            }
        }

        Map<String, WaQuestion> questionMap = Stream.concat(newQuestions.stream(), existingQuestions.stream())
                .collect(Collectors.toMap(WaQuestion::getQuestionIdentifier, Function.identity(), (v1, v2) -> v1));
        
        groupMap = questions.stream().filter(q -> q.getCodeSetGroupId() != null).collect(Collectors.toMap(WaQuestion::getQuestionIdentifier, WaQuestion::getCodeSetGroupId, Math::max));

        Long nbsTemplateUid = Optional.ofNullable(getTmpData(data).get("nbs_uid")).map(Long::parseLong).orElse(null);
        List<WaUiMetadata> nbsUiData = pageService.copyWaTemplateUIMetaData(nbsTemplateUid, PB_USER);
        List<WaNndMetadata> nbsNndData = pageService.copyWaTemplateNndMetaData(nbsTemplateUid, PB_USER);
        List<WaRuleMetadata> nbsRuleData = pageService.copyWaTemplateRuleMetaData(nbsTemplateUid, PB_USER);
        List<WaRdbMetadata> nbsRdbData = pageService.copyWaTemplateRdbMetaData(nbsTemplateUid, PB_USER);

        logger.info("Storing template data: {}", template.getTemplateNm());
        try {
            List<WaUiMetadata> mmgUiData = convert(data, template, questionMap, groupMap);
            List<WaUiMetadata> mergedUiData = mergeUi(nbsUiData, mmgUiData);
            List<WaNndMetadata> mmgNndData = convert(questionMap.values());
            List<WaNndMetadata> mergedNndData = mergeNnd(nbsNndData, mmgNndData);

            WaTemplate saved = pageService.save(template, mergedUiData, mergedNndData, nbsRuleData, nbsRdbData);

            logger.info("Successfully stored template data: {}, id: {}", saved.getTemplateNm(), saved.getWaTemplateUid());
            String msg = "template_id:" + saved.getWaTemplateUid() + " template_name:" + saved.getTemplateNm()
                    + " mmg_questions:" + questionMap.size() + " new_questions:" + newQuestions.size();

            return new EpisyncRouteResult(EpisyncRouteResult.RouteResultCode.SUCCESS, msg);
        } catch (Exception e) {
            throw new EpisyncRouterException("Cannot store template data: " + template.getTemplateNm() + "; " + e.getMessage());
        }
    }

    private Map<String, Long> processValues(EpisyncData<String, List<Dictionary<String, String>>> data) {

        logger.info("Start processing value sets metadata");

        List<String> codes = data.get("values").stream()
                .map(v -> v.get("code")).collect(Collectors.toList());

        List<CodesetGroupMetadata> existingGroups = valueSetService.findValueSetGroupByCode(codes);
        Set<String> existingCodes = existingGroups.stream()
                .map(CodesetGroupMetadata::getCodeSetNm).collect(Collectors.toSet());
        List<String> newCodes = codes.stream()
                .filter(code -> !existingCodes.contains(code)).collect(Collectors.toList());

        List<CodesetGroupMetadata> newGroups = convert(data, newCodes, valueSetService.getMaxGroupId());
        List<CodesetGroupMetadata> savedGroups = new ArrayList<>();
        if (!newGroups.isEmpty()) {
            Map<String, CodesetGroupMetadata> groupMap = newGroups.stream()
                    .collect(Collectors.toMap(CodesetGroupMetadata::getCodeSetNm, Function.identity(),
                            (v1, v2) -> v2.getCodeSetGroupId() > v1.getCodeSetGroupId() ? v2 : v1));

            List<Codeset> codeSets = convert(data, groupMap);
            Map<Codeset, List<CodeValueGeneral>> valueMap = convert(data, codeSets);

            for (Codeset cset: valueMap.keySet()) {
                logger.info("Storing value set: {}...", cset.getValueSetCode());
                try {
                    CodesetGroupMetadata saved = valueSetService.save(cset, valueMap.get(cset));
                    savedGroups.add(saved);
                } catch (Exception e) {
                    logger.error("Cannot store value set {}; {}", cset.getCodeSetNm(), e.getMessage());
                }
            }
            if (!savedGroups.isEmpty()) {
                logger.info("Successfully stored {} value sets of {}", savedGroups.size(), newGroups.size());
            }
        }

        return Stream.concat(savedGroups.stream(), existingGroups.stream())
                .collect(Collectors.toMap(CodesetGroupMetadata::getCodeSetNm, CodesetGroupMetadata::getCodeSetGroupId, Math::max));
    }

    private Dictionary<String, String> getTmpData(EpisyncData<String, List<Dictionary<String, String>>> data) {
        return data.get("template").listIterator().next();
    }

    private WaTemplate convert(EpisyncData<String, List<Dictionary<String, String>>> data) {
        WaTemplate result = new WaTemplate();

        Dictionary<String, String> tmpData = getTmpData(data);
        Long nbsTemplateUid = Optional.ofNullable(getTmpData(data).get("nbs_uid")).map(Long::parseLong).orElse(0L);
        Optional<WaTemplate> proto = pageService.getTemplate(nbsTemplateUid);

        if (proto.isPresent()) {
            WaTemplate source = proto.get();
            result.setTemplateType(source.getTemplateType());
            result.setFormCd(source.getFormCd());
            result.setBusObjType(source.getBusObjType());
            result.setDatamartNm(source.getDatamartNm());
            result.setRecordStatusCd(source.getRecordStatusCd());
            result.setRecordStatusTime(Instant.now());
            result.setLastChgTime(Instant.now());
            result.setLastChgUserId(PB_USER);
            result.setLocalId(tmpData.get("id"));
            result.setDescTxt(source.getDescTxt());
            result.setTemplateNm(source.getTemplateNm() + "_MMG");
            result.setPublishIndCd(source.getPublishIndCd());
            result.setAddTime(Instant.now());
            result.setAddUserId(PB_USER);
            result.setNndEntityIdentifier(source.getNndEntityIdentifier());
            result.setParentTemplateUid(source.getParentTemplateUid());
            result.setSourceNm(source.getSourceNm());
            result.setTemplateVersionNbr(source.getTemplateVersionNbr());
        } else {

            result.setTemplateType("Template");
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
        }

        return result;
    }

    private List<CodesetGroupMetadata> convert(EpisyncData<String, List<Dictionary<String, String>>> data,
                                               List<String> codes, Long groupId) {
        List<CodesetGroupMetadata> groups = new ArrayList<>();
        List<Dictionary<String, String>> values = data.get("values").stream()
                .filter(vs -> codes.contains(vs.get("code"))).collect(Collectors.toList());
        for (Dictionary<String, String> vs: values) {
            String valueSetCode = vs.get("code");
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

        return groups;
    }

    private List<Codeset> convert(EpisyncData<String, List<Dictionary<String, String>>> data,
                                  Map<String, CodesetGroupMetadata> groupMap) {
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
                c.setEffectiveFromTime(Instant.now());
                c.setEffectiveToTime(Instant.now());
                c.setModifiableInd("Y");
                c.setSourceVersionTxt(vs.get("version"));
                c.setSourceDomainNm(SOURCE_DOMAIN);
                c.setStatusCd("A");
                c.setStatusToTime(LocalDateTime.parse(vs.get("status_date")).toInstant(ZoneOffset.UTC));
                c.setCodeSetGroup(groupMap.get(valueSetCode));
                c.setValueSetNm(vs.get("name"));
                c.setLdfPicklistIndCd("Y");
                c.setValueSetCode(valueSetCode);
                c.setValueSetTypeCd(VALUESET_TYPE);
                c.setValueSetOid(vs.get("oid"));
                c.setValueSetStatusCd(ACTIVE);
                c.setValueSetStatusTime(c.getStatusToTime());
                c.setAddTime(Instant.now());
                c.setAddUserId(PB_USER);

                codesets.add(c);
            }
        }

        return codesets;
    }

    private Map<Codeset, List<CodeValueGeneral>> convert(EpisyncData<String, List<Dictionary<String, String>>> data, List<Codeset> codesets) {
        Map<Codeset, List<CodeValueGeneral>> result = new HashMap<>();
        for (Codeset codeset : codesets) {
            List<CodeValueGeneral> values = new ArrayList<>();
            List<Dictionary<String, String>> concepts = data.get(codeset.getValueSetCode());
            for (Dictionary<String, String> concept : concepts) {
                CodeValueGeneral value = new CodeValueGeneral();

                String code = concept.get("code");
                String name = concept.get("name");

                value.setCodeSetNm(codeset.getCodeSetNm());
                value.setCode(code);
                value.setCodeDescTxt(name);
                value.setCodeShortDescTxt(name.length() > 100 ? code : name);

                value.setCodeSystemCd(concept.get("oid"));
                value.setCodeSystemDescTxt(concept.get("identifier"));
                value.setEffectiveFromTime(Instant.now());
                value.setEffectiveToTime(Instant.now());

                value.setIndentLevelNbr(1);
                value.setModifiableInd("Y");
                value.setStatusCd("A");
                value.setStatusTime(LocalDateTime.parse(concept.get("status_date")).toInstant(ZoneOffset.UTC));
                value.setConceptTypeCd(VALUESET_TYPE);

                value.setConceptCode(code);
                value.setConceptNm(name);
                value.setConceptPreferredNm(concept.get("preferred"));

                value.setConceptStatusCd(ACTIVE);
                value.setConceptStatusTime(value.getStatusTime());
                value.setAddTime(Instant.now());
                value.setAddUserId(PB_USER);

                values.add(value);
            }
            result.put(codeset, values);
        }
        return result;
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
                q.setQuestionLabel(trunkEncode(qmData.get("name"), 300));
                q.setQuestionToolTip(trunkEncode(qmData.get("desc_txt"), 2000));

                q.setDefaultValue(qmData.get("default"));
                q.setVersionCtrlNbr(1);
                q.setAddUserId(PB_USER);
                q.setAddTime(Instant.now());
                q.setLastChgUserId(PB_USER);
                q.setLastChgTime(Instant.now());
                q.setQuestionNm(trunkEncode(qmData.get("name"), 50));

                q.setGroupNm(GROUP_NM);
                q.setSubGroupNm(SUB_GROUP_NM);

                q.setDescTxt(q.getQuestionToolTip());
                q.setRptAdminColumnNm(q.getQuestionNm());
                q.setNndMsgInd("T");
                q.setQuestionIdentifierNnd(qmData.get("identifier_nnd"));
                q.setQuestionLabelNnd(trunkEncode(qmData.get("name"), 150));
                q.setQuestionRequiredNnd(qmData.get("required_nnd"));
                q.setQuestionDataTypeNnd(qmData.get("data_type_nnd"));
                q.setHl7SegmentField(qmData.get("hl7_segment_field"));
                q.setRecordStatusCd(ACTIVE);
                q.setRecordStatusTime(Instant.now());

                q.setNbsUiComponentUid(q.getDataType().equals("CODED") ? CODED : INPUT);
                boolean standardNnd = "EI".equals(q.getQuestionDataTypeNnd());
                q.setStandardNndIndCd(standardNnd ? "T" : "F");
                q.setStandardQuestionIndCd(standardNnd ? "T" : "F");
                q.setEntryMethod(standardNnd ? ENTRY_SYSTEM : ENTRY_USER);

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

        uiMetadata.add(getMetaData(PAGE, template.getTemplateNm(), ++order, ++uiNum));
        uiMetadata.add(getMetaData(TAB, "MMG", ++order, ++uiNum));
        uiMetadata.add(getMetaData(SECTION, "Blocks", ++order, ++uiNum));

        List<Dictionary<String, String>> blocks = data.get(template.getLocalId());

        int blk = 0;
        for (Dictionary<String, String> blockData: blocks) {

            List<Dictionary<String, String>> elements = data.get(blockData.get("id"));
            boolean visible = elements.stream().map(e -> questions.get(e.get("identifier"))).anyMatch(q -> !"T".equals(q.getStandardNndIndCd()));
            boolean blockRepeat = blockData.get("type").equalsIgnoreCase("repeat");
            int batchWidth = 0;

            if (!elements.isEmpty()) {
                if (visible) { // is section needed
                    WaUiMetadata subsec = getMetaData(SUBSEC, blockData.get("name"), ++order, ++uiNum);
                    if (blockRepeat) {
                        subsec.setQuestionGroupSeqNbr(++blk);
                        subsec.setBlockNm("BLOCK_" + blk);
                        batchWidth = 100 / elements.size();
                    }
                    uiMetadata.add(subsec);
                }

                for (Dictionary<String, String> qmData : elements) {
                    WaUiMetadata ui = new WaUiMetadata();

                    String identifier = qmData.get("identifier");
                    WaQuestion q = questions.get(identifier);
                    Long groupId = groupMap.get(identifier);

                    String fieldSize = Optional.ofNullable(q.getFieldSize()).orElse("");
                    ui.setFieldSize(fieldSize.length() > 2 ? null : fieldSize);

                    long componentUid = q.getNbsUiComponentUid();
                    String dataType = q.getDataType();

                    if (groupId == null) {
                        if (componentUid == CODED) {
                            componentUid = fieldSize.length() > 2 ? TEXTAREA : INPUT;
                            dataType = "TEXT";
                        }
                    } else {
                        componentUid = CODED;
                        dataType = "CODED";
                    }

                    ui.setNbsUiComponentUid(componentUid);
                    ui.setQuestionLabel(trunkEncode(qmData.get("name"), 300));
                    ui.setQuestionToolTip(trunkEncode(qmData.get("desc_txt"), 2000));
                    ui.setOrderNbr("T".equalsIgnoreCase(q.getStandardNndIndCd()) ? 0 : ++order);
                    ui.setEnableInd("T");
                    ui.setDisplayInd("T");
                    ui.setRequiredInd("R".equals(q.getQuestionRequiredNnd()) ? "T" : "F");

                    ui.setAddTime(Instant.now());
                    ui.setAddUserId(PB_USER);
                    ui.setLastChgTime(Instant.now());
                    ui.setLastChgUserId(PB_USER);

                    ui.setVersionCtrlNbr(q.getVersionCtrlNbr());

                    ui.setMask(q.getMask());
                    ui.setEntryMethod(q.getEntryMethod());
                    ui.setCodeSetGroupId(groupId);

                    ui.setDataLocation(q.getDataLocation());
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

                    ui.setQuestionNm(trunkEncode(qmData.get("name"), 50));

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

    private List<WaNndMetadata> convert(Collection<WaQuestion> questions) {
        List<WaNndMetadata> nndMetadata = new ArrayList<>();

        questions.stream().filter(q -> !ENTRY_SYSTEM.equals(q.getEntryMethod())).forEach(question -> {
            WaNndMetadata nnd = new WaNndMetadata();

            nnd.setQuestionIdentifierNnd(question.getQuestionIdentifierNnd());
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

            nndMetadata.add(nnd);

        });
        return nndMetadata;
    }

    private List<WaNndMetadata> mergeNnd(List<WaNndMetadata> nbsData, List<WaNndMetadata> mmgData) {

        if (nbsData.isEmpty()) return mmgData;

        List<String> nbsIds = nbsData.stream().map(WaNndMetadata::getQuestionIdentifier).collect(Collectors.toList());
        List<WaNndMetadata> newData = mmgData.stream()
                .filter(nnd -> !nbsIds.contains(nnd.getQuestionIdentifier()))
                .collect(Collectors.toList());

        nbsData.addAll(newData);
        return nbsData;
    }

    private List<WaUiMetadata> mergeUi(List<WaUiMetadata> nbsUiData, List<WaUiMetadata> mmgUiData) {

        if (nbsUiData.isEmpty()) return mmgUiData;

        nbsUiData.sort(Comparator.comparing(WaUiMetadata::getOrderNbr));
        Map<Integer, WaUiMetadata> mmgNbrMap = mmgUiData.stream().collect(Collectors.toMap(WaUiMetadata::getOrderNbr, Function.identity(), (v1, v2) -> v1));

        Map<String, WaUiMetadata> nbsUiMap = nbsUiData.stream().collect(Collectors.toMap(WaUiMetadata::getQuestionIdentifier, Function.identity(), (v1, v2) -> v1));

        List<String> nbsIds = nbsUiData.stream().map(WaUiMetadata::getQuestionIdentifier).collect(Collectors.toList());
        List<WaUiMetadata> newUiData = mmgUiData.stream()
                .filter(ui -> !(ui.getDataType() == null || nbsIds.contains(ui.getQuestionIdentifier())))
                .collect(Collectors.toList());

        int maxNbr = nbsUiData.get(nbsUiData.size()-1).getOrderNbr();

        // guess the closest surrounding section
        for (WaUiMetadata nui: newUiData) {
            int nbr = nui.getOrderNbr();
            if (nbr > 0) {
                int nbsNbr = 0;
                while (nbr > 1) {
                    WaUiMetadata mmgNear = mmgNbrMap.get(--nbr);
                    if (mmgNear == null) continue;
                    WaUiMetadata nbsNear = nbsUiMap.get(mmgNear.getQuestionIdentifier());
                    if (nbsNear == null) continue;
                    nbsNbr = nbsNear.getOrderNbr();
                    break;
                }
                nui.setOrderNbr(nbsNbr > 0 ? nbsNbr : maxNbr + 1);
            }
            nbsUiData.add(nui);
        }

        // update code group data
        mmgUiData.stream().filter(mmgUi -> Objects.nonNull(mmgUi.getCodeSetGroupId())).forEach(mmgUI -> {
            WaUiMetadata nbsUI = nbsUiMap.get(mmgUI.getQuestionIdentifier());
            if (nbsUI != null && !Objects.equals(nbsUI.getCodeSetGroupId(), mmgUI.getCodeSetGroupId())) {
                nbsUI.setCodeSetGroupId(mmgUI.getCodeSetGroupId());
                nbsUI.setDataType(mmgUI.getDataType());
                nbsUI.setNbsUiComponentUid(mmgUI.getNbsUiComponentUid());
            }
        });

        return nbsUiData;
    }

    private WaUiMetadata getMetaData(Long componentUid, String label, int order, int uiNum) {
        WaUiMetadata sec = new WaUiMetadata();
        sec.setQuestionIdentifier("MMG_UI_" + uiNum);
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

    private static String trunkEncode(String str, int len) {
        if (str.length() > len) {
            str = str.split("\\.")[0];
            if (str.length() > len) {
                str = str.substring(0, len);
            }
        }
        return str.replaceAll("[\"’]", "'");
    }
}

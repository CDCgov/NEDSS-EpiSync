package gov.cdc.episync.pagebuilder.nbs;

import gov.cdc.episync.framework.EpisyncExtractResult;
import gov.cdc.episync.framework.EpisyncExtractor;
import gov.cdc.episync.framework.EpisyncWriter;
import gov.cdc.nbs.questionbank.entity.odse.WaQuestion;
import gov.cdc.nbs.questionbank.entity.odse.WaRuleMetadata;
import gov.cdc.nbs.questionbank.entity.odse.WaTemplate;
import gov.cdc.nbs.questionbank.entity.odse.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.srte.*;
import gov.cdc.nbs.questionbank.service.PageService;
import gov.cdc.nbs.questionbank.service.ValueSetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class NbsPageExtractor implements EpisyncExtractor<Long> {
    private final PageService pageService;
    private final ValueSetService valueSetService;
    private final EpisyncWriter<NbsPage> pageWriter;

    private static final String JURISDICTION_CODE = "S_JURDIC_C";
    private static final String PROGRAM_AREA_CODE = "S_PROGRA_C";
    private static final String CONDITION_CODE = "PHC_TYPE";

    Logger logger = LoggerFactory.getLogger(NbsPageExtractor.class);

    @Override
    public EpisyncExtractResult extractData(Long uid) throws Exception {

        logger.info("Start extracting NBS page: uid={}", uid);
        Optional<WaTemplate> tmp = pageService.getTemplate(uid);

        if (tmp.isPresent()) {
            WaTemplate source = tmp.get();
            NbsPage page = buildPageData(source);

            File newFile = pageWriter.writeToFile(uid + ".json", page);

            logger.info("Successfully extracted NBS page: {} (uid={})", page.getName(), uid);
            String msg = "Successfully extracted. Page: " + page.getName() + " (uid=" + uid + ")";
            return new EpisyncExtractResult(EpisyncExtractResult.ExtractResultCode.SUCCESS, msg, newFile.toURI());
        } else {
            logger.warn("Not found NBS page with uid={}", uid);
            throw new NoSuchObjectException("No page with uid=" + uid);
        }
    }

    NbsPage buildPageData(WaTemplate source) {
        NbsPage page = new NbsPage();

        page.setId(source.getWaTemplateUid());
        page.setType(source.getTemplateType());
        page.setName(source.getTemplateNm());
        page.setDescription(source.getDescTxt());

        List<WaUiMetadata> uiMetadata = pageService.findUiByTemplateUid(page.getId());
        Set<Long> groupIds = uiMetadata.stream().map(WaUiMetadata::getCodeSetGroupId)
                .filter(Objects::nonNull).collect(Collectors.toSet());

        buildBlockData(page, uiMetadata, groupIds);
        buildValueSetData(page, groupIds);
        buildRuleData(page);

        return page;
    }

    private void buildBlockData(NbsPage page, List<WaUiMetadata> uiMetadata, Collection<Long> groupIds) {
        logger.info("Start extracting questions and UI metadata");

        Long uid = page.getId();

        Map<String, WaQuestion> questionMap = pageService.findByIdentifiers(
                        uiMetadata.stream().map(WaUiMetadata::getQuestionIdentifier).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(WaQuestion::getQuestionIdentifier, Function.identity(), (v1, v2) -> v1));

        List<CodesetGroupMetadata> groupData = valueSetService.findValueSetGroupByGroupIds(groupIds);
        Map<Long, String> groupMap = groupData.stream()
                .collect(Collectors.toMap(CodesetGroupMetadata::getCodeSetGroupId, CodesetGroupMetadata::getCodeSetNm));

        List<NbsBlock> blocks = new ArrayList<>();
        List<NbsQuestion> pageElements = new ArrayList<>();
        List<NbsQuestion> elements = new ArrayList<>();

        Stack<WaUiMetadata> bstack = new Stack<>();
        WaUiMetadata lastBlock = null;
        NbsBlock block = null;
        long parentId = uid;
        for (WaUiMetadata ui: uiMetadata) {
            long compId = ui.getNbsUiComponentUid();
            NbsComp comp = NbsComp.getComp(compId);
            if (Objects.isNull(comp)) continue; // ignore line separators, etc, for now
            if (comp.getLevel() < 4) { // page, tab, section, subsection
                block = getNbsBlock(ui);

                if (lastBlock != null) {
                    parentId = lastBlock.getWaUiMetadataUid();
                    while (comp.getLevel() <= NbsComp.getComp(lastBlock.getNbsUiComponentUid()).getLevel()) {
                        lastBlock = bstack.pop();
                        parentId = bstack.peek().getWaUiMetadataUid();
                        if (comp.getLevel() == NbsComp.getComp(lastBlock.getNbsUiComponentUid()).getLevel()) break;
                    }
                }
                block.setParent(parentId);
                block.setType(comp.getType());
                blocks.add(block);
                lastBlock = bstack.push(ui);

                if (comp.getLevel() == 3) { // subsection
                    elements = new ArrayList<>();
                    block.setElements(elements);
                }
            } else {
                NbsQuestion elem = getNbsQuestion(ui, questionMap);
                elem.setParent(block != null ? block.getId() : uid);
                Optional.ofNullable(ui.getCodeSetGroupId()).ifPresent(id -> elem.setValueSetCode(groupMap.get(id)));

                if (elem.getOrder() > 0) {
                    elements.add(elem);
                } else {
                    pageElements.add(elem);
                }
            }
        }

        if (!pageElements.isEmpty()) {
            page.setElements(pageElements);
        }

        if (!blocks.isEmpty()) {
            page.setBlocks(blocks);
            long nelem = blocks.stream().filter(b -> Objects.nonNull(b.getElements())).mapToLong(b -> b.getElements().size()).sum();
            logger.info("Successfully extracted UI metadata: {} questions in {} blocks", nelem, blocks.size());
        } else {
            logger.info("No UI metadata found for NBS page: {} (uid={})", page.getName(), page.getId());
        }
    }

    private void buildValueSetData(NbsPage page, Collection<Long> groupIds) {
        logger.info("Start extracting value sets metadata");

        if (groupIds.isEmpty()) {
            logger.info("No value sets found for NBS page: {} (uid={})", page.getName(), page.getId());
            return;
        }

        List<Codeset> codesets = valueSetService.findCodesetsByGroupIds(groupIds);
        if (!codesets.isEmpty()) {
            List<CodeValueGeneral> values = valueSetService.findConceptsByCodes(
                    codesets.stream().map(Codeset::getCodeSetNm).collect(Collectors.toList()));

            Map<String, List<CodeValueGeneral>> conceptMap = values.stream()
                    .collect(groupingBy(CodeValueGeneral::getCodeSetNm));

            List<NbsCodeset> valueSets = new ArrayList<>();
            for (Codeset cs : codesets) {
                final String codeSetName = cs.getCodeSetNm();
                List<NbsConcept> concepts = new ArrayList<>();
                if (!conceptMap.containsKey(codeSetName)) {
                    switch (codeSetName) {
                        case JURISDICTION_CODE:
                            List<JurisdictionCode> jdCodes = valueSetService.getJurisdictionCodes(codeSetName);
                            jdCodes.forEach(val -> concepts.add(getNbsConcept(val)));
                            break;
                        case PROGRAM_AREA_CODE:
                            List<ProgramAreaCode> paCodes = valueSetService.getProgramAreaCodes(codeSetName);
                            paCodes.forEach(val -> concepts.add(getNbsConcept(val)));
                            break;
                        case CONDITION_CODE:
                            List<ConditionCode> codes = valueSetService.getConditionCodes(codeSetName);
                            codes.forEach(val -> concepts.add(getNbsConcept(val)));
                            break;
                        default:
                            continue;
                    }
                } else {
                    conceptMap.get(codeSetName).forEach(val -> concepts.add(getNbsConcept(val)));
                }
                NbsCodeset ncs = getNbsCodeset(cs);
                ncs.setConceptsCount(concepts.size());
                ncs.setConcepts(concepts);

                valueSets.add(ncs);
            }
            page.setValueSets(valueSets);
            logger.info("Successfully extracted {} value sets", valueSets.size());
        } else {
            logger.info("No value sets found for NBS page: {} (uid={})", page.getName(), page.getId());
        }
    }

    private void buildRuleData(NbsPage page) {
        logger.info("Start extracting rules metadata");

        List<NbsRule> rules = new ArrayList<>();

        List<WaRuleMetadata> ruleData = pageService.findRulesByTemplateUid(page.getId());
        ruleData.forEach(rdata -> rules.add(getNbsRule(rdata)));

        if (!rules.isEmpty()) {
            page.setRules(rules);
            logger.info("Successfully extracted {} rules", rules.size());
        } else {
            logger.info("No rules found for NBS page: {} (uid={})", page.getName(), page.getId());
        }
    }

    private NbsBlock getNbsBlock(WaUiMetadata ui) {
        NbsBlock block = new NbsBlock();

        block.setId(ui.getWaUiMetadataUid());
        block.setOrder(ui.getOrderNbr());
        block.setName(ui.getQuestionLabel());
        block.setRequired("T".equals(ui.getRequiredInd()));
        block.setGroupSeq(ui.getQuestionGroupSeqNbr());
        block.setBlockName(ui.getBlockNm());
        block.setVersion(ui.getVersionCtrlNbr());

        return block;
    }

    private NbsQuestion getNbsQuestion(WaUiMetadata ui, Map<String, WaQuestion> questionMap) {
        Optional<WaQuestion> question = Optional.ofNullable(questionMap.get(ui.getQuestionIdentifier()));
        NbsQuestion elem = new NbsQuestion();
        NbsQuestion.Mappings mapp = new NbsQuestion.Mappings();
        NbsQuestion.Nnd nnd = new NbsQuestion.Nnd();
        mapp.setNnd(nnd);
        elem.setMappings(mapp);

        elem.setId(ui.getWaUiMetadataUid());
        elem.setOrder(ui.getOrderNbr());
        elem.setLabel(ui.getQuestionLabel());
        elem.setName(ui.getQuestionNm());
        elem.setDescription(ui.getDescTxt());
        elem.setTooltip(ui.getQuestionToolTip());

        elem.setType(NbsComp.getComp(ui.getNbsUiComponentUid()).getType());
        elem.setDataType(ui.getDataType());
        elem.setIdentifier(ui.getQuestionIdentifier());
        elem.setRelatedElement(ui.getQuestionUnitIdentifier());
        elem.setParentElement(ui.getUnitParentIdentifier());
        elem.setDataLocation(ui.getDataLocation());

        elem.setSize(ui.getFieldSize());
        elem.setMax(ui.getMaxValue());
        elem.setMax(ui.getMinValue());
        elem.setVersion(ui.getVersionCtrlNbr());

        elem.setOid(ui.getQuestionOid());
        elem.setOidSystem(ui.getQuestionOidSystemTxt());
        elem.setStandard("T".equals(ui.getStandardQuestionIndCd()));
        elem.setEntryMethod(ui.getEntryMethod());
        elem.setQuestionType(ui.getQuestionType());

        Optional.ofNullable(ui.getOtherValueIndCd()).ifPresent(val -> elem.setOtherValue("T".equals(val)));
        elem.setGroupSeq(ui.getQuestionGroupSeqNbr());
        elem.setGroupName(ui.getGroupNm());
        elem.setSubGroupName(ui.getSubGroupNm());
        elem.setBlockName(ui.getBlockNm());
        elem.setBatchHeader(ui.getBatchTableHeader());
        elem.setBatchColWidth(ui.getBatchTableColumnWidth());

        question.ifPresent(q -> {
            elem.setNndMsg("T".equals(q.getNndMsgInd()));
            elem.setRdbTable(q.getRdbTableNm());
            elem.setRdbColumn(q.getRdbColumnNm());

            nnd.setIdentifier(q.getQuestionIdentifierNnd());
            nnd.setLegacyIdentifier(q.getLegacyQuestionIdentifier());
            nnd.setStandard("T".equals(q.getStandardNndIndCd()));
            nnd.setHl7Segment(q.getHl7SegmentField());
            nnd.setRequired(q.getQuestionRequiredNnd());
            nnd.setDataType(q.getQuestionDataTypeNnd());
            nnd.setOrderGroup(q.getOrderGroupId());
        });

        return elem;
    }

    private NbsCodeset getNbsCodeset(Codeset cs) {
        NbsCodeset ncs = new NbsCodeset();

        ncs.setName(cs.getCodeSetNm());
        ncs.setAssigningAuthority(cs.getAssigningAuthorityCd());
        ncs.setAssigningAuthorityDesc(cs.getAssigningAuthorityDescTxt());
        ncs.setDescription(cs.getCodeSetDescTxt());
        ncs.setValueSetOid(cs.getValueSetOid());
        ncs.setValueSetName(cs.getValueSetNm());
        ncs.setValueSetCode(cs.getValueSetCode());
        ncs.setValueSeType(cs.getValueSetTypeCd());
        ncs.setModifiable("Y".equals(cs.getModifiableInd()));
        ncs.setVersion(cs.getSourceVersionTxt());
        Optional.ofNullable(cs.getEffectiveFromTime()).ifPresent(time -> ncs.setEffectiveFrom(LocalDateTime.ofInstant(time, ZoneOffset.UTC).toString()));
        Optional.ofNullable(cs.getValueSetStatusTime()).ifPresent(time -> ncs.setStatusDate(LocalDateTime.ofInstant(time, ZoneOffset.UTC).toString()));

        return ncs;
    }

    private NbsConcept getNbsConcept(CodeValueGeneral val) {
        NbsConcept concept = new NbsConcept();

        concept.setCode(val.getCode());
        concept.setDescription(val.getCodeDescTxt());
        concept.setShortDesc(val.getCodeShortDescTxt());
        concept.setCodeSystem(val.getCodeSystemCd());
        concept.setCodeSystemDesc(val.getCodeSystemDescTxt());
        concept.setModifiable("Y".equals(val.getModifiableInd()));
        concept.setConceptType(val.getConceptTypeCd());
        concept.setConceptCode(val.getConceptCode());
        concept.setConceptName(val.getConceptNm());
        concept.setPreferredName(val.getConceptPreferredNm());
        concept.setVersion(val.getCodeSystemVersionNbr());
        Optional.ofNullable(val.getEffectiveFromTime()).ifPresent(time -> concept.setEffectiveFrom(LocalDateTime.ofInstant(time, ZoneOffset.UTC).toString()));
        Optional.ofNullable(val.getStatusTime()).ifPresent(time -> concept.setStatusDate(LocalDateTime.ofInstant(time, ZoneOffset.UTC).toString()));
        return concept;
    }

    private NbsConcept getNbsConcept(JurisdictionCode val) {
        NbsConcept concept = new NbsConcept();

        concept.setCode(val.getCode());
        concept.setDescription(val.getCodeDescTxt());
        concept.setShortDesc(val.getCodeShortDescTxt());
        concept.setCodeSystem(val.getCodeSystemCd());
        concept.setCodeSystemDesc(val.getCodeSystemDescTxt());
        concept.setModifiable("Y".equals(val.getModifiableInd()));
        concept.setConceptType(val.getTypeCd());
        concept.setConceptCode(val.getCode());
        Optional.ofNullable(val.getEffectiveFromTime()).ifPresent(time -> concept.setEffectiveFrom(LocalDateTime.ofInstant(time, ZoneOffset.UTC).toString()));
        Optional.ofNullable(val.getStatusTime()).ifPresent(time -> concept.setStatusDate(LocalDateTime.ofInstant(time, ZoneOffset.UTC).toString()));
        return concept;
    }

    private NbsConcept getNbsConcept(ProgramAreaCode val) {
        NbsConcept concept = new NbsConcept();

        concept.setCode(val.getProgAreaCd());
        concept.setDescription(val.getProgAreaDescTxt());
        concept.setShortDesc(val.getProgAreaDescTxt());
        concept.setConceptCode(val.getProgAreaCd());
        Optional.ofNullable(val.getStatusTime()).ifPresent(time -> concept.setStatusDate(LocalDateTime.ofInstant(time, ZoneOffset.UTC).toString()));
        return concept;
    }

    private NbsConcept getNbsConcept(ConditionCode val) {
        NbsConcept concept = new NbsConcept();

        concept.setCode(val.getConditionCd());
        concept.setDescription(val.getConditionDescTxt());
        concept.setShortDesc(val.getConditionShortNm());
        concept.setCodeSystem(val.getCodeSystemCd());
        concept.setCodeSystemDesc(val.getCodeSystemDescTxt());
        concept.setModifiable("Y".equals(val.getModifiableInd()));
        concept.setConceptCode(val.getConditionCd());
        concept.setConceptName(val.getConditionShortNm());
        Optional.ofNullable(val.getEffectiveFromTime()).ifPresent(time -> concept.setEffectiveFrom(LocalDateTime.ofInstant(time, ZoneOffset.UTC).toString()));
        Optional.ofNullable(val.getStatusTime()).ifPresent(time -> concept.setStatusDate(LocalDateTime.ofInstant(time, ZoneOffset.UTC).toString()));
        return concept;
    }


    private NbsRule getNbsRule(WaRuleMetadata rdata) {
        NbsRule rule = new NbsRule();

        rule.setId(rdata.getWaRuleMetadataUid());
        rule.setCode(rdata.getRuleCd());
        rule.setExpression(rdata.getRuleExpression());
        rule.setErrorMsg(rdata.getErrMsgTxt());
        rule.setSourceIdentifier(rdata.getSourceQuestionIdentifier());
        rule.setTargetIdentifier(rdata.getTargetQuestionIdentifier());
        rule.setDescription(rdata.getRuleDescTxt());
        rule.setUserRuleId(rdata.getUserRuleId());
        rule.setLogic(rdata.getLogic());
        rule.setSourceValues(rdata.getSourceValues());
        rule.setTargetType(rdata.getTargetType());
        rule.setFunctionName(rdata.getJavascriptFunctionNm());
        rule.setFunction(rdata.getJavascriptFunction());
        return rule;
    }
}

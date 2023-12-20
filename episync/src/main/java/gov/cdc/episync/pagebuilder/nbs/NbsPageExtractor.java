package gov.cdc.episync.pagebuilder.nbs;

import com.fasterxml.jackson.databind.ObjectWriter;
import gov.cdc.episync.framework.Episync;
import gov.cdc.episync.framework.EpisyncExtractResult;
import gov.cdc.episync.framework.EpisyncExtractor;
import gov.cdc.nbs.questionbank.entity.odse.WaQuestion;
import gov.cdc.nbs.questionbank.entity.odse.WaTemplate;
import gov.cdc.nbs.questionbank.entity.odse.WaUiMetadata;
import gov.cdc.nbs.questionbank.service.PageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.rmi.NoSuchObjectException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NbsPageExtractor implements EpisyncExtractor<Long> {
    private final PageService pageService;

    Logger logger = LoggerFactory.getLogger(NbsPageExtractor.class);

    @Override
    public EpisyncExtractResult extractData(Long uid) throws Exception {

        Optional<WaTemplate> tmp = pageService.getTemplate(uid);

        if (tmp.isPresent()) {
            WaTemplate source = tmp.get();
            NbsPage page = new NbsPage();

            page.setId(source.getWaTemplateUid());
            page.setType(source.getTemplateType());
            page.setName(source.getTemplateNm());
            page.setDescription(source.getDescTxt());

            List<WaUiMetadata> uiMetadata = pageService.findUiByTemplateUid(uid);
            List<WaQuestion> questions = pageService.findByIdentifiers(
                    uiMetadata.stream().map(WaUiMetadata::getQuestionIdentifier).collect(Collectors.toList()));
            Map<String, WaQuestion> questionMap = questions.stream()
                    .collect(Collectors.toMap(WaQuestion::getQuestionIdentifier, Function.identity(), (v1, v2) -> v1));

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
                if (comp.getLevel() < 4) {
                    block = new NbsBlock();
                    block.setId(ui.getWaUiMetadataUid());
                    if (lastBlock != null) {
                        parentId = lastBlock.getWaUiMetadataUid();
                        while (comp.getLevel() <= NbsComp.getComp(lastBlock.getNbsUiComponentUid()).getLevel()) {
                            lastBlock = bstack.pop();
                            parentId = bstack.peek().getWaUiMetadataUid();
                            if (comp.getLevel() == NbsComp.getComp(lastBlock.getNbsUiComponentUid()).getLevel()) break;
                        }
                    }

                    block.setParent(parentId);
                    block.setOrder(ui.getOrderNbr());
                    block.setName(ui.getQuestionLabel());
                    block.setType(comp.getType());
                    block.setRequired("T".equals(ui.getRequiredInd()));
                    block.setGroupSeq(ui.getQuestionGroupSeqNbr());
                    block.setBlockName(ui.getBlockNm());
                    block.setVersion(ui.getVersionCtrlNbr());

                    blocks.add(block);
                    lastBlock = bstack.push(ui);
                    if (comp.getLevel() == 3) {
                        elements = new ArrayList<>();
                        block.setElements(elements);
                    }
                } else {
                    NbsQuestion elem = getNbsQuestion(ui, questionMap);
                    elem.setParent(block != null ? block.id : uid);

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

            page.setBlocks(blocks);

            String fileName = uid + ".json";
            File newFile = new File(System.getProperty("user.home") + File.separator + fileName);
            ObjectWriter writer = Episync.getMapper().writerFor(NbsPage.class);
            writer.writeValue(newFile, page);

            String msg = "Successfully extracted. Page: " + source.getTemplateNm() +
                    " (Uid=" + uid + ")";
            return new EpisyncExtractResult(EpisyncExtractResult.ExtractResultCode.SUCCESS, msg, newFile.toURI());
        } else {
            throw new NoSuchObjectException("No page with Uid=" + uid);
        }
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

        elem.setValueSetCode(""); //TODO
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
}

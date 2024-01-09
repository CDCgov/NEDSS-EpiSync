package gov.cdc.episync.pagebuilder.nbs;

import gov.cdc.episync.framework.EpisyncExtractResult;
import gov.cdc.episync.framework.EpisyncWriter;
import gov.cdc.nbs.questionbank.entity.odse.WaQuestion;
import gov.cdc.nbs.questionbank.entity.odse.WaRuleMetadata;
import gov.cdc.nbs.questionbank.entity.odse.WaTemplate;
import gov.cdc.nbs.questionbank.entity.odse.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.srte.Codeset;
import gov.cdc.nbs.questionbank.entity.srte.CodesetGroupMetadata;
import gov.cdc.nbs.questionbank.service.PageService;
import gov.cdc.nbs.questionbank.service.ValueSetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.rmi.NoSuchObjectException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class NbsPageExtractorTest {
    @Mock
    private PageService pageService;

    @Mock
    private ValueSetService valueSetService;

    @Mock
    private EpisyncWriter<NbsPage> pageWriter;

    @InjectMocks
    private NbsPageExtractor nbsPageExtractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExtractDataTemplateNotFound() {
        Long uid = 1L;
        when(pageService.getTemplate(uid)).thenReturn(Optional.empty());
        assertThrows(NoSuchObjectException.class, () -> nbsPageExtractor.extractData(uid));
    }

    @Test
    void testExtractDataSuccess() throws Exception {
        Long uid = 1L;
        WaTemplate mockTemplate = createTemplate(uid);  // Populate with test data

        when(pageService.getTemplate(uid)).thenReturn(Optional.of(mockTemplate));
        when(pageService.findUiByTemplateUid(anyLong())).thenReturn(createStandardUiMetadataList());
        when(pageService.findByIdentifiers(anyCollection())).thenReturn(createStandardQuestionList());
        when(pageService.findRulesByTemplateUid(uid)).thenReturn(createStandardRulelist());

        when(valueSetService.findValueSetGroupByGroupIds(anyCollection())).thenReturn(createStandardGroupMetadataList());
        when(valueSetService.findCodesetsByGroupIds(anyCollection())).thenReturn(createStandardCodesetList());
        when(valueSetService.findConceptsByCodes(anyCollection())).thenReturn(createStandardCodeValueList());

        when(pageWriter.writeToFile(anyString(), any(NbsPage.class))).thenReturn(new File(uid.toString()));

        EpisyncExtractResult result = nbsPageExtractor.extractData(uid);
        assertNotNull(result);
        assertEquals(EpisyncExtractResult.ExtractResultCode.SUCCESS, result.getResultCode());
        assertEquals("Successfully extracted. Page: " + mockTemplate.getTemplateNm() + " (uid=" + uid + ")", result.getResultMessage());
    }

    @Test
    void testExtractDataWithEmptyMetadata() throws Exception {
        Long uid = 1L;
        WaTemplate mockTemplate = createTemplate(uid);  // Populate with test data

        when(pageService.getTemplate(uid)).thenReturn(Optional.of(mockTemplate));
        when(pageService.findUiByTemplateUid(anyLong())).thenReturn(Collections.emptyList());
        when(pageService.findByIdentifiers(anyCollection())).thenReturn(Collections.emptyList());
        when(pageService.findRulesByTemplateUid(uid)).thenReturn(Collections.emptyList());

        when(pageWriter.writeToFile(anyString(), any(NbsPage.class))).thenReturn(new File(uid.toString()));

        EpisyncExtractResult result = nbsPageExtractor.extractData(uid);
        assertNotNull(result);
        assertEquals(EpisyncExtractResult.ExtractResultCode.SUCCESS, result.getResultCode());
        assertEquals("Successfully extracted. Page: " + mockTemplate.getTemplateNm() + " (uid=" + uid + ")", result.getResultMessage());
    }

    @Test
    void testBuildPageDataWithAllMetadata() {
        Long uid = 1L;

        when(pageService.findUiByTemplateUid(anyLong())).thenReturn(createStandardUiMetadataList());
        when(pageService.findByIdentifiers(anyCollection())).thenReturn(createStandardQuestionList());
        when(pageService.findRulesByTemplateUid(uid)).thenReturn(createStandardRulelist());

        when(valueSetService.findValueSetGroupByGroupIds(anyCollection())).thenReturn(createStandardGroupMetadataList());
        when(valueSetService.findCodesetsByGroupIds(anyCollection())).thenReturn(createStandardCodesetList());
        when(valueSetService.findConceptsByCodes(anyCollection())).thenReturn(createStandardCodeValueList());

        NbsPage page = nbsPageExtractor.buildPageData(createTemplate(uid));
        assertEquals(page.getBlocks().size(), 6);
        assertEquals(page.getValueSets().size(), 2);
        assertEquals(page.getRules().size(), 2);
    }

    @Test
    void testBuildPageDataWithEmptyUiMetadata() {
        Long uid = 1L;

        when(pageService.findUiByTemplateUid(anyLong())).thenReturn(Collections.emptyList());
        when(pageService.findRulesByTemplateUid(uid)).thenReturn(createStandardRulelist());

        NbsPage page = nbsPageExtractor.buildPageData(createTemplate(uid));
        assertNull(page.getBlocks());
        assertNull(page.getValueSets());
        assertEquals(page.getRules().size(), 2);
    }

    @Test
    void testBuildPageDataWithMissingGroupIds() {
        Long uid = 1L;

        when(pageService.findUiByTemplateUid(anyLong())).thenReturn(createUiMetadataWithMissingGroupIds());
        when(pageService.findByIdentifiers(anyCollection())).thenReturn(createStandardQuestionList());
        when(pageService.findRulesByTemplateUid(uid)).thenReturn(createStandardRulelist());

        when(valueSetService.findValueSetGroupByGroupIds(anyCollection())).thenReturn(Collections.emptyList());
        when(valueSetService.findCodesetsByGroupIds(anyCollection())).thenReturn(createStandardCodesetList());

        NbsPage page = nbsPageExtractor.buildPageData(createTemplate(uid));
        assertEquals(page.getBlocks().size(), 4);
        assertNull(page.getValueSets());
        assertEquals(page.getRules().size(), 2);
    }

    @Test
    void testBuildPageDataWithUnrecognizedComponents() {
        Long uid = 1L;

        when(pageService.findUiByTemplateUid(anyLong())).thenReturn(createUiMetadataWithUnrecognizedComponents());
        when(pageService.findByIdentifiers(anyCollection())).thenReturn(createStandardQuestionList());
        when(pageService.findRulesByTemplateUid(uid)).thenReturn(createStandardRulelist());

        NbsPage page = nbsPageExtractor.buildPageData(createTemplate(uid));
        assertEquals(page.getBlocks().size(), 4);
        assertEquals(page.getRules().size(), 2);
    }

    @Test
    void testBuildPageDataWithMissingValueSets() {
        Long uid = 1L;

        when(pageService.findUiByTemplateUid(anyLong())).thenReturn(createStandardUiMetadataList());
        when(pageService.findByIdentifiers(anyCollection())).thenReturn(createStandardQuestionList());
        when(pageService.findRulesByTemplateUid(uid)).thenReturn(createStandardRulelist());

        NbsPage page = nbsPageExtractor.buildPageData(createTemplate(uid));
        assertEquals(page.getBlocks().size(), 6);
        assertNull(page.getValueSets());
        assertEquals(page.getRules().size(), 2);
    }

    private WaTemplate createTemplate(Long uid) {
        WaTemplate template = new WaTemplate();
        template.setWaTemplateUid(uid);
        template.setTemplateNm("Generic Test");
        template.setTemplateType("Published");
        return template;
    }

    private List<WaUiMetadata> createStandardUiMetadataList() {
        return new ArrayList<>(Arrays.asList(
                createUiMetadata(1002L, 1, "Page Generic", null),
                createUiMetadata(1010L, 2, "Tab 1", null),
                createUiMetadata(1015L, 3, "Section 1", null),
                createUiMetadata(1016L, 4, "Subsection 1", null),
                createUiMetadata(1008L, 5, "INV001", 10L),
                createUiMetadata(1008L, 6, "INV002", 20L),
                createUiMetadata(1010L, 7, "Tab 2", null),
                createUiMetadata(1015L, 8, "Section 2", null)));
    }

    private List<WaUiMetadata> createUiMetadataWithMissingGroupIds() {
        return new ArrayList<>(Arrays.asList(
                createUiMetadata(1002L, 1, "Page Generic", null),
                createUiMetadata(1010L, 2, "Tab 1", null),
                createUiMetadata(1015L, 3, "Section 1", null),
                createUiMetadata(1016L, 4, "Subsection 1", null),
                createUiMetadata(1008L, 5, "INV001", null),
                createUiMetadata(1008L, 6, "INV002", null)));
    }

    private List<WaUiMetadata> createUiMetadataWithUnrecognizedComponents() {
        return new ArrayList<>(Arrays.asList(
                createUiMetadata(1002L, 1, "Page Generic", null),
                createUiMetadata(1010L, 2, "Tab 1", null),
                createUiMetadata(1015L, 3, "Section 1", null),
                createUiMetadata(1016L, 4, "Subsection 1", null),
                createUiMetadata(1023L, 5, "Info", null)));
    }

    private List<WaQuestion> createStandardQuestionList() {
        return new ArrayList<>(Arrays.asList(
                createQuestion("INV001"),
                createQuestion("INV002")));
    }

    private List<CodesetGroupMetadata> createStandardGroupMetadataList() {
        return new ArrayList<>(Arrays.asList(
                createGroupMetadata(10L, "TEST_SET1"),
                createGroupMetadata(20L, "TEST_SET2")));
    }

    private List<Codeset> createStandardCodesetList() {
        return new ArrayList<>(Arrays.asList(
                createCodeset(createGroupMetadata(10L, "TEST_SET1")),
                createCodeset(createGroupMetadata(20L, "TEST_SET2"))));
    }

    private List<CodeValueGeneral> createStandardCodeValueList() {
        return new ArrayList<>(Arrays.asList(
                createCodeValue("101", "TEST_SET1"),
                createCodeValue("102", "TEST_SET1"),
                createCodeValue("103", "TEST_SET1"),
                createCodeValue("201", "TEST_SET2"),
                createCodeValue("202", "TEST_SET2"),
                createCodeValue("303", "TEST_SET3"),
                createCodeValue("304", "TEST_SET3")));
    }

    private List<WaRuleMetadata> createStandardRulelist() {
        return new ArrayList<>(Arrays.asList(
                createRuleMetadata("Enable", "INV001", "INV002", "INV001 ( Y ) = ^ E ( INV002 , INV003 ) "),
                createRuleMetadata("Date Compare", "INV004", "INV005", "INV004 <=  ^ DT ( INV005 ) ")));
    }

    private WaUiMetadata createUiMetadata(Long componentId, Integer order, String identifier, Long groupId) {
        WaUiMetadata ui = new WaUiMetadata();
        ui.setWaUiMetadataUid(Long.valueOf(order));
        ui.setNbsUiComponentUid(componentId);
        ui.setOrderNbr(order);
        ui.setQuestionIdentifier(identifier);
        ui.setCodeSetGroupId(groupId);
        return ui;
    }

    private WaQuestion createQuestion(String identifier) {
        WaQuestion q = new WaQuestion();
        q.setQuestionIdentifier(identifier);
        return q;
    }

    private CodeValueGeneral createCodeValue(String code, String codeSetName) {
        CodeValueGeneral val = new CodeValueGeneral();
        val.setCode(code);
        val.setCodeSetNm(codeSetName);
        return val;
    }

    private CodesetGroupMetadata createGroupMetadata(Long groupId, String codeSetName) {
        CodesetGroupMetadata groupData = new CodesetGroupMetadata();
        groupData.setCodeSetGroupId(groupId);
        groupData.setCodeSetNm(codeSetName);
        return groupData;
    }

    private Codeset createCodeset(CodesetGroupMetadata groupData) {
        Codeset cs = new Codeset();
        cs.setCodeSetGroup(groupData);
        cs.setCodeSetNm(groupData.getCodeSetNm());
        return cs;
    }

    private WaRuleMetadata createRuleMetadata(String code, String source, String target, String exp) {
        WaRuleMetadata rule = new WaRuleMetadata();
        rule.setRuleCd(code);
        rule.setSourceQuestionIdentifier(source);
        rule.setTargetQuestionIdentifier(target);
        rule.setRuleExpression(exp);
        return rule;
    }
}

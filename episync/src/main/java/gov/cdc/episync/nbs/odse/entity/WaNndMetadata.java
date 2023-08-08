package gov.cdc.episync.nbs.odse.entity;

import javax.persistence.*;

@Entity
@Table(name = "WA_NND_metadata")
public class WaNndMetadata {
    @Id
    @Column(name = "wa_nnd_metadata_uid")
    private Long waNndMetadataUid;

    @Column(name = "wa_template_uid")
    private Long waTemplateUid;

    @Column(name = "question_identifier_nnd")
    private String questionIdentifierNnd;

    @Column(name = "question_label_nnd")
    private String questionLabelNnd;

    @Column(name = "question_required_nnd")
    private char questionRequiredNnd;

    @Column(name = "question_data_type_nnd")
    private String questionDataTypeNnd;

    @Column(name = "hl7_segment_field")
    private String hl7SegmentField;

    @Column(name = "order_group_id")
    private String orderGroupId;

    @Column(name = "translation_table_nm")
    private String translationTableNm;

    @Column(name = "add_time")
    private LocalDateTime addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "last_chg_time")
    private LocalDateTime lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "record_status_cd")
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private LocalDateTime recordStatusTime;

    @Column(name = "question_identifier")
    private String questionIdentifier;

    @Column(name = "xml_path")
    private String xmlPath;

    @Column(name = "xml_tag")
    private String xmlTag;

    @Column(name = "xml_data_type")
    private String xmlDataType;

    @Column(name = "part_type_cd")
    private String partTypeCd;

    @Column(name = "repeat_group_seq_nbr")
    private Integer repeatGroupSeqNbr;

    @Column(name = "question_order_nnd")
    private Integer questionOrderNnd;

    @Column(name = "local_id")
    private String localId;

    @Column(name = "wa_ui_metadata_uid")
    private Long waUiMetadataUid;

    @Column(name = "question_map")
    private String questionMap;

    @Column(name = "indicator_cd")
    private String indicatorCd;

    public Long getWaNndMetadataUid() {
        return this.waNndMetadataUid;
    }

    public void setWaNndMetadataUid(Long waNndMetadataUid) {
        this.waNndMetadataUid = waNndMetadataUid;
    }

    public Long getWaTemplateUid() {
        return this.waTemplateUid;
    }

    public void setWaTemplateUid(Long waTemplateUid) {
        this.waTemplateUid = waTemplateUid;
    }

    public String getQuestionIdentifierNnd() {
        return this.questionIdentifierNnd;
    }

    public void setQuestionIdentifierNnd(String questionIdentifierNnd) {
        this.questionIdentifierNnd = questionIdentifierNnd;
    }

    public String getQuestionLabelNnd() {
        return this.questionLabelNnd;
    }

    public void setQuestionLabelNnd(String questionLabelNnd) {
        this.questionLabelNnd = questionLabelNnd;
    }

    public char getQuestionRequiredNnd() {
        return this.questionRequiredNnd;
    }

    public void setQuestionRequiredNnd(char questionRequiredNnd) {
        this.questionRequiredNnd = questionRequiredNnd;
    }

    public String getQuestionDataTypeNnd() {
        return this.questionDataTypeNnd;
    }

    public void setQuestionDataTypeNnd(String questionDataTypeNnd) {
        this.questionDataTypeNnd = questionDataTypeNnd;
    }

    public String getHl7SegmentField() {
        return this.hl7SegmentField;
    }

    public void setHl7SegmentField(String hl7SegmentField) {
        this.hl7SegmentField = hl7SegmentField;
    }

    public String getOrderGroupId() {
        return this.orderGroupId;
    }

    public void setOrderGroupId(String orderGroupId) {
        this.orderGroupId = orderGroupId;
    }

    public String getTranslationTableNm() {
        return this.translationTableNm;
    }

    public void setTranslationTableNm(String translationTableNm) {
        this.translationTableNm = translationTableNm;
    }

    public LocalDateTime getAddTime() {
        return this.addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public Long getAddUserId() {
        return this.addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }

    public LocalDateTime getLastChgTime() {
        return this.lastChgTime;
    }

    public void setLastChgTime(LocalDateTime lastChgTime) {
        this.lastChgTime = lastChgTime;
    }

    public Long getLastChgUserId() {
        return this.lastChgUserId;
    }

    public void setLastChgUserId(Long lastChgUserId) {
        this.lastChgUserId = lastChgUserId;
    }

    public String getRecordStatusCd() {
        return this.recordStatusCd;
    }

    public void setRecordStatusCd(String recordStatusCd) {
        this.recordStatusCd = recordStatusCd;
    }

    public LocalDateTime getRecordStatusTime() {
        return this.recordStatusTime;
    }

    public void setRecordStatusTime(LocalDateTime recordStatusTime) {
        this.recordStatusTime = recordStatusTime;
    }

    public String getQuestionIdentifier() {
        return this.questionIdentifier;
    }

    public void setQuestionIdentifier(String questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
    }

    public String getXmlPath() {
        return this.xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public String getXmlTag() {
        return this.xmlTag;
    }

    public void setXmlTag(String xmlTag) {
        this.xmlTag = xmlTag;
    }

    public String getXmlDataType() {
        return this.xmlDataType;
    }

    public void setXmlDataType(String xmlDataType) {
        this.xmlDataType = xmlDataType;
    }

    public String getPartTypeCd() {
        return this.partTypeCd;
    }

    public void setPartTypeCd(String partTypeCd) {
        this.partTypeCd = partTypeCd;
    }

    public Integer getRepeatGroupSeqNbr() {
        return this.repeatGroupSeqNbr;
    }

    public void setRepeatGroupSeqNbr(Integer repeatGroupSeqNbr) {
        this.repeatGroupSeqNbr = repeatGroupSeqNbr;
    }

    public Integer getQuestionOrderNnd() {
        return this.questionOrderNnd;
    }

    public void setQuestionOrderNnd(Integer questionOrderNnd) {
        this.questionOrderNnd = questionOrderNnd;
    }

    public String getLocalId() {
        return this.localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public Long getWaUiMetadataUid() {
        return this.waUiMetadataUid;
    }

    public void setWaUiMetadataUid(Long waUiMetadataUid) {
        this.waUiMetadataUid = waUiMetadataUid;
    }

    public String getQuestionMap() {
        return this.questionMap;
    }

    public void setQuestionMap(String questionMap) {
        this.questionMap = questionMap;
    }

    public String getIndicatorCd() {
        return this.indicatorCd;
    }

    public void setIndicatorCd(String indicatorCd) {
        this.indicatorCd = indicatorCd;
    }
}

package gov.cdc.episync.nbs.odse.entity;

import javax.persistence.*;

@Entity
@Table(name = "WA_question")
public class WaQuestion {
    @Id
    @Column(name = "wa_question_uid")
    private Long waQuestionUid;

    @Column(name = "code_set_group_id")
    private Long codeSetGroupId;

    @Column(name = "data_cd")
    private String dataCd;

    @Column(name = "data_location")
    private String dataLocation;

    @Column(name = "question_identifier")
    private String questionIdentifier;

    @Column(name = "question_oid")
    private String questionOid;

    @Column(name = "question_oid_system_txt")
    private String questionOidSystemTxt;

    @Column(name = "question_unit_identifier")
    private String questionUnitIdentifier;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "data_use_cd")
    private String dataUseCd;

    @Column(name = "question_label")
    private String questionLabel;

    @Column(name = "question_tool_tip")
    private String questionToolTip;

    @Column(name = "rdb_column_nm")
    private String rdbColumnNm;

    @Column(name = "part_type_cd")
    private String partTypeCd;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "version_ctrl_nbr")
    private Integer versionCtrlNbr;

    @Column(name = "unit_parent_identifier")
    private String unitParentIdentifier;

    @Column(name = "question_group_seq_nbr")
    private Integer questionGroupSeqNbr;

    @Column(name = "future_date_ind_cd")
    private null futureDateIndCd;

    @Column(name = "legacy_data_location")
    private String legacyDataLocation;

    @Column(name = "repeats_ind_cd")
    private null repeatsIndCd;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "add_time")
    private LocalDateTime addTime;

    @Column(name = "last_chg_time")
    private LocalDateTime lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id")
    private String localId;

    @Column(name = "question_nm")
    private String questionNm;

    @Column(name = "group_nm")
    private String groupNm;

    @Column(name = "sub_group_nm")
    private String subGroupNm;

    @Column(name = "desc_txt")
    private String descTxt;

    @Column(name = "mask")
    private String mask;

    @Column(name = "field_size")
    private String fieldSize;

    @Column(name = "rpt_admin_column_nm")
    private String rptAdminColumnNm;

    @Column(name = "nnd_msg_ind")
    private null nndMsgInd;

    @Column(name = "question_identifier_nnd")
    private String questionIdentifierNnd;

    @Column(name = "question_label_nnd")
    private String questionLabelNnd;

    @Column(name = "question_required_nnd")
    private null questionRequiredNnd;

    @Column(name = "question_data_type_nnd")
    private String questionDataTypeNnd;

    @Column(name = "hl7_segment_field")
    private String hl7SegmentField;

    @Column(name = "order_group_id")
    private String orderGroupId;

    @Column(name = "record_status_cd")
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private LocalDateTime recordStatusTime;

    @Column(name = "nbs_ui_component_uid")
    private Long nbsUiComponentUid;

    @Column(name = "standard_question_ind_cd")
    private null standardQuestionIndCd;

    @Column(name = "entry_method")
    private String entryMethod;

    @Column(name = "question_type")
    private String questionType;

    @Column(name = "admin_comment")
    private String adminComment;

    @Column(name = "rdb_table_nm")
    private String rdbTableNm;

    @Column(name = "user_defined_column_nm")
    private String userDefinedColumnNm;

    @Column(name = "min_value")
    private Long minValue;

    @Column(name = "max_value")
    private Long maxValue;

    @Column(name = "standard_nnd_ind_cd")
    private null standardNndIndCd;

    @Column(name = "legacy_question_identifier")
    private String legacyQuestionIdentifier;

    @Column(name = "unit_type_cd")
    private String unitTypeCd;

    @Column(name = "unit_value")
    private String unitValue;

    @Column(name = "other_value_ind_cd")
    private null otherValueIndCd;

    @Column(name = "source_nm")
    private String sourceNm;

    @Column(name = "coinfection_ind_cd")
    private null coinfectionIndCd;

    public Long getWaQuestionUid() {
        return this.waQuestionUid;
    }

    public void setWaQuestionUid(Long waQuestionUid) {
        this.waQuestionUid = waQuestionUid;
    }

    public Long getCodeSetGroupId() {
        return this.codeSetGroupId;
    }

    public void setCodeSetGroupId(Long codeSetGroupId) {
        this.codeSetGroupId = codeSetGroupId;
    }

    public String getDataCd() {
        return this.dataCd;
    }

    public void setDataCd(String dataCd) {
        this.dataCd = dataCd;
    }

    public String getDataLocation() {
        return this.dataLocation;
    }

    public void setDataLocation(String dataLocation) {
        this.dataLocation = dataLocation;
    }

    public String getQuestionIdentifier() {
        return this.questionIdentifier;
    }

    public void setQuestionIdentifier(String questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
    }

    public String getQuestionOid() {
        return this.questionOid;
    }

    public void setQuestionOid(String questionOid) {
        this.questionOid = questionOid;
    }

    public String getQuestionOidSystemTxt() {
        return this.questionOidSystemTxt;
    }

    public void setQuestionOidSystemTxt(String questionOidSystemTxt) {
        this.questionOidSystemTxt = questionOidSystemTxt;
    }

    public String getQuestionUnitIdentifier() {
        return this.questionUnitIdentifier;
    }

    public void setQuestionUnitIdentifier(String questionUnitIdentifier) {
        this.questionUnitIdentifier = questionUnitIdentifier;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataUseCd() {
        return this.dataUseCd;
    }

    public void setDataUseCd(String dataUseCd) {
        this.dataUseCd = dataUseCd;
    }

    public String getQuestionLabel() {
        return this.questionLabel;
    }

    public void setQuestionLabel(String questionLabel) {
        this.questionLabel = questionLabel;
    }

    public String getQuestionToolTip() {
        return this.questionToolTip;
    }

    public void setQuestionToolTip(String questionToolTip) {
        this.questionToolTip = questionToolTip;
    }

    public String getRdbColumnNm() {
        return this.rdbColumnNm;
    }

    public void setRdbColumnNm(String rdbColumnNm) {
        this.rdbColumnNm = rdbColumnNm;
    }

    public String getPartTypeCd() {
        return this.partTypeCd;
    }

    public void setPartTypeCd(String partTypeCd) {
        this.partTypeCd = partTypeCd;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getVersionCtrlNbr() {
        return this.versionCtrlNbr;
    }

    public void setVersionCtrlNbr(Integer versionCtrlNbr) {
        this.versionCtrlNbr = versionCtrlNbr;
    }

    public String getUnitParentIdentifier() {
        return this.unitParentIdentifier;
    }

    public void setUnitParentIdentifier(String unitParentIdentifier) {
        this.unitParentIdentifier = unitParentIdentifier;
    }

    public Integer getQuestionGroupSeqNbr() {
        return this.questionGroupSeqNbr;
    }

    public void setQuestionGroupSeqNbr(Integer questionGroupSeqNbr) {
        this.questionGroupSeqNbr = questionGroupSeqNbr;
    }

    public null getFutureDateIndCd() {
        return this.futureDateIndCd;
    }

    public void setFutureDateIndCd(null futureDateIndCd) {
        this.futureDateIndCd = futureDateIndCd;
    }

    public String getLegacyDataLocation() {
        return this.legacyDataLocation;
    }

    public void setLegacyDataLocation(String legacyDataLocation) {
        this.legacyDataLocation = legacyDataLocation;
    }

    public null getRepeatsIndCd() {
        return this.repeatsIndCd;
    }

    public void setRepeatsIndCd(null repeatsIndCd) {
        this.repeatsIndCd = repeatsIndCd;
    }

    public Long getAddUserId() {
        return this.addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }

    public LocalDateTime getAddTime() {
        return this.addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
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

    public String getLocalId() {
        return this.localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getQuestionNm() {
        return this.questionNm;
    }

    public void setQuestionNm(String questionNm) {
        this.questionNm = questionNm;
    }

    public String getGroupNm() {
        return this.groupNm;
    }

    public void setGroupNm(String groupNm) {
        this.groupNm = groupNm;
    }

    public String getSubGroupNm() {
        return this.subGroupNm;
    }

    public void setSubGroupNm(String subGroupNm) {
        this.subGroupNm = subGroupNm;
    }

    public String getDescTxt() {
        return this.descTxt;
    }

    public void setDescTxt(String descTxt) {
        this.descTxt = descTxt;
    }

    public String getMask() {
        return this.mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getFieldSize() {
        return this.fieldSize;
    }

    public void setFieldSize(String fieldSize) {
        this.fieldSize = fieldSize;
    }

    public String getRptAdminColumnNm() {
        return this.rptAdminColumnNm;
    }

    public void setRptAdminColumnNm(String rptAdminColumnNm) {
        this.rptAdminColumnNm = rptAdminColumnNm;
    }

    public null getNndMsgInd() {
        return this.nndMsgInd;
    }

    public void setNndMsgInd(null nndMsgInd) {
        this.nndMsgInd = nndMsgInd;
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

    public null getQuestionRequiredNnd() {
        return this.questionRequiredNnd;
    }

    public void setQuestionRequiredNnd(null questionRequiredNnd) {
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

    public Long getNbsUiComponentUid() {
        return this.nbsUiComponentUid;
    }

    public void setNbsUiComponentUid(Long nbsUiComponentUid) {
        this.nbsUiComponentUid = nbsUiComponentUid;
    }

    public null getStandardQuestionIndCd() {
        return this.standardQuestionIndCd;
    }

    public void setStandardQuestionIndCd(null standardQuestionIndCd) {
        this.standardQuestionIndCd = standardQuestionIndCd;
    }

    public String getEntryMethod() {
        return this.entryMethod;
    }

    public void setEntryMethod(String entryMethod) {
        this.entryMethod = entryMethod;
    }

    public String getQuestionType() {
        return this.questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAdminComment() {
        return this.adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public String getRdbTableNm() {
        return this.rdbTableNm;
    }

    public void setRdbTableNm(String rdbTableNm) {
        this.rdbTableNm = rdbTableNm;
    }

    public String getUserDefinedColumnNm() {
        return this.userDefinedColumnNm;
    }

    public void setUserDefinedColumnNm(String userDefinedColumnNm) {
        this.userDefinedColumnNm = userDefinedColumnNm;
    }

    public Long getMinValue() {
        return this.minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public null getStandardNndIndCd() {
        return this.standardNndIndCd;
    }

    public void setStandardNndIndCd(null standardNndIndCd) {
        this.standardNndIndCd = standardNndIndCd;
    }

    public String getLegacyQuestionIdentifier() {
        return this.legacyQuestionIdentifier;
    }

    public void setLegacyQuestionIdentifier(String legacyQuestionIdentifier) {
        this.legacyQuestionIdentifier = legacyQuestionIdentifier;
    }

    public String getUnitTypeCd() {
        return this.unitTypeCd;
    }

    public void setUnitTypeCd(String unitTypeCd) {
        this.unitTypeCd = unitTypeCd;
    }

    public String getUnitValue() {
        return this.unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public null getOtherValueIndCd() {
        return this.otherValueIndCd;
    }

    public void setOtherValueIndCd(null otherValueIndCd) {
        this.otherValueIndCd = otherValueIndCd;
    }

    public String getSourceNm() {
        return this.sourceNm;
    }

    public void setSourceNm(String sourceNm) {
        this.sourceNm = sourceNm;
    }

    public null getCoinfectionIndCd() {
        return this.coinfectionIndCd;
    }

    public void setCoinfectionIndCd(null coinfectionIndCd) {
        this.coinfectionIndCd = coinfectionIndCd;
    }
}

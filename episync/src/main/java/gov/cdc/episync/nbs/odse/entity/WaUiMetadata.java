package gov.cdc.episync.nbs.odse.entity;

import javax.persistence.*;

@Entity
@Table(name = "WA_UI_metadata")
public class WaUiMetadata {
    @Id
    @Column(name = "wa_ui_metadata_uid")
    private Long waUiMetadataUid;

    @Column(name = "wa_template_uid")
    private Long waTemplateUid;

    @Column(name = "nbs_ui_component_uid")
    private Long nbsUiComponentUid;

    @Column(name = "parent_uid")
    private Long parentUid;

    @Column(name = "question_label")
    private String questionLabel;

    @Column(name = "question_tool_tip")
    private String questionToolTip;

    @Column(name = "enable_ind")
    private String enableInd;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "display_ind")
    private String displayInd;

    @Column(name = "order_nbr")
    private Integer orderNbr;

    @Column(name = "required_ind")
    private String requiredInd;

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

    @Column(name = "max_length")
    private Long maxLength;

    @Column(name = "admin_comment")
    private String adminComment;

    @Column(name = "version_ctrl_nbr")
    private Integer versionCtrlNbr;

    @Column(name = "field_size")
    private String fieldSize;

    @Column(name = "future_date_ind_cd")
    private null futureDateIndCd;

    @Column(name = "local_id")
    private String localId;

    @Column(name = "code_set_group_id")
    private Long codeSetGroupId;

    @Column(name = "data_cd")
    private String dataCd;

    @Column(name = "data_location")
    private String dataLocation;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "data_use_cd")
    private String dataUseCd;

    @Column(name = "legacy_data_location")
    private String legacyDataLocation;

    @Column(name = "part_type_cd")
    private String partTypeCd;

    @Column(name = "question_group_seq_nbr")
    private Integer questionGroupSeqNbr;

    @Column(name = "question_identifier")
    private String questionIdentifier;

    @Column(name = "question_oid")
    private String questionOid;

    @Column(name = "question_oid_system_txt")
    private String questionOidSystemTxt;

    @Column(name = "question_unit_identifier")
    private String questionUnitIdentifier;

    @Column(name = "repeats_ind_cd")
    private null repeatsIndCd;

    @Column(name = "unit_parent_identifier")
    private String unitParentIdentifier;

    @Column(name = "group_nm")
    private String groupNm;

    @Column(name = "sub_group_nm")
    private String subGroupNm;

    @Column(name = "desc_txt")
    private String descTxt;

    @Column(name = "mask")
    private String mask;

    @Column(name = "entry_method")
    private String entryMethod;

    @Column(name = "question_type")
    private String questionType;

    @Column(name = "publish_ind_cd")
    private null publishIndCd;

    @Column(name = "min_value")
    private Long minValue;

    @Column(name = "max_value")
    private Long maxValue;

    @Column(name = "standard_question_ind_cd")
    private null standardQuestionIndCd;

    @Column(name = "standard_nnd_ind_cd")
    private null standardNndIndCd;

    @Column(name = "question_nm")
    private String questionNm;

    @Column(name = "unit_type_cd")
    private String unitTypeCd;

    @Column(name = "unit_value")
    private String unitValue;

    @Column(name = "other_value_ind_cd")
    private null otherValueIndCd;

    @Column(name = "batch_table_appear_ind_cd")
    private null batchTableAppearIndCd;

    @Column(name = "batch_table_header")
    private String batchTableHeader;

    @Column(name = "batch_table_column_width")
    private Integer batchTableColumnWidth;

    @Column(name = "coinfection_ind_cd")
    private null coinfectionIndCd;

    @Column(name = "block_nm")
    private String blockNm;

    public Long getWaUiMetadataUid() {
        return this.waUiMetadataUid;
    }

    public void setWaUiMetadataUid(Long waUiMetadataUid) {
        this.waUiMetadataUid = waUiMetadataUid;
    }

    public Long getWaTemplateUid() {
        return this.waTemplateUid;
    }

    public void setWaTemplateUid(Long waTemplateUid) {
        this.waTemplateUid = waTemplateUid;
    }

    public Long getNbsUiComponentUid() {
        return this.nbsUiComponentUid;
    }

    public void setNbsUiComponentUid(Long nbsUiComponentUid) {
        this.nbsUiComponentUid = nbsUiComponentUid;
    }

    public Long getParentUid() {
        return this.parentUid;
    }

    public void setParentUid(Long parentUid) {
        this.parentUid = parentUid;
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

    public String getEnableInd() {
        return this.enableInd;
    }

    public void setEnableInd(String enableInd) {
        this.enableInd = enableInd;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDisplayInd() {
        return this.displayInd;
    }

    public void setDisplayInd(String displayInd) {
        this.displayInd = displayInd;
    }

    public Integer getOrderNbr() {
        return this.orderNbr;
    }

    public void setOrderNbr(Integer orderNbr) {
        this.orderNbr = orderNbr;
    }

    public String getRequiredInd() {
        return this.requiredInd;
    }

    public void setRequiredInd(String requiredInd) {
        this.requiredInd = requiredInd;
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

    public Long getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(Long maxLength) {
        this.maxLength = maxLength;
    }

    public String getAdminComment() {
        return this.adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public Integer getVersionCtrlNbr() {
        return this.versionCtrlNbr;
    }

    public void setVersionCtrlNbr(Integer versionCtrlNbr) {
        this.versionCtrlNbr = versionCtrlNbr;
    }

    public String getFieldSize() {
        return this.fieldSize;
    }

    public void setFieldSize(String fieldSize) {
        this.fieldSize = fieldSize;
    }

    public null getFutureDateIndCd() {
        return this.futureDateIndCd;
    }

    public void setFutureDateIndCd(null futureDateIndCd) {
        this.futureDateIndCd = futureDateIndCd;
    }

    public String getLocalId() {
        return this.localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
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

    public String getLegacyDataLocation() {
        return this.legacyDataLocation;
    }

    public void setLegacyDataLocation(String legacyDataLocation) {
        this.legacyDataLocation = legacyDataLocation;
    }

    public String getPartTypeCd() {
        return this.partTypeCd;
    }

    public void setPartTypeCd(String partTypeCd) {
        this.partTypeCd = partTypeCd;
    }

    public Integer getQuestionGroupSeqNbr() {
        return this.questionGroupSeqNbr;
    }

    public void setQuestionGroupSeqNbr(Integer questionGroupSeqNbr) {
        this.questionGroupSeqNbr = questionGroupSeqNbr;
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

    public null getRepeatsIndCd() {
        return this.repeatsIndCd;
    }

    public void setRepeatsIndCd(null repeatsIndCd) {
        this.repeatsIndCd = repeatsIndCd;
    }

    public String getUnitParentIdentifier() {
        return this.unitParentIdentifier;
    }

    public void setUnitParentIdentifier(String unitParentIdentifier) {
        this.unitParentIdentifier = unitParentIdentifier;
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

    public null getPublishIndCd() {
        return this.publishIndCd;
    }

    public void setPublishIndCd(null publishIndCd) {
        this.publishIndCd = publishIndCd;
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

    public null getStandardQuestionIndCd() {
        return this.standardQuestionIndCd;
    }

    public void setStandardQuestionIndCd(null standardQuestionIndCd) {
        this.standardQuestionIndCd = standardQuestionIndCd;
    }

    public null getStandardNndIndCd() {
        return this.standardNndIndCd;
    }

    public void setStandardNndIndCd(null standardNndIndCd) {
        this.standardNndIndCd = standardNndIndCd;
    }

    public String getQuestionNm() {
        return this.questionNm;
    }

    public void setQuestionNm(String questionNm) {
        this.questionNm = questionNm;
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

    public null getBatchTableAppearIndCd() {
        return this.batchTableAppearIndCd;
    }

    public void setBatchTableAppearIndCd(null batchTableAppearIndCd) {
        this.batchTableAppearIndCd = batchTableAppearIndCd;
    }

    public String getBatchTableHeader() {
        return this.batchTableHeader;
    }

    public void setBatchTableHeader(String batchTableHeader) {
        this.batchTableHeader = batchTableHeader;
    }

    public Integer getBatchTableColumnWidth() {
        return this.batchTableColumnWidth;
    }

    public void setBatchTableColumnWidth(Integer batchTableColumnWidth) {
        this.batchTableColumnWidth = batchTableColumnWidth;
    }

    public null getCoinfectionIndCd() {
        return this.coinfectionIndCd;
    }

    public void setCoinfectionIndCd(null coinfectionIndCd) {
        this.coinfectionIndCd = coinfectionIndCd;
    }

    public String getBlockNm() {
        return this.blockNm;
    }

    public void setBlockNm(String blockNm) {
        this.blockNm = blockNm;
    }
}

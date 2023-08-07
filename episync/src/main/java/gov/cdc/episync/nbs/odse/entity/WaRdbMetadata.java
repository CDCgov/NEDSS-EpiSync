package gov.cdc.episync.nbs.odse.entity;

import javax.persistence.*;

@Entity
@Table(name = "WA_RDB_metadata")
public class WaRdbMetadata {
    @Id
    @Column(name = "wa_rdb_metadata_uid")
    private Long waRdbMetadataUid;

    @Column(name = "wa_template_uid")
    private Long waTemplateUid;

    @Column(name = "user_defined_column_nm")
    private String userDefinedColumnNm;

    @Column(name = "record_status_cd")
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private LocalDateTime recordStatusTime;

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

    @Column(name = "wa_ui_metadata_uid")
    private Long waUiMetadataUid;

    @Column(name = "rdb_table_nm")
    private String rdbTableNm;

    @Column(name = "rpt_admin_column_nm")
    private String rptAdminColumnNm;

    @Column(name = "rdb_column_nm")
    private String rdbColumnNm;

    @Column(name = "question_identifier")
    private String questionIdentifier;

    @Column(name = "block_pivot_nbr")
    private Integer blockPivotNbr;

    public Long getWaRdbMetadataUid() {
        return this.waRdbMetadataUid;
    }

    public void setWaRdbMetadataUid(Long waRdbMetadataUid) {
        this.waRdbMetadataUid = waRdbMetadataUid;
    }

    public Long getWaTemplateUid() {
        return this.waTemplateUid;
    }

    public void setWaTemplateUid(Long waTemplateUid) {
        this.waTemplateUid = waTemplateUid;
    }

    public String getUserDefinedColumnNm() {
        return this.userDefinedColumnNm;
    }

    public void setUserDefinedColumnNm(String userDefinedColumnNm) {
        this.userDefinedColumnNm = userDefinedColumnNm;
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

    public Long getWaUiMetadataUid() {
        return this.waUiMetadataUid;
    }

    public void setWaUiMetadataUid(Long waUiMetadataUid) {
        this.waUiMetadataUid = waUiMetadataUid;
    }

    public String getRdbTableNm() {
        return this.rdbTableNm;
    }

    public void setRdbTableNm(String rdbTableNm) {
        this.rdbTableNm = rdbTableNm;
    }

    public String getRptAdminColumnNm() {
        return this.rptAdminColumnNm;
    }

    public void setRptAdminColumnNm(String rptAdminColumnNm) {
        this.rptAdminColumnNm = rptAdminColumnNm;
    }

    public String getRdbColumnNm() {
        return this.rdbColumnNm;
    }

    public void setRdbColumnNm(String rdbColumnNm) {
        this.rdbColumnNm = rdbColumnNm;
    }

    public String getQuestionIdentifier() {
        return this.questionIdentifier;
    }

    public void setQuestionIdentifier(String questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
    }

    public Integer getBlockPivotNbr() {
        return this.blockPivotNbr;
    }

    public void setBlockPivotNbr(Integer blockPivotNbr) {
        this.blockPivotNbr = blockPivotNbr;
    }
}

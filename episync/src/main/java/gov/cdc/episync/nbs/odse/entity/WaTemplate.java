package gov.cdc.episync.nbs.odse.entity;

import javax.persistence.*;

@Entity
@Table(name = "WA_template")
public class WaTemplate {
    @Id
    @Column(name = "wa_template_uid")
    private Long waTemplateUid;

    @Column(name = "template_type")
    private String templateType;

    @Column(name = "xml_payload")
    private char xmlPayload;

    @Column(name = "publish_version_nbr")
    private Integer publishVersionNbr;

    @Column(name = "form_cd")
    private String formCd;

    @Column(name = "condition_cd")
    private String conditionCd;

    @Column(name = "bus_obj_type")
    private String busObjType;

    @Column(name = "datamart_nm")
    private String datamartNm;

    @Column(name = "record_status_cd")
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private LocalDateTime recordStatusTime;

    @Column(name = "last_chg_time")
    private LocalDateTime lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id")
    private String localId;

    @Column(name = "desc_txt")
    private String descTxt;

    @Column(name = "template_nm")
    private String templateNm;

    @Column(name = "publish_ind_cd")
    private char publishIndCd;

    @Column(name = "add_time")
    private LocalDateTime addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "nnd_entity_identifier")
    private String nndEntityIdentifier;

    @Column(name = "parent_template_uid")
    private Long parentTemplateUid;

    @Column(name = "source_nm")
    private String sourceNm;

    @Column(name = "template_version_nbr")
    private Integer templateVersionNbr;

    @Column(name = "version_note")
    private String versionNote;

    public Long getWaTemplateUid() {
        return this.waTemplateUid;
    }

    public void setWaTemplateUid(Long waTemplateUid) {
        this.waTemplateUid = waTemplateUid;
    }

    public String getTemplateType() {
        return this.templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public char getXmlPayload() {
        return this.xmlPayload;
    }

    public void setXmlPayload(char xmlPayload) {
        this.xmlPayload = xmlPayload;
    }

    public Integer getPublishVersionNbr() {
        return this.publishVersionNbr;
    }

    public void setPublishVersionNbr(Integer publishVersionNbr) {
        this.publishVersionNbr = publishVersionNbr;
    }

    public String getFormCd() {
        return this.formCd;
    }

    public void setFormCd(String formCd) {
        this.formCd = formCd;
    }

    public String getConditionCd() {
        return this.conditionCd;
    }

    public void setConditionCd(String conditionCd) {
        this.conditionCd = conditionCd;
    }

    public String getBusObjType() {
        return this.busObjType;
    }

    public void setBusObjType(String busObjType) {
        this.busObjType = busObjType;
    }

    public String getDatamartNm() {
        return this.datamartNm;
    }

    public void setDatamartNm(String datamartNm) {
        this.datamartNm = datamartNm;
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

    public String getDescTxt() {
        return this.descTxt;
    }

    public void setDescTxt(String descTxt) {
        this.descTxt = descTxt;
    }

    public String getTemplateNm() {
        return this.templateNm;
    }

    public void setTemplateNm(String templateNm) {
        this.templateNm = templateNm;
    }

    public char getPublishIndCd() {
        return this.publishIndCd;
    }

    public void setPublishIndCd(char publishIndCd) {
        this.publishIndCd = publishIndCd;
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

    public String getNndEntityIdentifier() {
        return this.nndEntityIdentifier;
    }

    public void setNndEntityIdentifier(String nndEntityIdentifier) {
        this.nndEntityIdentifier = nndEntityIdentifier;
    }

    public Long getParentTemplateUid() {
        return this.parentTemplateUid;
    }

    public void setParentTemplateUid(Long parentTemplateUid) {
        this.parentTemplateUid = parentTemplateUid;
    }

    public String getSourceNm() {
        return this.sourceNm;
    }

    public void setSourceNm(String sourceNm) {
        this.sourceNm = sourceNm;
    }

    public Integer getTemplateVersionNbr() {
        return this.templateVersionNbr;
    }

    public void setTemplateVersionNbr(Integer templateVersionNbr) {
        this.templateVersionNbr = templateVersionNbr;
    }

    public String getVersionNote() {
        return this.versionNote;
    }

    public void setVersionNote(String versionNote) {
        this.versionNote = versionNote;
    }
}

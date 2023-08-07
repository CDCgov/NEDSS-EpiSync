package gov.cdc.episync.nbs.srte.entity;

import javax.persistence.*;

@Entity
@Table(name = "Codeset")
public class Codeset {
    @Id
    @Column(name = "code_set_nm")
    private String codeSetNm;

    @Column(name = "assigning_authority_cd")
    private String assigningAuthorityCd;

    @Column(name = "assigning_authority_desc_txt")
    private String assigningAuthorityDescTxt;

    @Column(name = "code_set_desc_txt")
    private String codeSetDescTxt;

    @Id
    @Column(name = "class_cd")
    private String classCd;

    @Column(name = "effective_from_time")
    private LocalDateTime effectiveFromTime;

    @Column(name = "effective_to_time")
    private LocalDateTime effectiveToTime;

    @Column(name = "is_modifiable_ind")
    private null isModifiableInd;

    @Column(name = "nbs_uid")
    private Integer nbsUid;

    @Column(name = "source_version_txt")
    private String sourceVersionTxt;

    @Column(name = "source_domain_nm")
    private String sourceDomainNm;

    @Column(name = "status_cd")
    private String statusCd;

    @Column(name = "status_to_time")
    private LocalDateTime statusToTime;

    @Column(name = "code_set_group_id")
    private Long codeSetGroupId;

    @Column(name = "admin_comments")
    private String adminComments;

    @Column(name = "value_set_nm")
    private String valueSetNm;

    @Column(name = "ldf_picklist_ind_cd")
    private null ldfPicklistIndCd;

    @Column(name = "value_set_code")
    private String valueSetCode;

    @Column(name = "value_set_type_cd")
    private String valueSetTypeCd;

    @Column(name = "value_set_oid")
    private String valueSetOid;

    @Column(name = "value_set_status_cd")
    private String valueSetStatusCd;

    @Column(name = "value_set_status_time")
    private LocalDateTime valueSetStatusTime;

    @Column(name = "parent_is_cd")
    private Long parentIsCd;

    @Column(name = "add_time")
    private LocalDateTime addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    public String getCodeSetNm() {
        return this.codeSetNm;
    }

    public void setCodeSetNm(String codeSetNm) {
        this.codeSetNm = codeSetNm;
    }

    public String getAssigningAuthorityCd() {
        return this.assigningAuthorityCd;
    }

    public void setAssigningAuthorityCd(String assigningAuthorityCd) {
        this.assigningAuthorityCd = assigningAuthorityCd;
    }

    public String getAssigningAuthorityDescTxt() {
        return this.assigningAuthorityDescTxt;
    }

    public void setAssigningAuthorityDescTxt(String assigningAuthorityDescTxt) {
        this.assigningAuthorityDescTxt = assigningAuthorityDescTxt;
    }

    public String getCodeSetDescTxt() {
        return this.codeSetDescTxt;
    }

    public void setCodeSetDescTxt(String codeSetDescTxt) {
        this.codeSetDescTxt = codeSetDescTxt;
    }

    public String getClassCd() {
        return this.classCd;
    }

    public void setClassCd(String classCd) {
        this.classCd = classCd;
    }

    public LocalDateTime getEffectiveFromTime() {
        return this.effectiveFromTime;
    }

    public void setEffectiveFromTime(LocalDateTime effectiveFromTime) {
        this.effectiveFromTime = effectiveFromTime;
    }

    public LocalDateTime getEffectiveToTime() {
        return this.effectiveToTime;
    }

    public void setEffectiveToTime(LocalDateTime effectiveToTime) {
        this.effectiveToTime = effectiveToTime;
    }

    public null getIsModifiableInd() {
        return this.isModifiableInd;
    }

    public void setIsModifiableInd(null isModifiableInd) {
        this.isModifiableInd = isModifiableInd;
    }

    public Integer getNbsUid() {
        return this.nbsUid;
    }

    public void setNbsUid(Integer nbsUid) {
        this.nbsUid = nbsUid;
    }

    public String getSourceVersionTxt() {
        return this.sourceVersionTxt;
    }

    public void setSourceVersionTxt(String sourceVersionTxt) {
        this.sourceVersionTxt = sourceVersionTxt;
    }

    public String getSourceDomainNm() {
        return this.sourceDomainNm;
    }

    public void setSourceDomainNm(String sourceDomainNm) {
        this.sourceDomainNm = sourceDomainNm;
    }

    public String getStatusCd() {
        return this.statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public LocalDateTime getStatusToTime() {
        return this.statusToTime;
    }

    public void setStatusToTime(LocalDateTime statusToTime) {
        this.statusToTime = statusToTime;
    }

    public Long getCodeSetGroupId() {
        return this.codeSetGroupId;
    }

    public void setCodeSetGroupId(Long codeSetGroupId) {
        this.codeSetGroupId = codeSetGroupId;
    }

    public String getAdminComments() {
        return this.adminComments;
    }

    public void setAdminComments(String adminComments) {
        this.adminComments = adminComments;
    }

    public String getValueSetNm() {
        return this.valueSetNm;
    }

    public void setValueSetNm(String valueSetNm) {
        this.valueSetNm = valueSetNm;
    }

    public null getLdfPicklistIndCd() {
        return this.ldfPicklistIndCd;
    }

    public void setLdfPicklistIndCd(null ldfPicklistIndCd) {
        this.ldfPicklistIndCd = ldfPicklistIndCd;
    }

    public String getValueSetCode() {
        return this.valueSetCode;
    }

    public void setValueSetCode(String valueSetCode) {
        this.valueSetCode = valueSetCode;
    }

    public String getValueSetTypeCd() {
        return this.valueSetTypeCd;
    }

    public void setValueSetTypeCd(String valueSetTypeCd) {
        this.valueSetTypeCd = valueSetTypeCd;
    }

    public String getValueSetOid() {
        return this.valueSetOid;
    }

    public void setValueSetOid(String valueSetOid) {
        this.valueSetOid = valueSetOid;
    }

    public String getValueSetStatusCd() {
        return this.valueSetStatusCd;
    }

    public void setValueSetStatusCd(String valueSetStatusCd) {
        this.valueSetStatusCd = valueSetStatusCd;
    }

    public LocalDateTime getValueSetStatusTime() {
        return this.valueSetStatusTime;
    }

    public void setValueSetStatusTime(LocalDateTime valueSetStatusTime) {
        this.valueSetStatusTime = valueSetStatusTime;
    }

    public Long getParentIsCd() {
        return this.parentIsCd;
    }

    public void setParentIsCd(Long parentIsCd) {
        this.parentIsCd = parentIsCd;
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
}

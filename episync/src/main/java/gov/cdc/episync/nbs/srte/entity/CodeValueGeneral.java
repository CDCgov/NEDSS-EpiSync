package gov.cdc.episync.nbs.srte.entity;

import javax.persistence.*;

@Entity
@Table(name = "Code_value_general")
public class CodeValueGeneral {
    @Id
    @Column(name = "code_set_nm")
    private String codeSetNm;

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "code_desc_txt")
    private String codeDescTxt;

    @Column(name = "code_short_desc_txt")
    private String codeShortDescTxt;

    @Column(name = "code_system_cd")
    private String codeSystemCd;

    @Column(name = "code_system_desc_txt")
    private String codeSystemDescTxt;

    @Column(name = "effective_from_time")
    private LocalDateTime effectiveFromTime;

    @Column(name = "effective_to_time")
    private LocalDateTime effectiveToTime;

    @Column(name = "indent_level_nbr")
    private char indentLevelNbr;

    @Column(name = "is_modifiable_ind")
    private char isModifiableInd;

    @Column(name = "nbs_uid")
    private Integer nbsUid;

    @Column(name = "parent_is_cd")
    private String parentIsCd;

    @Column(name = "source_concept_id")
    private String sourceConceptId;

    @Column(name = "super_code_set_nm")
    private String superCodeSetNm;

    @Column(name = "super_code")
    private String superCode;

    @Column(name = "status_cd")
    private char statusCd;

    @Column(name = "status_time")
    private LocalDateTime statusTime;

    @Column(name = "concept_type_cd")
    private String conceptTypeCd;

    @Column(name = "concept_code")
    private String conceptCode;

    @Column(name = "concept_nm")
    private String conceptNm;

    @Column(name = "concept_preferred_nm")
    private String conceptPreferredNm;

    @Column(name = "concept_status_cd")
    private String conceptStatusCd;

    @Column(name = "concept_status_time")
    private LocalDateTime conceptStatusTime;

    @Column(name = "code_system_version_nbr")
    private String codeSystemVersionNbr;

    @Column(name = "concept_order_nbr")
    private Integer conceptOrderNbr;

    @Column(name = "admin_comments")
    private String adminComments;

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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeDescTxt() {
        return this.codeDescTxt;
    }

    public void setCodeDescTxt(String codeDescTxt) {
        this.codeDescTxt = codeDescTxt;
    }

    public String getCodeShortDescTxt() {
        return this.codeShortDescTxt;
    }

    public void setCodeShortDescTxt(String codeShortDescTxt) {
        this.codeShortDescTxt = codeShortDescTxt;
    }

    public String getCodeSystemCd() {
        return this.codeSystemCd;
    }

    public void setCodeSystemCd(String codeSystemCd) {
        this.codeSystemCd = codeSystemCd;
    }

    public String getCodeSystemDescTxt() {
        return this.codeSystemDescTxt;
    }

    public void setCodeSystemDescTxt(String codeSystemDescTxt) {
        this.codeSystemDescTxt = codeSystemDescTxt;
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

    public char getIndentLevelNbr() {
        return this.indentLevelNbr;
    }

    public void setIndentLevelNbr(char indentLevelNbr) {
        this.indentLevelNbr = indentLevelNbr;
    }

    public char getIsModifiableInd() {
        return this.isModifiableInd;
    }

    public void setIsModifiableInd(char isModifiableInd) {
        this.isModifiableInd = isModifiableInd;
    }

    public Integer getNbsUid() {
        return this.nbsUid;
    }

    public void setNbsUid(Integer nbsUid) {
        this.nbsUid = nbsUid;
    }

    public String getParentIsCd() {
        return this.parentIsCd;
    }

    public void setParentIsCd(String parentIsCd) {
        this.parentIsCd = parentIsCd;
    }

    public String getSourceConceptId() {
        return this.sourceConceptId;
    }

    public void setSourceConceptId(String sourceConceptId) {
        this.sourceConceptId = sourceConceptId;
    }

    public String getSuperCodeSetNm() {
        return this.superCodeSetNm;
    }

    public void setSuperCodeSetNm(String superCodeSetNm) {
        this.superCodeSetNm = superCodeSetNm;
    }

    public String getSuperCode() {
        return this.superCode;
    }

    public void setSuperCode(String superCode) {
        this.superCode = superCode;
    }

    public char getStatusCd() {
        return this.statusCd;
    }

    public void setStatusCd(char statusCd) {
        this.statusCd = statusCd;
    }

    public LocalDateTime getStatusTime() {
        return this.statusTime;
    }

    public void setStatusTime(LocalDateTime statusTime) {
        this.statusTime = statusTime;
    }

    public String getConceptTypeCd() {
        return this.conceptTypeCd;
    }

    public void setConceptTypeCd(String conceptTypeCd) {
        this.conceptTypeCd = conceptTypeCd;
    }

    public String getConceptCode() {
        return this.conceptCode;
    }

    public void setConceptCode(String conceptCode) {
        this.conceptCode = conceptCode;
    }

    public String getConceptNm() {
        return this.conceptNm;
    }

    public void setConceptNm(String conceptNm) {
        this.conceptNm = conceptNm;
    }

    public String getConceptPreferredNm() {
        return this.conceptPreferredNm;
    }

    public void setConceptPreferredNm(String conceptPreferredNm) {
        this.conceptPreferredNm = conceptPreferredNm;
    }

    public String getConceptStatusCd() {
        return this.conceptStatusCd;
    }

    public void setConceptStatusCd(String conceptStatusCd) {
        this.conceptStatusCd = conceptStatusCd;
    }

    public LocalDateTime getConceptStatusTime() {
        return this.conceptStatusTime;
    }

    public void setConceptStatusTime(LocalDateTime conceptStatusTime) {
        this.conceptStatusTime = conceptStatusTime;
    }

    public String getCodeSystemVersionNbr() {
        return this.codeSystemVersionNbr;
    }

    public void setCodeSystemVersionNbr(String codeSystemVersionNbr) {
        this.codeSystemVersionNbr = codeSystemVersionNbr;
    }

    public Integer getConceptOrderNbr() {
        return this.conceptOrderNbr;
    }

    public void setConceptOrderNbr(Integer conceptOrderNbr) {
        this.conceptOrderNbr = conceptOrderNbr;
    }

    public String getAdminComments() {
        return this.adminComments;
    }

    public void setAdminComments(String adminComments) {
        this.adminComments = adminComments;
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

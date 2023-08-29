package gov.cdc.nbs.questionbank.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "Codeset")
@IdClass(CodesetId.class)
public class Codeset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "code_set_nm", nullable = false)
    private String codeSetNm;

    @Column(name = "assigning_authority_cd")
    private String assigningAuthorityCd;

    @Column(name = "assigning_authority_desc_txt")
    private String assigningAuthorityDescTxt;

    @Column(name = "code_set_desc_txt")
    private String codeSetDescTxt;

    @Id
    @Column(name = "class_cd", nullable = false)
    private String classCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "is_modifiable_ind")
    private Boolean modifiableInd;

    @Column(name = "nbs_uid")
    private Integer nbsUid;

    @Column(name = "source_version_txt")
    private String sourceVersionTxt;

    @Column(name = "source_domain_nm")
    private String sourceDomainNm;

    @Column(name = "status_cd")
    private String statusCd;

    @Column(name = "status_to_time")
    private Instant statusToTime;

    @Column(name = "code_set_group_id")
    private Long codeSetGroupId;

    @Column(name = "admin_comments")
    private String adminComments;

    @Column(name = "value_set_nm")
    private String valueSetNm;

    @Column(name = "ldf_picklist_ind_cd")
    private String ldfPicklistIndCd;

    @Column(name = "value_set_code")
    private String valueSetCode;

    @Column(name = "value_set_type_cd")
    private String valueSetTypeCd;

    @Column(name = "value_set_oid")
    private String valueSetOid;

    @Column(name = "value_set_status_cd")
    private String valueSetStatusCd;

    @Column(name = "value_set_status_time")
    private Instant valueSetStatusTime;

    @Column(name = "parent_is_cd")
    private Long parentIsCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

}

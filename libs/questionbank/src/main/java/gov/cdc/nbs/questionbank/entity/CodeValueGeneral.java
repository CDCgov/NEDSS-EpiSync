package gov.cdc.nbs.questionbank.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "Code_value_general")
@IdClass(CodeValueGeneral.class)
public class CodeValueGeneral implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "code_set_nm", nullable = false)
    private String codeSetNm;

    @Id
    @Column(name = "code", nullable = false)
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
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "indent_level_nbr")
    private Integer indentLevelNbr;

    @Column(name = "is_modifiable_ind")
    private Boolean modifiableInd;

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
    private String statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

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
    private Instant conceptStatusTime;

    @Column(name = "code_system_version_nbr")
    private String codeSystemVersionNbr;

    @Column(name = "concept_order_nbr")
    private Integer conceptOrderNbr;

    @Column(name = "admin_comments")
    private String adminComments;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

}

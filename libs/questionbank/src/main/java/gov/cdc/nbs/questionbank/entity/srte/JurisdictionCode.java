package gov.cdc.nbs.questionbank.entity.srte;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "Jurisdiction_code")
public class JurisdictionCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "type_cd", nullable = false)
    private String typeCd;

    @Column(name = "assigning_authority_cd")
    private String assigningAuthorityCd;

    @Column(name = "assigning_authority_desc_txt")
    private String assigningAuthorityDescTxt;

    @Column(name = "code_desc_txt")
    private String codeDescTxt;

    @Column(name = "code_short_desc_txt")
    private String codeShortDescTxt;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "indent_level_nbr")
    private Integer indentLevelNbr;

    @Column(name = "is_modifiable_ind")
    private String modifiableInd;

    @Column(name = "parent_is_cd")
    private String parentIsCd;

    @Column(name = "state_domain_cd")
    private String stateDomainCd;

    @Column(name = "status_cd")
    private String statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "code_set_nm")
    private String codeSetNm;

    @Column(name = "code_seq_num")
    private Integer codeSeqNum;

    @Column(name = "nbs_uid")
    private Integer nbsUid;

    @Column(name = "source_concept_id")
    private String sourceConceptId;

    @Column(name = "code_system_cd")
    private String codeSystemCd;

    @Column(name = "code_system_desc_txt")
    private String codeSystemDescTxt;

    @Column(name = "export_ind")
    private String exportInd;

}

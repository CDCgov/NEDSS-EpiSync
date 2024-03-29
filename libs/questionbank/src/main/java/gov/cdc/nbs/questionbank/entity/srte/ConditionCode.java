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
@Table(name = "Condition_code")
public class ConditionCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "condition_cd", nullable = false)
    private String conditionCd;

    @Column(name = "condition_codeset_nm")
    private String conditionCodesetNm;

    @Column(name = "condition_seq_num")
    private Integer conditionSeqNum;

    @Column(name = "assigning_authority_cd")
    private String assigningAuthorityCd;

    @Column(name = "assigning_authority_desc_txt")
    private String assigningAuthorityDescTxt;

    @Column(name = "code_system_cd")
    private String codeSystemCd;

    @Column(name = "code_system_desc_txt")
    private String codeSystemDescTxt;

    @Column(name = "condition_desc_txt")
    private String conditionDescTxt;

    @Column(name = "condition_short_nm")
    private String conditionShortNm;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "indent_level_nbr")
    private Integer indentLevelNbr;

    @Column(name = "investigation_form_cd")
    private String investigationFormCd;

    @Column(name = "is_modifiable_ind")
    private String modifiableInd;

    @Column(name = "nbs_uid")
    private Long nbsUid;

    @Column(name = "nnd_ind", nullable = false)
    private String nndInd;

    @Column(name = "parent_is_cd")
    private String parentIsCd;

    @Column(name = "prog_area_cd")
    private String progAreaCd;

    @Column(name = "reportable_morbidity_ind", nullable = false)
    private String reportableMorbidityInd;

    @Column(name = "reportable_summary_ind", nullable = false)
    private String reportableSummaryInd;

    @Column(name = "status_cd")
    private String statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "nnd_entity_identifier")
    private String nndEntityIdentifier;

    @Column(name = "nnd_summary_entity_identifier")
    private String nndSummaryEntityIdentifier;

    @Column(name = "summary_investigation_form_cd")
    private String summaryInvestigationFormCd;

    @Column(name = "contact_tracing_enable_ind")
    private String contactTracingEnableInd;

    @Column(name = "vaccine_enable_ind")
    private String vaccineEnableInd;

    @Column(name = "treatment_enable_ind")
    private String treatmentEnableInd;

    @Column(name = "lab_report_enable_ind")
    private String labReportEnableInd;

    @Column(name = "morb_report_enable_ind")
    private String morbReportEnableInd;

    @Column(name = "port_req_ind_cd")
    private String portReqIndCd;

    @Column(name = "family_cd")
    private String familyCd;

    @Column(name = "coinfection_grp_cd")
    private String coinfectionGrpCd;

    @Column(name = "rhap_parse_nbs_ind")
    private String rhapParseNbsInd;

    @Column(name = "rhap_action_value")
    private String rhapActionValue;

}

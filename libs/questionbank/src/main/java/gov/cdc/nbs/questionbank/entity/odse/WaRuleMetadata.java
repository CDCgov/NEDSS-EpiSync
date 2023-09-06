package gov.cdc.nbs.questionbank.entity.odse;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "WA_rule_metadata")
public class WaRuleMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wa_rule_metadata_uid", nullable = false)
    private Long waRuleMetadataUid;

    @Column(name = "wa_template_uid", nullable = false)
    private Long waTemplateUid;

    @Column(name = "rule_cd", nullable = false)
    private String ruleCd;

    @Column(name = "rule_expression")
    private String ruleExpression;

    @Column(name = "err_msg_txt", nullable = false)
    private String errMsgTxt;

    @Column(name = "source_question_identifier")
    private String sourceQuestionIdentifier;

    @Column(name = "target_question_identifier")
    private String targetQuestionIdentifier;

    @Column(name = "record_status_cd", nullable = false)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "rule_desc_txt")
    private String ruleDescTxt;

    @Column(name = "javascript_function", nullable = false)
    private String javascriptFunction;

    @Column(name = "javascript_function_nm", nullable = false)
    private String javascriptFunctionNm;

    @Column(name = "user_rule_id")
    private String userRuleId;

    @Column(name = "logic")
    private String logic;

    @Column(name = "source_values")
    private String sourceValues;

    @Column(name = "target_type")
    private String targetType;

}

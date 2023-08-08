package gov.cdc.episync.nbs.odse.entity;

import javax.persistence.*;

@Entity
@Table(name = "WA_rule_metadata")
public class WaRuleMetadata {
    @Id
    @Column(name = "wa_rule_metadata_uid")
    private Long waRuleMetadataUid;

    @Column(name = "wa_template_uid")
    private Long waTemplateUid;

    @Column(name = "rule_cd")
    private String ruleCd;

    @Column(name = "rule_expression")
    private String ruleExpression;

    @Column(name = "err_msg_txt")
    private String errMsgTxt;

    @Column(name = "source_question_identifier")
    private String sourceQuestionIdentifier;

    @Column(name = "target_question_identifier")
    private String targetQuestionIdentifier;

    @Column(name = "record_status_cd")
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private LocalDateTime recordStatusTime;

    @Column(name = "add_time")
    private LocalDateTime addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "last_chg_time")
    private LocalDateTime lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "rule_desc_txt")
    private String ruleDescTxt;

    @Column(name = "javascript_function")
    private char javascriptFunction;

    @Column(name = "javascript_function_nm")
    private String javascriptFunctionNm;

    @Column(name = "user_rule_id")
    private String userRuleId;

    @Column(name = "logic")
    private String logic;

    @Column(name = "source_values")
    private String sourceValues;

    @Column(name = "target_type")
    private String targetType;

    public Long getWaRuleMetadataUid() {
        return this.waRuleMetadataUid;
    }

    public void setWaRuleMetadataUid(Long waRuleMetadataUid) {
        this.waRuleMetadataUid = waRuleMetadataUid;
    }

    public Long getWaTemplateUid() {
        return this.waTemplateUid;
    }

    public void setWaTemplateUid(Long waTemplateUid) {
        this.waTemplateUid = waTemplateUid;
    }

    public String getRuleCd() {
        return this.ruleCd;
    }

    public void setRuleCd(String ruleCd) {
        this.ruleCd = ruleCd;
    }

    public String getRuleExpression() {
        return this.ruleExpression;
    }

    public void setRuleExpression(String ruleExpression) {
        this.ruleExpression = ruleExpression;
    }

    public String getErrMsgTxt() {
        return this.errMsgTxt;
    }

    public void setErrMsgTxt(String errMsgTxt) {
        this.errMsgTxt = errMsgTxt;
    }

    public String getSourceQuestionIdentifier() {
        return this.sourceQuestionIdentifier;
    }

    public void setSourceQuestionIdentifier(String sourceQuestionIdentifier) {
        this.sourceQuestionIdentifier = sourceQuestionIdentifier;
    }

    public String getTargetQuestionIdentifier() {
        return this.targetQuestionIdentifier;
    }

    public void setTargetQuestionIdentifier(String targetQuestionIdentifier) {
        this.targetQuestionIdentifier = targetQuestionIdentifier;
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

    public String getRuleDescTxt() {
        return this.ruleDescTxt;
    }

    public void setRuleDescTxt(String ruleDescTxt) {
        this.ruleDescTxt = ruleDescTxt;
    }

    public char getJavascriptFunction() {
        return this.javascriptFunction;
    }

    public void setJavascriptFunction(char javascriptFunction) {
        this.javascriptFunction = javascriptFunction;
    }

    public String getJavascriptFunctionNm() {
        return this.javascriptFunctionNm;
    }

    public void setJavascriptFunctionNm(String javascriptFunctionNm) {
        this.javascriptFunctionNm = javascriptFunctionNm;
    }

    public String getUserRuleId() {
        return this.userRuleId;
    }

    public void setUserRuleId(String userRuleId) {
        this.userRuleId = userRuleId;
    }

    public String getLogic() {
        return this.logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public String getSourceValues() {
        return this.sourceValues;
    }

    public void setSourceValues(String sourceValues) {
        this.sourceValues = sourceValues;
    }

    public String getTargetType() {
        return this.targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
}

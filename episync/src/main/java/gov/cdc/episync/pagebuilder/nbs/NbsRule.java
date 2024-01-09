package gov.cdc.episync.pagebuilder.nbs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NbsRule {
    private Long id;
    String code;
    String expression;
    String errorMsg;
    String sourceIdentifier;
    String targetIdentifier;
    String description;
    String userRuleId;
    String logic;
    String sourceValues;
    String targetType;
    String functionName;
    String function;
}

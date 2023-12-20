package gov.cdc.episync.pagebuilder.mmg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MmgConcept {
    private String valueSetConceptId;
    private String codeSystemConceptName;
    private String valueSetConceptStatusDate;
    private String valueSetConceptDefinitionText;
    private String cdcPreferredDesignation;
    private String codeSystemOid;
    private String conceptCode;
    @JsonProperty("hL70396Identifier")
    private String hL70396Identifier;
}

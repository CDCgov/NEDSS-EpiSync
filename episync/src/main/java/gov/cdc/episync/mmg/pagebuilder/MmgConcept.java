package gov.cdc.episync.mmg.pagebuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MmgConcept {
    private String valueSetConceptId;
    private String codeSystemConceptName;
    private String valueSetConceptDefinitionText;
    private String codeSystemOid;
    private String conceptCode;
}

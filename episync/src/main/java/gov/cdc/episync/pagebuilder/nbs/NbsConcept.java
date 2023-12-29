package gov.cdc.episync.pagebuilder.nbs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NbsConcept {
    String code;
    String description;
    String shortDesc;
    String codeSystem;
    String codeSystemDesc;
    Boolean modifiable;
    String conceptType;
    String conceptCode;
    String conceptName;
    String preferredName;
    String version;
    String effectiveFrom;
    String statusDate;
}

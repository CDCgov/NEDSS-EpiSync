package gov.cdc.episync.pagebuilder.nbs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NbsCodeset {
    String name;
    String assigningAuthority;
    String assigningAuthorityDesc;
    String description;
    String valueSetOid;
    String valueSetName;
    String valueSetCode;
    String valueSeType;
    Boolean modifiable;
    String version;
    String effectiveFrom;
    String statusDate;
    Integer conceptsCount;
    List<NbsConcept> concepts;
}

package gov.cdc.episync.mmg.pagebuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MmgValueSet {

    private MmgValueSetInfo valueSet;
    private int conceptsCount;

    private List<MmgConcept> concepts;

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MmgValueSetInfo {
        private String valueSetId;
        private String valueSetOid;
        private String valueSetName;
        private String valueSetCode;
        private String definitionText;
    }
}
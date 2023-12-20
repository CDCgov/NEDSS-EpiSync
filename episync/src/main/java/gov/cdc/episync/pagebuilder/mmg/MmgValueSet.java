package gov.cdc.episync.pagebuilder.mmg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MmgValueSet {

    private MmgValueSetInfo valueSet;
    private MmgValueSetVersion valueSetVersion;
    private int conceptsCount;

    private List<MmgConcept> concepts;

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MmgValueSetInfo {
        private String valueSetId;
        private String valueSetOid;
        private String valueSetName;
        private String valueSetCode;
        private String statusDate;
        private String definitionText;
        private String assigningAuthorityId;
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MmgValueSetVersion {
        private String valueSetVersionId;
        private int valueSetVersionNumber;
    }
}
package gov.cdc.episync.mmg.pagebuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MmgElement {
    private String id;
    private int ordinal;
    private String name;
    private String shortName;
    private String description;
    private String dataType;
    private String priority;
    private boolean isRepeat;
    private String valueSetCode;

    private Mappings mappings;
    private DefaultValue defaultValue;

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Mappings {
        private Hl7 hl7v251;
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hl7 {
        private String legacyIdentifier;
        private String identifier;
        private String dataType;
        private String segmentType;
        private Integer fieldPosition;
        private Integer componentPosition;
        private String usage;
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DefaultValue {
        private String value;
    }

    }

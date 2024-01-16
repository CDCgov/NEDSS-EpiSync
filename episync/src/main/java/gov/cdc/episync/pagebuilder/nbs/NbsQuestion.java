package gov.cdc.episync.pagebuilder.nbs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NbsQuestion {
    Long id;
    Long parent;
    Integer order;
    String label;
    String name;
    String description;
    String tooltip;
    String type;
    String dataType;
    String identifier;
    String relatedElement;
    String parentElement;
    String dataLocation;
    String size;
    Long min;
    Long max;
    Integer version;
    String valueSetCode;
    String oid;
    String oidSystem;
    Boolean standard;
    Boolean nndMsg;
    String entryMethod;
    String questionType;
    String rdbTable;
    String rdbColumn;
    Boolean otherValue;
    Integer groupSeq;
    String groupName;
    String subGroupName;
    String blockName;
    String batchHeader;
    Integer batchColWidth;

    Mappings mappings;

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Mappings {
        private NbsQuestion.Nnd nnd;
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Nnd {
        String identifier;
        String legacyIdentifier;
        Boolean standard;
        String hl7Segment;
        String required;
        String dataType;
        String orderGroup;
    }
}

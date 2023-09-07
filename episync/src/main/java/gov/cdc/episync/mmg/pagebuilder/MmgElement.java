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
    private String description;
    private String dataType;
    private String priority;
    private boolean isRepeat;
}

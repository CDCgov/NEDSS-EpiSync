package gov.cdc.episync.mmg.pagebuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MmgBlock {
    @JsonProperty
    private String id;
    @JsonProperty
    private String type;
    @JsonProperty
    private String name;

    @JsonProperty
    private List<MmgElement> elements;
}

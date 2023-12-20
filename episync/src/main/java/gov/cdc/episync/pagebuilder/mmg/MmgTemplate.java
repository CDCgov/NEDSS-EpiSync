package gov.cdc.episync.pagebuilder.mmg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MmgTemplate {
    private String id;
    private String type;
    private String name;
    private String shortName;
    private String description;
    private String profileIdentifier;

    private List<MmgBlock> blocks;
    private List<MmgValueSet> valueSets;
}


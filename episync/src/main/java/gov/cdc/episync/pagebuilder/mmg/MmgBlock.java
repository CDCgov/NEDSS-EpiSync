package gov.cdc.episync.pagebuilder.mmg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MmgBlock {
    private String id;
    private String guideId;
    private String type;
    private String name;

    private List<MmgElement> elements;
}

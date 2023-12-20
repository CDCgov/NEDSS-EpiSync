package gov.cdc.episync.pagebuilder.nbs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NbsPage {
    private Long id;
    private String type;
    private String name;
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<NbsQuestion> elements;
    List<NbsBlock> blocks;
}

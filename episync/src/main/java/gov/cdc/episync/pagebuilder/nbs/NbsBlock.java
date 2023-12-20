package gov.cdc.episync.pagebuilder.nbs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NbsBlock {
    Long id;
    Long parent;
    Integer order;
    String name;
    String type;
    Boolean required;
    Integer groupSeq;
    String blockName;
    Integer version;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<NbsQuestion> elements;
}

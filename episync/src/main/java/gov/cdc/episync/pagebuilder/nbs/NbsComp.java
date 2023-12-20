package gov.cdc.episync.pagebuilder.nbs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor @Getter
public enum NbsComp {
    PAGE(1002L, "Page", 0),
    TAB(1010L, "Tab", 1),
    SECTION(1015L, "Section", 2),
    SUBSECTION(1016L, "Subsection", 3),
    CHECKBOX(1001L, "Checkbox", 4),
    HYPERLINK(1003L, "Hyperlink", 4),
    RADIO(1006L, "Radio", 4),
    SINGLESELECT(1007L, "Select", 4),
    INPUT(1008L, "Input", 4),
    TEXTAREA(1009L, "Textarea", 4),
    MULTISELECT(1013L, "Multiselect", 4),
    READONLY(1014L, "Readonly", 4),
    PARTICIPATION(1017L, "Participation", 4),
    SINGLESELECT_READONLY_SAVE(1024L, "SelectReadOnlySave", 4),
    MULTISELECT_READONLY_SAVE(1025L, "MultiselectReadOnlySave", 4),
    INPUT_READONLY_SAVE(1026L, "InputReadOnlySave", 4),
    SINGLESELECT_READONLY_NOSAVE(1027L, "SelectReadOnlyNoSave", 4),
    MULTISELECT_READONLY_NOSAVE(1028L,"MultiselectReadOnlyNoSave", 4),
    INPUT_READONLY_NOSAVE(1029L, "InputReadOnlyNoSave", 4);

    private final Long compId;
    private final String type;
    private final int level;

    private static final Map<Long, NbsComp> BY_COMP =
            Stream.of(values()).collect(Collectors.toMap(NbsComp::getCompId, Function.identity()));

    public static NbsComp getComp(Long id) {
        return BY_COMP.get(id);
    }
}

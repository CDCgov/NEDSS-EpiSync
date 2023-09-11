package gov.cdc.episync.framework;

import lombok.Data;
import lombok.Getter;

@Data
public class EpisyncDocumentType {
    @Getter
    public enum Type {
        MMG("Generic MMG"),
        HL7("HL7 message"),
        NND("Intermediate NND message"),
        NONE("Non valid");

        private final String name;
        Type(String name) {
            this.name = name;
        }
    }

    public EpisyncDocumentType(String type) {
        try {
            this.type = Type.valueOf(type);
        } catch (IllegalArgumentException ignore) {
            this.type = Type.NONE;
        }
    }

    public EpisyncDocumentType(Type type) {
        this.type = type;
    }

    public static EpisyncDocumentType of(Type type) {
        return new EpisyncDocumentType(type);
    }

    private Type type;
}

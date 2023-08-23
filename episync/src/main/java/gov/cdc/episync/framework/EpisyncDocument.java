package gov.cdc.episync.framework;

import java.net.URL;

public abstract class EpisyncDocument {

    private EpisyncCodeSystem codeSystem;
    private URL origin;
    private String name = "";
    private String description = "";
    private EpisyncDocumentType documentType;


    String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EpisyncDocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(EpisyncDocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getOrigin() {
        return origin;
    }

    public void setOrigin(URL origin) {
        this.origin = origin;
    }

    public EpisyncCodeSystem getCodeSystem() {
        return codeSystem;
    }

    public void setCodeSystem(EpisyncCodeSystem codeSystem) {
        this.codeSystem = codeSystem;
    }
}

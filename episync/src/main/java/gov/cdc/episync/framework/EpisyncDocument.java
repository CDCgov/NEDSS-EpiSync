package gov.cdc.episync.framework;

import lombok.Data;
import java.net.URL;

@Data
public abstract class EpisyncDocument {
    private EpisyncCodeSystem codeSystem;
    private URL origin;
    private Long uid;
    private String name = "";
    private String description = "";
    private EpisyncDocumentType documentType;
}

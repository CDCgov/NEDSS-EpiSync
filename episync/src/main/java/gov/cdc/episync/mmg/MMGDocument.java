package gov.cdc.episync.mmg;

import gov.cdc.episync.framework.EpisyncDocument;
import gov.cdc.episync.framework.EpisyncDocumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.InputStream;

@Data @EqualsAndHashCode(callSuper = true)
public class MMGDocument extends EpisyncDocument {
    InputStream json;

    public MMGDocument(InputStream stream) {
        this.json = stream;
        setDocumentType(new EpisyncDocumentType(EpisyncDocumentType.Type.MMG));
    }
}

package gov.cdc.episync.mmg;

import gov.cdc.episync.framework.EpisyncDocument;
import gov.cdc.episync.framework.EpisyncDocumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.InputStream;

@Data @EqualsAndHashCode(callSuper = true)
public class MmgDocument extends EpisyncDocument {
    InputStream json;

    public MmgDocument(InputStream stream) {
        this.json = stream;
        setDocumentType(EpisyncDocumentType.of(EpisyncDocumentType.Type.MMG));
    }
}

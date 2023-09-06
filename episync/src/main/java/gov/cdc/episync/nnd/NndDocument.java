package gov.cdc.episync.nnd;

import gov.cdc.episync.framework.EpisyncDocument;
import gov.cdc.episync.framework.EpisyncDocumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.InputStream;

@Data @EqualsAndHashCode(callSuper = true)
public class NndDocument extends EpisyncDocument {
    InputStream xml;

    public NndDocument(InputStream stream) {
        this.xml = stream;
        setDocumentType(new EpisyncDocumentType(EpisyncDocumentType.Type.NND));
    }
}

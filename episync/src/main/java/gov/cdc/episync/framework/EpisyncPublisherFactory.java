package gov.cdc.episync.framework;

import gov.cdc.episync.pagebuilder.mmg.MmgPageBuilderPublisher;
import gov.cdc.episync.nnd.NndPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class EpisyncPublisherFactory {
    private final MmgPageBuilderPublisher pageBuilderPublisher;
    private final NndPublisher nndPublisher;

    public EpisyncPublisher<? extends EpisyncDocument> getPublisherForType(EpisyncDocument document) {
        EpisyncDocumentType.Type type = document.getDocumentType().getType();

        switch (type) {
            case MMG:
                return pageBuilderPublisher;
            case NND:
                return nndPublisher;
            default:
                throw new IllegalArgumentException("No publisher available for this document type");
        }
    }
}

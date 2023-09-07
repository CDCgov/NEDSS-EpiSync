package gov.cdc.episync.nnd;

import gov.cdc.episync.framework.EpisyncPublishException;
import gov.cdc.episync.framework.EpisyncPublishResult;
import gov.cdc.episync.framework.EpisyncPublisher;
import org.springframework.stereotype.Service;

@Service("nnd")
public class NndPublisher implements EpisyncPublisher<NndDocument> {
    @Override
    public EpisyncPublishResult publishDocument(NndDocument document) throws EpisyncPublishException {
        return null;
    }
}

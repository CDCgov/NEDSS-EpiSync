package gov.cdc.episync.mmg;

import gov.cdc.episync.framework.EpisyncDocument;
import gov.cdc.episync.framework.EpisyncPublishException;
import gov.cdc.episync.framework.EpisyncPublishResult;
import gov.cdc.episync.framework.EpisyncPublisher;

public abstract class MMGPublisher implements EpisyncPublisher {
    @Override
    public abstract EpisyncPublishResult publishDocument(EpisyncDocument document) throws EpisyncPublishException;
}

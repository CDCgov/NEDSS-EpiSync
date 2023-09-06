package gov.cdc.episync.mmg;

import gov.cdc.episync.framework.EpisyncPublishException;
import gov.cdc.episync.framework.EpisyncPublishResult;
import gov.cdc.episync.framework.EpisyncPublisher;

public abstract class MMGPublisher implements EpisyncPublisher<MMGDocument> {
    public abstract EpisyncPublishResult publishDocument(MMGDocument document) throws EpisyncPublishException;
}
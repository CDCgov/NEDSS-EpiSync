package gov.cdc.episync.mmg;

import gov.cdc.episync.framework.EpisyncPublishException;
import gov.cdc.episync.framework.EpisyncPublishResult;
import gov.cdc.episync.framework.EpisyncPublisher;

public abstract class MmgPublisher implements EpisyncPublisher<MmgDocument> {
    public abstract EpisyncPublishResult publishDocument(MmgDocument document) throws EpisyncPublishException;
}
package gov.cdc.episync.framework;

public interface EpisyncPublisher {

    EpisyncPublishResult publishDocument(EpisyncDocument document) throws EpisyncPublishException;
}

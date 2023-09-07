package gov.cdc.episync.framework;

public interface EpisyncPublisher<T extends EpisyncDocument> {

    EpisyncPublishResult publishDocument(T document) throws EpisyncPublishException;
}

package gov.cdc.episync.framework;

public interface EpisyncExtractor<T> {
    EpisyncExtractResult extractData(T uid) throws Exception;
}

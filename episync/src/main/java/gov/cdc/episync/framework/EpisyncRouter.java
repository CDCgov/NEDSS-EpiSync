package gov.cdc.episync.framework;

public interface EpisyncRouter<K, V> {
    EpisyncRouteResult routeData(EpisyncData<K, V> data) throws EpisyncRouterException;
}

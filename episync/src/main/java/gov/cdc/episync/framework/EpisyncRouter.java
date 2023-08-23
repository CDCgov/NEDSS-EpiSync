package gov.cdc.episync.framework;

public interface EpisyncRouter {

    EpisyncRouteResult routeData(EpisyncData data) throws EpisyncRouterException;
}

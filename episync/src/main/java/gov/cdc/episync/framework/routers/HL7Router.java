package gov.cdc.episync.framework.routers;

import gov.cdc.episync.framework.EpisyncData;
import gov.cdc.episync.framework.EpisyncRouteResult;
import gov.cdc.episync.framework.EpisyncRouter;
import gov.cdc.episync.framework.EpisyncRouterException;

public class HL7Router implements EpisyncRouter<String, String> {
    @Override
    public EpisyncRouteResult routeData(EpisyncData<String, String> data) throws EpisyncRouterException {
        return null;
    }
}

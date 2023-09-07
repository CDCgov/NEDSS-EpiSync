package gov.cdc.episync.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
public class EpisyncRouteResult {
    @Getter
    @AllArgsConstructor
    public enum RouteResultCode {
        SUCCESS("Routing succeeded"),
        FAILED("Routing failed");

        private final String description;
    }

    private final RouteResultCode resultCode;
    private final String resultMessage;
}

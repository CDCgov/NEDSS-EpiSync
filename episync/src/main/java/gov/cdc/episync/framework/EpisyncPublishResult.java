package gov.cdc.episync.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class EpisyncPublishResult {
    @Getter
    @AllArgsConstructor
    public enum PublishResultCode {
        SUCCESS("Publishing succeeded"),
        FAILED("Publishing failed"),
        INVALID_DATA("Invalid data provided"),
        PERMISSION_DENIED("Permission denied"),
        NETWORK_ERROR("Network error"),
        TIMEOUT("Publishing timed out"),
        UNKNOWN_ERROR("An unknown error occurred");

        private final String description;
    }

    public EpisyncPublishResult(PublishResultCode resultCode) {
        this.resultCode = resultCode;
        resultMessage = "";
    }

    private final PublishResultCode resultCode;
    private final String resultMessage;
}


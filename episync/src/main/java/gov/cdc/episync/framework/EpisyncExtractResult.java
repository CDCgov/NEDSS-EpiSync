package gov.cdc.episync.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter @Setter @AllArgsConstructor
public class EpisyncExtractResult {
    @Getter @AllArgsConstructor
    public enum ExtractResultCode {
        SUCCESS("Extraction succeeded"),
        FAILED("Extraction failed"),
        NETWORK_ERROR("Network error"),
        TIMEOUT("Publishing timed out"),
        UNKNOWN_ERROR("An unknown error occurred");

        private final String description;
    }
    private final EpisyncExtractResult.ExtractResultCode resultCode;
    private final String resultMessage;
    private final URI location;
}
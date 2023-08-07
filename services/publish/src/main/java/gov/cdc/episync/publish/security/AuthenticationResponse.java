package gov.cdc.episync.publish.security;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String message;
}

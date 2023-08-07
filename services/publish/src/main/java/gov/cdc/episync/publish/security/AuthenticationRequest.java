package gov.cdc.episync.publish.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example="episync")
    private String username;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example="secret")
    private String password;

    // Parameterized constructor
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
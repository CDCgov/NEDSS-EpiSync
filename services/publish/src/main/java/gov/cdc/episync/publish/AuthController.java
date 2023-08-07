package gov.cdc.episync.publish;

import gov.cdc.episync.publish.security.AuthenticationRequest;
import gov.cdc.episync.publish.security.AuthenticationResponse;
import gov.cdc.episync.publish.security.JwtTokenUtil;
import gov.cdc.episync.publish.shared.SimpleResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Authorization")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))}),
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleResponse.class))}),
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleResponse.class))})
})
@RestController
@RequestMapping("/oauth")
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/token")
    public ResponseEntity<?> authenticate(
            @RequestBody(description = "Obtain API token", required = true,
                    content = @Content(schema=@Schema(implementation = AuthenticationRequest.class)))
            @org.springframework.web.bind.annotation.RequestBody AuthenticationRequest request) {

        // Validate user credentials and generate JWT token - must be actual in future!!! - TODO
        if (!StringUtils.hasLength(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new SimpleResponse(HttpStatus.UNAUTHORIZED.value(), "Bad credentials"));
        }

        try {
            String token = jwtTokenUtil.generateToken(request.getUsername());
            // Return token as response
            return ResponseEntity.ok(new AuthenticationResponse(token, "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new SimpleResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }
}

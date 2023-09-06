package gov.cdc.episync.publish.security;

import gov.cdc.episync.publish.shared.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtTokenInterceptor(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        // Extract Bearer token from request headers
        String token = jwtTokenUtil.extractBearerTokenFromRequest(request); // Custom method to extract token from headers
        boolean isValidToken = jwtTokenUtil.validateToken(token);

        if (isValidToken) {
            return true; // Proceed to the controller
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(
                    SimpleResponse.of(HttpStatus.UNAUTHORIZED.value(), "Unauthorized")));
            writer.flush();
            return false; // Abort request processing
        }
    }
}

package com.episync.publish.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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
            response.getWriter().write("Unauthorized");
            return false; // Abort request processing
        }
    }
}

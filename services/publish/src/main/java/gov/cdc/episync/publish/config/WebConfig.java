package gov.cdc.episync.publish.config;

import gov.cdc.episync.publish.security.JwtTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenInterceptor jwtTokenInterceptor;

    public WebConfig(JwtTokenInterceptor jwtTokenInterceptor) {
        this.jwtTokenInterceptor = jwtTokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register JwtTokenInterceptor to intercept all requests
        registry.addInterceptor(jwtTokenInterceptor).addPathPatterns("/feed/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new TypeEnumConverter());
    }
}


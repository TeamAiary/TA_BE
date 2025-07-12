package com.aiary.be.global.config;

import com.aiary.be.global.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
            .addPathPatterns(
                "/api/**",
                "/admin/**"
            )
            .excludePathPatterns(
                "/api/auth/register",
                "/api/auth/login",
                "/api/auth/logout",
                "/admin",
                "/error",
                "/favicon.ico"
            );
    }
}

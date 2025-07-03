package com.diary.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    @Value("${app.name}")
    private String appName;
    
    @Value("${app.version}")
    private String appVersion;
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    
    @Value("${cors.allowed-origins}")
    private String corsAllowedOrigins;
    
    @Value("${cors.allowed-methods}")
    private String corsAllowedMethods;
    
    @Value("${cors.allowed-headers}")
    private String corsAllowedHeaders;
    
    @Value("${cors.allow-credentials}")
    private Boolean corsAllowCredentials;
    
    public String getAppName() {
        return appName;
    }
    
    public String getAppVersion() {
        return appVersion;
    }
    
    public String getJwtSecret() {
        return jwtSecret;
    }
    
    public Long getJwtExpiration() {
        return jwtExpiration;
    }
    
    public String getCorsAllowedOrigins() {
        return corsAllowedOrigins;
    }
    
    public String getCorsAllowedMethods() {
        return corsAllowedMethods;
    }
    
    public String getCorsAllowedHeaders() {
        return corsAllowedHeaders;
    }
    
    public Boolean getCorsAllowCredentials() {
        return corsAllowCredentials;
    }
}
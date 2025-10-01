package com.ecommerceapi.ecommerce_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")   // allow all origins
                        .allowedMethods("*")          // allow all HTTP methods
                        .allowedHeaders("*")          // allow all headers
                        .allowCredentials(false);     // must be false if you use "*"
            }
        };
    }
}

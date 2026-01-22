package com.example.tax_api.config;

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
                        .allowedOrigins(
                                "https://d2g96d75cddiut.cloudfront.net",
                                "https://api.tkpshivatemple.com",
                                "http://localhost:3000",
                                "http://tax-frontend.s3-website-us-east-1.amazonaws.com",
                                "https://tax-frontend.s3-website-us-east-1.amazonaws.com",
                                "http://inventory-table-frontend.s3-website-us-east-1.amazonaws.com/mvn"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}

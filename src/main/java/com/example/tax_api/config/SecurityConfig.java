package com.example.tax_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults()) // enable CORS support
                .csrf(csrf -> csrf.disable()) // disable if you're stateless API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll() // preflight
                        .requestMatchers(HttpMethod.GET, "/api/tax/config").permitAll()
                        .requestMatchers("/actuator/health", "/actuator/health/**", "/actuator/info", "/actuator",
                                "/actuator/**")
                        .permitAll() // allow actuator endpoints for health checks
                        .anyRequest().authenticated())
                .build();
    }
}

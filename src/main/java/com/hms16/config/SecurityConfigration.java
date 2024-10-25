package com.hms16.config;

import com.hms16.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfigration {
private JWtFilter jWtFilter;

    public SecurityConfigration(JwtService jwtService, JWtFilter jWtFilter) {
        this.jWtFilter = jWtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    ) throws Exception {
        httpSecurity.csrf().disable().cors().disable();
        httpSecurity.addFilterBefore(jWtFilter, AuthorizationFilter.class);
//        httpSecurity.authorizeHttpRequests().anyRequest().permitAll();
        httpSecurity.authorizeHttpRequests().requestMatchers("/api/v1/users/signup","/api/v1/users/login")
                .permitAll()
                .anyRequest().authenticated();
        return httpSecurity.build();
    }
}

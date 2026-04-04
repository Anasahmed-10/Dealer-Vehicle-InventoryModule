package com.example.inventoryModule.common.security;

import lombok.RequiredArgsConstructor;
import com.example.inventoryModule.common.security.HeaderAuthenticationFilter;
import com.example.inventoryModule.common.tenant.TenantFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final HeaderAuthenticationFilter headerAuthenticationFilter;
    private final TenantFilter tenantFilter;

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("GLOBAL_ADMIN")
                .requestMatchers("/dealers/**", "/vehicles/**").hasAnyRole("TENANT_USER", "GLOBAL_ADMIN")
                .anyRequest().permitAll()
            )
            .addFilterBefore(headerAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(tenantFilter, HeaderAuthenticationFilter.class)
            //.httpBasic(Customizer.withDefaults())
           ;

        return http.build();
    }
}
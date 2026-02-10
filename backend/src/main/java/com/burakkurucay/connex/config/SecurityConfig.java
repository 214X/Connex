package com.burakkurucay.connex.config;

import com.burakkurucay.connex.security.JwtAuthenticationFilter;
import com.burakkurucay.connex.exception.handler.SecurityExceptionHandlers;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    JwtAuthenticationFilter jwtAuthFilter;
    SecurityExceptionHandlers securityHandlers;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, SecurityExceptionHandlers securityHandlers) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.securityHandlers = securityHandlers;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Stateless API: session yok, her request token taşır
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // CSRF disabled (JWT + stateless REST)
                .csrf(csrf -> csrf.disable())

                // CORS aktif
                .cors(Customizer.withDefaults())

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(securityHandlers)
                        .accessDeniedHandler(securityHandlers))

                // Public vs Protected endpointler
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/health/**").permitAll()

                        // 🔓 Public Job Endpoints
                        .requestMatchers(HttpMethod.GET, "/jobs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/jobs/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/jobs/company/**").permitAll()

                        // 🔓 Public avatar and header access (anyone can view profile images)
                        .requestMatchers(HttpMethod.GET, "/api/profiles/*/avatar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/profiles/*/header").permitAll()

                        // 🔒 Private profile (must be BEFORE the wildcard rule)
                        .requestMatchers("/api/profiles/me").authenticated()
                        .requestMatchers("/api/profiles/me/**").authenticated()

                        // 🔓 Public CV Access
                        .requestMatchers(HttpMethod.GET, "/api/profiles/personal/*/cv").permitAll()

                        // 🔓 Public Search Domain
                        .requestMatchers("/api/search/**").permitAll()

                        // 🔓 Public profile by userId
                        .requestMatchers(HttpMethod.GET, "/api/profiles/*").permitAll()

                        .anyRequest().authenticated())

                // JWT filter: Authorization: Bearer token kontrolü
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

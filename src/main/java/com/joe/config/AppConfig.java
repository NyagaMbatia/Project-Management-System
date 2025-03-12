package com.joe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.joe.config.JwtConstant.HEADER;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security
                // This configures  the application to be stateless, meaning it doesn't store session information between requests
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /*
                * This specifies which endpoints require authentication
                *   Any URL starting with /api/ require authentication
                *   All other URLS are accessible without authentication
                * */
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/**")
                        .authenticated()
                        .anyRequest()
                        .permitAll())
                // This adds a custom filter -> JwtTokenValidator before any other authentication filter runs
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                // disables CSRF which is common for stateless REST APIs
                .csrf(AbstractHttpConfigurer::disable)
                // This configures Cross-Origin Resource Sharing, allowing the application to accept requests from specified origins
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return security.build();

    }

    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration cfg = new CorsConfiguration();
            // Allows requests from the given domains
            cfg.setAllowedOrigins(Arrays.asList(
                    "http://localhost:3000",
                    "http://localhost:5173"
            ));

            // Allow all HTTP Methods
            cfg.setAllowedMethods(Collections.singletonList("*"));
            // Allows all Headers
            cfg.setAllowedHeaders(Collections.singletonList("*"));
            // Allows credentials to be included in the request
            cfg.setAllowCredentials(true);
            // Exposes the Authorization header to the client
            cfg.setExposedHeaders(List.of(HEADER));
            // Sets the maximum age of the CORS options to 1 hour
            cfg.setMaxAge(3600L);

            return cfg;

        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder();}
}

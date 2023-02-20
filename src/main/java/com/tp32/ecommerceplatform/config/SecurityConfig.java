package com.tp32.ecommerceplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tp32.ecommerceplatform.security.jwt.JwtAuthEntryPoint;
import com.tp32.ecommerceplatform.security.jwt.JwtAuthFilter;

/**
 * Allows the implementation of application-level security.
 * Provides a highly customiseable way to implement authentication and authorisation.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthEntryPoint authEntryPoint;

    private JwtAuthFilter authFilter;

    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint, JwtAuthFilter jwtAuthFilter) {
        this.authEntryPoint = jwtAuthEntryPoint;
        this.authFilter = jwtAuthFilter;
    }

    /**
     * Defines the concrete implementation for PasswordEncoder
     * @return bcrypt encoder object
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines the concrete implementation for the AuthenticationManager
     * @param authConfig handles the authentication export
     * @return an Authentication Manager object from the configuration parameter
     * @throws Exception if the manager is not initialised
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * 
     * @param http to allow configuring web based security for specific http requests.
     * @return the object required for Spring Boot security initialisation
     * @throws Exception if the authentication fails
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((auth) -> 
                auth.requestMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN") // The role is named "ROLE_ADMIN" but 'ROLE' is omitted here
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                ).exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authEntryPoint));
                        
        // Attempts to find the username/password request and authenticates the user
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

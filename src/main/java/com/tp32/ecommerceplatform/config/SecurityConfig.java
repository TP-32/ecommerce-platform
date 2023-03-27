package com.tp32.ecommerceplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.tp32.ecommerceplatform.security.jwt.JwtAuthFilter;

/**
 * Allows the implementation of application-level security.
 * Provides a highly customiseable way to implement authentication and authorisation.
 */
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    
    private AuthenticationProvider authProvider;

    private JwtAuthFilter authFilter;

    public SecurityConfig(AuthenticationProvider authProvider, JwtAuthFilter jwtAuthFilter) {
        this.authProvider = authProvider;
        this.authFilter = jwtAuthFilter;
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
                .authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/orders").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/net/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/user/update").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authProvider)
                .logout().deleteCookies("JSESSIONID").deleteCookies("Authorization").invalidateHttpSession(true).logoutSuccessUrl("/")
                .and().addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

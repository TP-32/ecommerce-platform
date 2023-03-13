package com.tp32.ecommerceplatform.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.tp32.ecommerceplatform.repository.TokenRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A filter (servlet) that extracts the Jwt (Token) from the request header and determines its validity.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtToken jwtToken;

    private UserDetailsService userDetailsService;

    private TokenRepository tokenRepository;

    public JwtAuthFilter(JwtToken jwtToken, UserDetailsService userDetailsService, TokenRepository tokenRepository) {
        this.jwtToken = jwtToken;
        this.userDetailsService = userDetailsService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Returns the token from the HTTP request
        String token = "";
        Cookie cookie = WebUtils.getCookie(request, "Authorization");
        if (cookie != null)
            token = cookie.getValue();

        if (token.equals("")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String email = jwtToken.getEmailFromToken(token);
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            boolean isValid = tokenRepository.findByToken(token)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);
            if (jwtToken.validateToken(token, userDetails) && isValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}

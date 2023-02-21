package com.tp32.ecommerceplatform.security.jwt;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Handles the generation and validation of Jwt (Tokens)
 */
@Component
public class JwtToken {
    
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-ms}")
    private long jwtExpiration;

    public String generateToken(Authentication auth, UserRepository userRepository) {
        String email = auth.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpiration);
        User user = userRepository.findByEmail(email).get();

        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .claim("role", user.getRole())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String email = claims.getSubject();
        return email;
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (Exception e) {
            return false;
            // Throw Exception
        }
    }
}

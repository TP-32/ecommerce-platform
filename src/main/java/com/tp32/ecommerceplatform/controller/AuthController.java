package com.tp32.ecommerceplatform.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tp32.ecommerceplatform.dto.JwtResponse;
import com.tp32.ecommerceplatform.dto.LoginDto;
import com.tp32.ecommerceplatform.dto.RegisterDto;
import com.tp32.ecommerceplatform.service.UserService;

/**
 * Handles the creation of RESTful web services, which maps requests to actions.
 */
@RestController
public class AuthController {

    private UserService userService;

    @Value("${app.jwt-expiration-ms}")
    private long jwtExpiration;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param registerDto stores relevant details of the register process to be sent
     *                    from front-end to back-end
     * @return A ResponseEntity response which confirms that the process was
     *         successful
     */
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@ModelAttribute RegisterDto registerDto) {
        JwtResponse res = userService.register(registerDto);
        ResponseCookie cookie = ResponseCookie.from("Authorization", res.getToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(jwtExpiration/1000)
            .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(res);
    }

    /**
     * @param loginDto stores relevant details of the login process to be sent from
     *                 front-end to back-end
     * @return a ResponseEntity response which returns the token to the user, and
     *         confirms the process was successful
     */

     @PostMapping("login")
     public ResponseEntity<JwtResponse> login(@ModelAttribute LoginDto loginDto) {
        JwtResponse res = userService.login(loginDto);
        ResponseCookie cookie = ResponseCookie.from("Authorization", res.getToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(jwtExpiration/1000)
            .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(res);
     }
    @GetMapping("admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "Admin Auth";
    }
}

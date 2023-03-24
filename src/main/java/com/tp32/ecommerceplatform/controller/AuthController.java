package com.tp32.ecommerceplatform.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tp32.ecommerceplatform.dto.JwtResponse;
import com.tp32.ecommerceplatform.dto.LoginDto;
import com.tp32.ecommerceplatform.dto.RegisterDto;
import com.tp32.ecommerceplatform.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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
     * @throws IOException
     */
    @PostMapping("/register")
    public void register(@ModelAttribute RegisterDto registerDto, HttpServletResponse response) throws IOException {
        JwtResponse res = userService.register(registerDto);
        setCookie(res.getToken(), response);
    }

    /**
     * @param loginDto stores relevant details of the login process to be sent from
     *                 front-end to back-end
     * @throws IOException
     */

     @PostMapping("login")
     public void login(@ModelAttribute LoginDto loginDto, HttpServletResponse response) throws IOException {
        JwtResponse res = userService.login(loginDto);
        setCookie(res.getToken(), response);
     }

     /**
      * Helper Method to generate and set the cookie of a user
      * @param token the generated token for this instance
      * @param response provides HTTP functionality in sending a response
      * @throws IOException
      */
     private void setCookie(String token, HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (jwtExpiration/1000));
        
        response.addCookie(cookie);
        response.sendRedirect("/");
     }
}

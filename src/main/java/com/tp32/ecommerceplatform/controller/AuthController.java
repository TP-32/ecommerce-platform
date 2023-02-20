package com.tp32.ecommerceplatform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tp32.ecommerceplatform.dto.JwtResponse;
import com.tp32.ecommerceplatform.dto.LoginDto;
import com.tp32.ecommerceplatform.dto.RegisterDto;
import com.tp32.ecommerceplatform.service.UserService;

/**
 * Handles the creation of RESTful web services, which maps requests to actions.
 */
@Controller
@RequestMapping()
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param registerDto stores relevant details of the register process to be sent from front-end to back-end
     * @return A ResponseEntity response which confirms that the process was successful
     */
    @PostMapping("register")
    public String register(RegisterDto registerDto) {
        userService.register(registerDto);
        return "redirect:/";
    }

    /**
     * @param loginDto stores relevant details of the login process to be sent from front-end to back-end
     * @return a ResponseEntity response which returns the token to the user, and confirms the process was successful
     */
    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(LoginDto loginDto) {
        String token = userService.login(loginDto);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);

        //return "redirect:/";
        return ResponseEntity.ok(jwtResponse);
    }

/*  Debug request to test Role Permissions */
    // @GetMapping("admin")
    // public String admin() {
    //     System.out.println("Access + perm");
    //     return "redirect:/";
    // } 
}

package com.tp32.ecommerceplatform.controller;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tp32.ecommerceplatform.dto.LoginDto;
import com.tp32.ecommerceplatform.dto.RegisterDto;
import com.tp32.ecommerceplatform.dto.UpdateUserDto;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class IndexController {

    private UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    /*
     * Simply displays the home page to the user when there are no explicit page
     * arguments requested.
     */
    @GetMapping("")
    public String index() {
        return "index.html";
    }

    /*
     * When login is requested, a new Login Data Transfer Object is constructed (to
     * be updated with data from user input)
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userDto", new LoginDto());
        return "login.html";
    }

    /*
     * When register is requested, a new Register Data Transfer Object is
     * constructed (to be updated with data from the user input)
     */
    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("userDto", new RegisterDto());
        return "signup.html";
    }

    @GetMapping("/user/update")
    public String edit(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userDto", new UpdateUserDto(user));
        return "update-user.html";
    }

    @PostMapping("/user/update")
    public String update(@Valid @ModelAttribute("userDto") UpdateUserDto userDto, BindingResult result, Model model, HttpServletResponse response) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!result.hasErrors()) {
            Cookie cookie = new Cookie("Authorization", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);
    
            response.addCookie(cookie);
            response.sendRedirect("/");
            userService.updateUser(user.getID(), userDto);
            return "index.html";
        }
        model.addAttribute("userDto", userDto);
        return "update-user.html";
    }

    /*
     * Simply displays the about us page to the user.
     */
    @GetMapping("/aboutus")
    public String aboutus() {
        return "aboutus.html";
    }

    /*
     * Simply displays the contact us page to the user.
     */
    @GetMapping("/contact")
    public String contact() {
        return "contact.html";
    }
}
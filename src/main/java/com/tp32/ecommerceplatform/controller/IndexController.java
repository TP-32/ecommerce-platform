package com.tp32.ecommerceplatform.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tp32.ecommerceplatform.dto.LoginDto;
import com.tp32.ecommerceplatform.dto.RegisterDto;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.service.OrderService;

@Controller
public class IndexController {

    private OrderService orderService;

    public IndexController(OrderService orderService) {
        this.orderService = orderService;
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
        model.addAttribute("user", new LoginDto());
        return "login.html";
    }

    /*
     * When register is requested, a new Register Data Transfer Object is
     * constructed (to be updated with data from the user input)
     */
    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("user", new RegisterDto());
        return "signup.html";
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

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("status", orderService.getStatus());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof User))
            return null;

        model.addAttribute("orders", orderService.getOrders(((User) principal).getID()));
        return "orders.html";
    }
}
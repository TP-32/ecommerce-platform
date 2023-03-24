package com.tp32.ecommerceplatform.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tp32.ecommerceplatform.dto.LoginDto;
import com.tp32.ecommerceplatform.dto.RegisterDto;
import com.tp32.ecommerceplatform.model.Order;
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

        model.addAttribute("reverseSortDir", "asc");
        model.addAttribute("orders", orderService.getOrders(((User) principal).getID()));
        return "orders.html";
    }

    @GetMapping("/orders/{sort}")
    public String orders(Model model, @PathVariable("sort") String sort, @RequestParam("sortDir") String sortDir) {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (sort.equals("name"))
            sort = "user.firstName";
        List<Order> orders = orderService.getOrdersWithSort(user.getID(), sort, sortDir);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("orders", orders);
        return "orders.html";
    }
}
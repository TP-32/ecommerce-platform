package com.tp32.ecommerceplatform.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.service.OrderService;

@Controller
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String orders(Model model, @RequestParam(value = "orderId", required = false) Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id != null) {
            if (!orderService.getOrders(user.getID()).contains(orderService.getOrder(id))) return null;
            model.addAttribute("productview", "true");
            model.addAttribute("orderitems", orderService.getOrder(id).getOrderItems());
        } else {
            model.addAttribute("status", orderService.getStatus());
            model.addAttribute("reverseSortDir", "asc");
            model.addAttribute("orders", orderService.getOrders(user.getID()));
        }
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
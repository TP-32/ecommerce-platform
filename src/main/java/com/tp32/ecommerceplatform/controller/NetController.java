package com.tp32.ecommerceplatform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.repository.UserRepository;
import com.tp32.ecommerceplatform.service.NetService;
import com.tp32.ecommerceplatform.service.ProductService;

@Controller
public class NetController {

    private NetService netService;
    private ProductService productService;
    private UserRepository userRepository;
    
    public NetController(NetService netService, ProductService productService, UserRepository userRepository) {
        this.netService = netService;
        this.productService = productService;
        this.userRepository = userRepository;
    }

    /*
     * Simply displays the categories page to the user.
     */
    @PostMapping("/cart/add/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Product> addToCart(@PathVariable("productId") Long productId, Model model, Authentication auth) throws Exception {
        Product product = productService.getProduct(productId);
        User user = userRepository.findByEmail(auth.getName()).get();
        netService.addProduct(user, product);

        return ResponseEntity.ok(product);
        //return "redirect:/categories";
    }
}

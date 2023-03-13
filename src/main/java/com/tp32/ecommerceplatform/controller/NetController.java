package com.tp32.ecommerceplatform.controller;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Product> addToCart(@PathVariable("productId") Long productId, Model model) throws Exception {
        Product product = productService.getProduct(productId);
        //System.out.println("Product: " + product.getName());
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //System.out.println("Auth: " + auth);
        //if (userRepository.findByEmail(auth.getName()).get() == null) {
        //    System.out.println("This is null.");
        //} 
        //User user = userRepository.findByEmail(auth.getName()).get();
        User user = userRepository.findById(1L).get();
        System.out.println("User " + user);

        System.out.println("Email " + user.getEmail());
        netService.addProduct(user, product);

        return ResponseEntity.ok(product);
        //return "redirect:/categories";
    }
}

package com.tp32.ecommerceplatform.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tp32.ecommerceplatform.dto.NetDto;
import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.service.ProductService;

@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /*
     * Simply displays the categories page to the user, with a limit of 10 shown at
     * once.
     */
    @GetMapping("categories")
    public String categories(Model model) {
        List<Product> products = productService.getProducts().stream().filter(p -> p.getInventory().getStock() > 0)
                .collect(Collectors.toList()).subList(0,
                        Math.min(productService.getProducts().size(), 10));
        model.addAttribute("products", products);
        return "categories.html";
    }

    @GetMapping("/tropical")
    public String categoryTropical(Model model) {
        List<Product> products = productService.getProducts().stream()
                .filter(p -> p.getCategory().getName().equals("Tropical") && p.getInventory().getStock() > 0)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "tropical.html";
    }

    @GetMapping("/brackish")
    public String categoryBrackish(Model model) {
        List<Product> products = productService.getProducts().stream()
                .filter(p -> p.getCategory().getName().equals("Brackish") && p.getInventory().getStock() > 0)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "brackish.html";
    }

    @GetMapping("/coldwater")
    public String categoryColdwater(Model model) {
        List<Product> products = productService.getProducts().stream()
                .filter(p -> p.getCategory().getName().equals("Coldwater") && p.getInventory().getStock() > 0)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "coldwater.html";
    }

    @GetMapping("/hybrid")
    public String categoryHybrid(Model model) {
        List<Product> products = productService.getProducts().stream()
                .filter(p -> p.getCategory().getName().equals("Hybrid") && p.getInventory().getStock() > 0)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "hybrid.html";
    }

    @GetMapping("/marine")
    public String categoryMarine(Model model) {
        List<Product> products = productService.getProducts().stream()
                .filter(p -> p.getCategory().getName().equals("Marine") && p.getInventory().getStock() > 0)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "marine.html";
    }

    @GetMapping("/predatory")
    public String categoryPredatory(Model model) {
        List<Product> products = productService.getProducts().stream()
                .filter(p -> p.getCategory().getName().equals("Predatory") && p.getInventory().getStock() > 0)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "predatory.html";
    }

    @GetMapping("/products/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "basket.html";
    }

    @GetMapping("/fish/details/{productId}")
    public String getDetails(@PathVariable("productId") Long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        model.addAttribute("netDto", new NetDto());
        return "fishdetail.html";
    }

}

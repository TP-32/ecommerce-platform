package com.tp32.ecommerceplatform.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.service.ProductService;

@Controller
public class ProductController {

    private ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /*
     * Simply displays the categories page to the user.
     */
    @GetMapping("categories")
    public String categories(Model model) {
        List<Product> products = productService.getProducts().stream().limit(10).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "categories.html";
    }

    @GetMapping("/tropical")
    public String categoryTropical(Model model) {
        List<Product> products = productService.getProducts().stream().filter(p -> p.getCategory().getName().equals("Tropical")).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "tropical.html";
    }

    @GetMapping("/brackish")
    public String categoryBrackish(Model model) {
        List<Product> products = productService.getProducts().stream().filter(p -> p.getCategory().getName().equals("Brackish")).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "brackish.html";
    }

    @GetMapping("/coldwater")
    public String categoryColdwater(Model model) {
        List<Product> products = productService.getProducts().stream().filter(p -> p.getCategory().getName().equals("Coldwater")).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "coldwater.html";
    }

    @GetMapping("/hybrid")
    public String categoryHybrid(Model model) {
        List<Product> products = productService.getProducts().stream().filter(p -> p.getCategory().getName().equals("Hybrid")).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "hybrid.html";
    }

    @GetMapping("/marine")
    public String categoryMarine(Model model) {
        List<Product> products = productService.getProducts().stream().filter(p -> p.getCategory().getName().equals("Marine")).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "marine.html";
    }

    @GetMapping("/predatory")
    public String categoryPredatory(Model model) {
        List<Product> products = productService.getProducts().stream().filter(p -> p.getCategory().getName().equals("Predatory")).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "predatory.html";
    }

    @GetMapping("/products/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "basket.html";
    }
    
    /* A parameter needs to be mapped here.
    * With the 'Shop Now' button, once clicked, a request should be made to /fish/details with a parameter, this will likely be its ID
    * Then the GetMapping method below will need to grab that id, find the product, and add it to the model as an attribute, before
    * redirecting the user to the page, which should then have access to that model.
    * GetMapping("/fish/details")
    * public String getDetails(@RequestParam(required = true) String id) {
    *    return "ID: " + id;
    *}
    * This will then be mapped to /fish/details?id={id}
    */
    @GetMapping("/fish/details/{productId}")
    public String getDetails(@PathVariable("productId") Long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "fishdetail.html";
    }

}

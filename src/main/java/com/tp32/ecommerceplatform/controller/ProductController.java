package com.tp32.ecommerceplatform.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String categories(Model model, @RequestParam(value = "search", required = false) String search) {
        List<Product> products;
        if (search != null && !search.isEmpty())
            products = this.filterBySearch(productService.getProducts(), search);
        else
            products = productService.getProducts();
        products.stream().filter(p -> p.getInventory().getStock() > 0).collect(Collectors.toList());
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

    @GetMapping("/fish/details/{productId}")
    public String getDetails(@PathVariable("productId") Long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        model.addAttribute("netDto", new NetDto());
        return "fishdetail.html";
    }

    /*
     * Helper Method which filters Products that match the search keyword, by their
     * name and description.
     */
    private List<Product> filterBySearch(List<Product> products, String search) {
        List<Product> productList = products.stream()
                .filter(p -> p.getName().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
        List<Product> productFilterDesc = products.stream()
                .filter(p -> p.getDescription().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());

        productList.removeAll(productFilterDesc); // Removes potential duplicates without Set()
        productList.addAll(productFilterDesc);
        productList = productList.stream().sorted(Comparator.comparing(Product::getID)).collect(Collectors.toList());
        return productList;
    }

}

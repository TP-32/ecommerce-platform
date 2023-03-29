package com.tp32.ecommerceplatform.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * Simply displays the categories page to the user, showing all products at
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

    @GetMapping("/products/list")
    @ResponseBody
    public List<Product> getProducts(@RequestParam(value = "filter", required = true) Long filter) {
        List<Product> products = productService.getProducts();
        if (filter == 0) // Sort by alphabetical order
            Collections.sort(products, (p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
        else if (filter == 1) // Sort by reverse alphabetical order
            Collections.sort(products, (p1, p2) -> p2.getName().compareToIgnoreCase(p1.getName()));
        else if (filter == 3) // Sort by price (lowest -> highest)
            Collections.sort(products, Comparator.comparing(Product::getPrice));
        else if (filter == 4) // Sort by price (highest -> lowest)
            Collections.sort(products, Comparator.comparing(Product::getPrice).reversed());
        else if (filter == 5) // Sort by newest
            Collections.reverse(products);
        else if (filter == 6) {// Sort by oldest
        } // Do nothing

        return products;
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

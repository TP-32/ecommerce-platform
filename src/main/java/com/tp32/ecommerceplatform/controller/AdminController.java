package com.tp32.ecommerceplatform.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tp32.ecommerceplatform.dto.ProductDto;
import com.tp32.ecommerceplatform.dto.UpdateOrderDto;
import com.tp32.ecommerceplatform.dto.UpdateUserDto;
import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.repository.CategoryRepository;
import com.tp32.ecommerceplatform.service.AdminService;
import com.tp32.ecommerceplatform.service.OrderService;
import com.tp32.ecommerceplatform.service.ProductService;
import com.tp32.ecommerceplatform.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;
    private ProductService productService;
    private CategoryRepository categoryRepository;
    private OrderService orderService;
    private UserService userService;

    public AdminController(AdminService adminService, ProductService productService,
            CategoryRepository categoryRepository, OrderService orderService, UserService userService) {
        this.adminService = adminService;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Attributes used for statistics on the dashboard
        model.addAttribute("sales", orderService.count(orderService.getStatus(4L)));
        model.addAttribute("users", userService.count());

        List<User> customers = userService.getUsers();
        // Reverses the sort order to show most recent
        Collections.reverse(customers);
        // Returns a portion of the list, with 7 being the maximum
        customers = customers.subList(0, Math.min(customers.size(), 7));
        model.addAttribute("customers", customers);

        List<Order> orders = orderService.getOrders();
        // Reverses the sort order to show most recent
        Collections.reverse(orders);
        // Returns a portion of the list, with 10 being the maximum
        orders = orders.subList(0, Math.min(orders.size(), 10));
        model.addAttribute("orders", orders);

        // Formats the total price to 2 decimal places
        Float price = orderService.sumPrice();
        if (price == null)
            price = 0F;
        model.addAttribute("earning", String.format("£%.2f", price));
        return "admin-dashboard.html";
    }

    @GetMapping("/analytics")
    public String analytics(Model model) {
        // Attributes used for statistics on the dashboard
        model.addAttribute("sales", orderService.count(orderService.getStatus(4L)));
        model.addAttribute("users", userService.count());

        // Formats the total price to 2 decimal places
        Float price = orderService.sumPrice();
        if (price == null)
            price = 0F;
        model.addAttribute("earning", String.format("£%.2f", price));
        model.addAttribute("product", productService.getProduct(5L));
        return "admin-analytics.html";
    }

    @GetMapping("/customers")
    public String customers(Model model, @RequestParam(value = "userId", required = false) Long id) {
        if (id != null) {
            model.addAttribute("userDto", new UpdateUserDto());
            model.addAttribute("customer", userService.getUser(id));
            model.addAttribute("roles", userService.getRoles());
            return "admin-update-customer.html";
        }

        model.addAttribute("customers", userService.getUsers());
        model.addAttribute("reverseSortDir", "desc");
        return "admin-customers.html";
    }

    @PostMapping("/customers/update")
    public ModelAndView updateCustomer(@ModelAttribute UpdateUserDto userDto, @RequestParam(value = "userId") Long id,
            RedirectAttributes redirect) {
        userService.updateUser(id, userDto);
        ModelAndView model = new ModelAndView("redirect:/admin/customers");
        return model;
    }

    @GetMapping("/customers/delete")
    public ModelAndView deleteCustomer(@RequestParam(value = "userId", required = true) Long id,
            RedirectAttributes redirect) {
        userService.deleteUser(id);
        ModelAndView model = new ModelAndView("redirect:/admin/customers");
        return model;
    }

    @GetMapping("/customers/{sort}")
    public String customers(Model model, @PathVariable("sort") String sort, @RequestParam("sortDir") String sortDir) {
        if (sort.equals("role"))
            sort = "role.name";
        List<User> users = userService.getUsersWithSort(sort, sortDir);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("customers", users);
        return "admin-customers.html";
    }

    @GetMapping("/products")
    public String products(Model model, @RequestParam(value = "productId", required = false) Long id,
            @RequestParam(value = "search", required = false) String search) {
        if (id != null) {
            model.addAttribute("productDto", new ProductDto());
            model.addAttribute("product", productService.getProduct(id));
            model.addAttribute("filter", "true");
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin-update-product.html";
        } else if (search != null && !search.isEmpty()) {
            List<Product> products = this.filterBySearch(productService.getProducts(), search);
            model.addAttribute("search", search);
            model.addAttribute("products", products);
            model.addAttribute("reverseSortDir", "desc");
            return "admin-products.html";
        }

        model.addAttribute("products", productService.getProducts());
        model.addAttribute("reverseSortDir", "desc");
        return "admin-products.html";
    }

    @GetMapping("/products/new")
    public String createProduct(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin-create-product.html";
    }

    @PostMapping("/products/save")
    public ModelAndView saveProduct(@ModelAttribute ProductDto productDto, RedirectAttributes redirect) {
        Product product = adminService.createProduct(productDto);
        redirect.addFlashAttribute("preProduct", product);
        ModelAndView model = new ModelAndView("redirect:/admin/products/new");
        return model;
    }

    @PostMapping("/products/update")
    public ModelAndView updateProduct(@ModelAttribute ProductDto productDto, @RequestParam(value = "productId") Long id,
            RedirectAttributes redirect) {
        productService.updateProduct(id, productDto);
        ModelAndView model = new ModelAndView("redirect:/admin/products");
        return model;
    }

    @GetMapping("/products/delete")
    public ModelAndView deleteProduct(@RequestParam(value = "productId", required = true) Long id,
            RedirectAttributes redirect) {
        productService.deleteProduct(id);
        ModelAndView model = new ModelAndView("redirect:/admin/products");
        return model;
    }

    @GetMapping("/products/{sort}")
    public String products(Model model, @PathVariable("sort") String sort, @RequestParam("sortDir") String sortDir,
            @RequestParam(value = "search", required = false) String search) {
        if (sort.equals("stock"))
            sort = "inventory.stock";
        List<Product> products;
        if (search != null && !search.isEmpty()) {
            products = this.filterBySearch(productService.getProducts(), search);
            products = productService.getProductsWithSort(products, sort, sortDir);
            model.addAttribute("search", search.toLowerCase());
        } else products = productService.getProductsWithSort(sort, sortDir);
        
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("products", products);
        return "admin-products.html";
    }

    @GetMapping("/products/low")
    public String productsLow(Model model) {
        List<Product> products = productService.getProducts().stream().filter(p -> p.getInventory().getStock() < 10)
                .collect(Collectors.toList());
        model.addAttribute("filter", "true");
        model.addAttribute("products", products);
        return "admin-products.html";
    }

    @GetMapping("/orders")
    public String orders(Model model, @RequestParam(value = "orderId", required = false) Long id) {
        if (id != null) {
            model.addAttribute("orderDto", new UpdateOrderDto());
            model.addAttribute("order", orderService.getOrder(id));
            model.addAttribute("status", orderService.getStatus());
            return "admin-update-order.html";
        }

        model.addAttribute("status", orderService.getStatus());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("reverseSortDir", "desc");
        return "admin-orders.html";
    }

    @PostMapping("/orders/update")
    public ModelAndView updateOrder(@ModelAttribute UpdateOrderDto orderDto, @RequestParam(value = "orderId") Long id,
            RedirectAttributes redirect) {
        orderService.updateOrder(id, orderDto);
        ModelAndView model = new ModelAndView("redirect:/admin/orders");
        return model;
    }

    @GetMapping("/orders/delete")
    public ModelAndView deleteOrder(@RequestParam(value = "orderId", required = true) Long id,
            RedirectAttributes redirect) {
        orderService.deleteOrder(id);
        ModelAndView model = new ModelAndView("redirect:/admin/orders");
        return model;
    }

    @GetMapping("/orders/{sort}")
    public String orders(Model model, @PathVariable("sort") String sort, @RequestParam("sortDir") String sortDir) {
        if (sort.equals("name"))
            sort = "user.firstName";
        List<Order> orders = orderService.getOrdersWithSort(sort, sortDir);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("orders", orders);
        model.addAttribute("status", orderService.getStatus());
        return "admin-orders.html";
    }

    @GetMapping("/orders/list")
    @ResponseBody
    public List<Order> getOrders(@RequestParam(value = "status", required = false) Long status) {
        if (status == 0)
            return orderService.getOrders();
        return orderService.getOrders().stream()
                .filter(o -> o.getStatus().getName().equals(orderService.getStatus(status).getName()))
                .collect(Collectors.toList());
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
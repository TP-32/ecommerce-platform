package com.tp32.ecommerceplatform.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.tp32.ecommerceplatform.model.OrderItem;
import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.repository.CategoryRepository;
import com.tp32.ecommerceplatform.service.OrderItemsService;
import com.tp32.ecommerceplatform.service.OrderService;
import com.tp32.ecommerceplatform.service.ProductService;
import com.tp32.ecommerceplatform.service.UserService;
import com.tp32.ecommerceplatform.service.impl.OrderItemsServiceImpl.Popular;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ProductService productService;
    private CategoryRepository categoryRepository;
    private OrderService orderService;
    private OrderItemsService orderItemsService;
    private UserService userService;

    public AdminController(ProductService productService,
            CategoryRepository categoryRepository, OrderService orderService, OrderItemsService orderItemsService,
            UserService userService) {

        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.orderService = orderService;
        this.orderItemsService = orderItemsService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Attributes used for statistics on the dashboard
        model.addAttribute("sales", orderService.count(orderService.getStatus(4L)));
        model.addAttribute("users", userService.count());
        model.addAttribute("products", productService.count());

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
        model.addAttribute("income", String.format("£%,.2f", price));
        return "admin-dashboard.html";
    }

    @GetMapping("/analytics")
    public String analytics(Model model) {
        // Attributes used for statistics on the dashboard
        model.addAttribute("sales", orderService.count(orderService.getStatus(4L)));
        model.addAttribute("users", userService.count());
        model.addAttribute("products", productService.count());

        // Formats the total price to 2 decimal places
        Float price = orderService.sumPrice();
        if (price == null)
            price = 0F;
        model.addAttribute("income", String.format("£%,.2f", price));
        Popular popular = orderItemsService.popularProduct();
        model.addAttribute("product", popular.getProduct());
        model.addAttribute("sale", popular.getCount());
        return "admin-analytics.html";
    }

    @GetMapping("/customers")
    public String customers(Model model, @RequestParam(value = "userId", required = false) Long id) {
        if (id != null) {
            User user = userService.getUser(id);

            model.addAttribute("userDto", new UpdateUserDto(user));
            model.addAttribute("customer", userService.getUser(id));
            model.addAttribute("roles", userService.getRoles());
            return "admin-update-customer.html";
        }

        model.addAttribute("customers", userService.getUsers());
        model.addAttribute("reverseSortDir", "desc");
        return "admin-customers.html";
    }

    @PostMapping("/customers/update")
    public String updateCustomer(@Valid @ModelAttribute("userDto") UpdateUserDto userDto, BindingResult result,
            Model model,
            @RequestParam(value = "userId") Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean updateError = userDto.getEmail().equals(user.getEmail());
        boolean emailError = userService.existsByEmail(userDto.getEmail());
        if (!result.hasErrors() && !updateError && !emailError) {
            userService.updateUser(id, userDto);
            model.addAttribute("customers", userService.getUsers());
            model.addAttribute("reverseSortDir", "desc");
            return "admin-customers.html";
        }
        if (updateError)
            model.addAttribute("updateError", "You cannot modify the account you are currently logged in as.");

        if (emailError)
            model.addAttribute("emailError", "Email already exists.");
            
        model.addAttribute("userDto", userDto);
        model.addAttribute("customer", userService.getUser(id));
        model.addAttribute("roles", userService.getRoles());
        return "admin-update-customer.html";
    }

    @GetMapping("/customers/delete")
    public ModelAndView deleteCustomer(@RequestParam(value = "userId", required = true) Long id,
            HttpServletResponse response) throws IOException {
        if (userService.getUser(id).getEmail()
                .equals(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail())) {
            Cookie cookie = new Cookie("Authorization", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);

            response.addCookie(cookie);
            response.sendRedirect("/");
        }
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
            Product product = productService.getProduct(id);

            model.addAttribute("productDto", new ProductDto(product));
            model.addAttribute("product", product);
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
    public String createProduct(Model model, @RequestParam(value = "error", required = false) String error,
            RedirectAttributes redirect) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin-create-product.html";
    }

    @PostMapping("/products/save")
    public String saveProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult result,
            Model model) {
        if (!result.hasErrors()) {
            Product product = productService.createProduct(productDto);
            model.addAttribute("preProduct", product);
        }

        model.addAttribute("categories", categoryRepository.findAll());
        return "admin-create-product.html";
    }

    @PostMapping("/products/update")
    public String updateProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result,
            Model model, @RequestParam(value = "productId") Long id) {
        if (!result.hasErrors()) {
            productService.updateProduct(id, productDto);
            model.addAttribute("products", productService.getProducts());
            model.addAttribute("reverseSortDir", "desc");
            return "admin-products.html";
        }
        model.addAttribute("productDto", productDto);
        model.addAttribute("product", productService.getProduct(id));
        model.addAttribute("filter", "true");
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin-update-product.html";
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
        } else
            products = productService.getProductsWithSort(sort, sortDir);

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
    public String orders(Model model, @RequestParam(value = "orderId", required = false) Long id,
            @RequestParam(value = "status", required = false) Long status) {
        if (id != null) {
            model.addAttribute("orderDto", new UpdateOrderDto());
            model.addAttribute("order", orderService.getOrder(id));
            model.addAttribute("status", orderService.getStatus());
            if (orderService.getOrder(id) == null)
                return "";

            model.addAttribute("orderitems", orderService.getOrder(id).getOrderItems());
            return "admin-update-order.html";
        }

        if (status != null) {
            model.addAttribute("orders",
                    orderService.getOrders().stream()
                            .filter(o -> o.getStatus().getName().equals(orderService.getStatus(status).getName()))
                            .collect(Collectors.toList()));
        } else {
            List<Order> orders = orderService.getOrders().stream()
                    .filter(o -> !o.getStatus().getName().equals("Completed")).collect(Collectors.toList());
            if (orders.isEmpty())
                model.addAttribute("message", "Filter by 'Completed' instead, maybe.");

            model.addAttribute("orders", orderService.getOrders());
        }

        model.addAttribute("status", orderService.getStatus());
        model.addAttribute("reverseSortDir", "desc");
        return "admin-orders.html";
    }

    @PostMapping("/orders/update")
    public String updateOrder(@Valid @ModelAttribute("orderDto") UpdateOrderDto orderDto, BindingResult result,
            Model model, @RequestParam(value = "orderId") Long id) {
        boolean stockError = false;
        Order order = orderService.getOrder(id);
        if (!order.getStatus().getName().equals("Completed") && orderDto.getStatus().equals("Completed")) {
            for (OrderItem orderItem : order.getOrderItems()) {
                if (orderItem.getQuantity() > orderItem.getProduct().getInventory().getStock()) {
                    stockError = true;
                }
            }
        }
        if (!result.hasErrors() && !stockError) {
            orderService.updateOrder(id, orderDto);
            model.addAttribute("orders", orderService.getOrders());
            model.addAttribute("status", orderService.getStatus());
            model.addAttribute("reverseSortDir", "desc");
            return "admin-orders.html";
        }
        model.addAttribute("orderitems", orderService.getOrder(id).getOrderItems());
        if (stockError)
            model.addAttribute("stockError",
                    "Cannot be marked as Completed: Insufficient stock available for one or more products.");
        model.addAttribute("orderDto", orderDto);
        model.addAttribute("order", orderService.getOrder(id));
        model.addAttribute("status", orderService.getStatus());
        return "admin-update-order.html";
    }

    @GetMapping("/orders/delete")
    public ModelAndView deleteOrder(@RequestParam(value = "orderId", required = true) Long id,
            RedirectAttributes redirect) {
        orderService.deleteOrder(id);
        ModelAndView model = new ModelAndView("redirect:/admin/orders");
        return model;
    }

    @GetMapping("/orders/product/delete")
    public ModelAndView deleteOrderItem(@RequestParam(value = "orderId", required = true) Long orderId,
            @RequestParam(value = "productId", required = true) Long productId) {
        List<OrderItem> orderItems = orderItemsService.removeOrderItem(orderId, productId);
        if (orderItems.isEmpty())
            return new ModelAndView("redirect:/admin/orders");

        return new ModelAndView("redirect:/admin/orders?orderId=" + orderId);
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
    public List<Order> getOrders(@RequestParam(value = "status", required = true) Long status) {
        if (status == 0)
            return orderService.getOrders();

        return orderService.getOrders().stream()
                .filter(o -> o.getStatus().getName().equals(orderService.getStatus(status).getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/notification")
    public String notif(Model model) {
        List<Product> products = productService.getProducts().stream().filter(p -> p.getInventory().getStock() <= 0)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "admin-notification.html";
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
package com.tp32.ecommerceplatform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tp32.ecommerceplatform.model.Inventory;
import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.OrderItem;
import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.repository.OrderItemsRepository;
import com.tp32.ecommerceplatform.service.OrderItemsService;
import com.tp32.ecommerceplatform.service.OrderService;
import com.tp32.ecommerceplatform.service.ProductService;

@Service
public class OrderItemsServiceImpl implements OrderItemsService {

    private OrderItemsRepository orderItemsRepository;
    private OrderService orderService;
    private ProductService productService;

    public OrderItemsServiceImpl(OrderItemsRepository orderItemsRepository, OrderService orderService,
            ProductService productService) {
        this.orderItemsRepository = orderItemsRepository;
        this.orderService = orderService;
        this.productService = productService;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemsRepository.save(orderItem);
    }

    @Override
    public OrderItem getOrderItem(Long orderId, Long productId) {
        Order order = orderService.getOrder(orderId);
        Product product = productService.getProduct(productId);
        OrderItem orderItem = orderItemsRepository.findByOrderProduct(order, product);
        return orderItemsRepository.findById(orderItem.getID()).get();
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemsRepository.findAll();
    }

    @Override
    public List<OrderItem> removeOrderItem(Long orderId, Long productId) {
        Order order = orderService.getOrder(orderId);
        Product product = productService.getProduct(productId);
        OrderItem orderItem = orderItemsRepository.findByOrderProduct(order, product);
        order.getOrderItems().remove(orderItem);
        order.setPrice(order.getPrice() - (orderItem.getProduct().getPrice() * orderItem.getQuantity()));
        
        Inventory inventory = orderItem.getProduct().getInventory();
        inventory.setStock(inventory.getStock() + orderItem.getQuantity());
        orderItem.getProduct().setInventory(inventory);
        
        orderItemsRepository.delete(orderItem);

        if (order.getOrderItems().isEmpty()) {
            orderService.deleteOrder(order.getID());
        }
        return order.getOrderItems();
    }

    @Override
    public Popular popularProduct() {
        Map<Product, Integer> productCount = new HashMap<>();
        List<OrderItem> orderItems = orderItemsRepository.findAll();
        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            if (productCount.containsKey(product)) {
                int current = productCount.get(product);
                productCount.put(product, current + quantity);
            } else
                productCount.put(product, quantity);
        }

        Product product = null;
        int count = 0;

        for (Map.Entry<Product, Integer> entry : productCount.entrySet()) {
            if (entry.getValue() > count) {
                product = entry.getKey();
                count = entry.getValue();
            }
        }

        return new Popular(product, count);
    }

    public class Popular {
        private Product product;
        private int count;

        public Popular(Product product, int count) {
            this.product = product;
            this.count = count;
        }

        public Product getProduct() {
            return this.product;
        }

        public int getCount() {
            return this.count;
        }
    }

}

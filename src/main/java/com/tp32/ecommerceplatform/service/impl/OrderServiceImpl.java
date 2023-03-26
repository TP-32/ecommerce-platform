package com.tp32.ecommerceplatform.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tp32.ecommerceplatform.dto.UpdateOrderDto;
import com.tp32.ecommerceplatform.model.Inventory;
import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.OrderItem;
import com.tp32.ecommerceplatform.model.Status;
import com.tp32.ecommerceplatform.repository.OrderRepository;
import com.tp32.ecommerceplatform.repository.StatusRepository;
import com.tp32.ecommerceplatform.service.OrderService;
import com.tp32.ecommerceplatform.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private StatusRepository statusRepository;
    private UserService userService;

    public OrderServiceImpl(OrderRepository orderRepository, StatusRepository statusRepository,
            UserService userService) {
        this.orderRepository = orderRepository;
        this.statusRepository = statusRepository;
        this.userService = userService;
    }

    @Override
    public long count(Status status) {
        return orderRepository.count(status);
    }

    @Override
    public Float sumPrice() {
        return orderRepository.sumPrice();
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrders(Long id) {
        List<Order> orders = orderRepository.findAllByUser(userService.getUser(id));
        Collections.reverse(orders);
        return orders;
    }

    @Override
    public List<Status> getStatus() {
        // Sorts the Status in the list by ID to maintain correct order
        return statusRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Order updateOrder(Long id, UpdateOrderDto orderDto) {
        Order updateOrder = orderRepository.findById(id).get();
        //updateOrder.setPrice(orderDto.getPrice());

        Status status = statusRepository.findByName(orderDto.getStatus()).get();

        updateOrder.setStatus(status);
        // Date and User cannot be modified

        orderRepository.save(updateOrder);
        return updateOrder;
    }

    @Override
    public Order deleteOrder(Long id) {
        Order order = orderRepository.findById(id).get();

        for (OrderItem orderItem : order.getOrderItems()) {
            Inventory inventory = orderItem.getProduct().getInventory();
            inventory.setStock(inventory.getStock() + orderItem.getQuantity());
            orderItem.getProduct().setInventory(inventory);
        }

        orderRepository.deleteById(id);
        return order;
    }

    @Override
    public List<Order> getOrdersWithSort(String field, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(field).ascending()
                : Sort.by(field).descending();

        return orderRepository.findAll(sort);
    }

    @Override
    public List<Order> getOrdersWithSort(Long id, String field, String direction) {
        List<Order> orders = getOrdersWithSort(field, direction);
        List<Order> orders2 = getOrders(id);

        orders.retainAll(orders2);
        return orders;
    }

    @Override
    public Status getStatus(Long id) {
        if (statusRepository.existsById(id))
            return statusRepository.findById(id).get();
        return null;
    }

    // New changes

    @Override
    public Order create() {
        Order order = new Order();
        order.setOrderItems(new ArrayList<>());
        order.setPrice(0F);
        return save(order);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.isEmpty() ? null : order.get();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}

package com.tp32.ecommerceplatform.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tp32.ecommerceplatform.dto.UpdateOrderDto;
import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.Status;
import com.tp32.ecommerceplatform.repository.OrderRepository;
import com.tp32.ecommerceplatform.repository.StatusRepository;
import com.tp32.ecommerceplatform.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private StatusRepository statusRepository;

    public OrderServiceImpl(OrderRepository orderRepository, StatusRepository statusRepository) {
        this.orderRepository = orderRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public long count() {
        return orderRepository.count();
    }

    @Override
    public Float sumPrice() {
        return orderRepository.sumPrice();
    }

    @Override
    public Order getOrder(Long id) {
        if (orderRepository.existsById(id))
            return orderRepository.findById(id).get();
        return null;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Status> getStatus() {
        // Sorts the Status in the list by ID to maintain correct order
        return statusRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Order updateOrder(Long id, UpdateOrderDto orderDto) {
        Order updateOrder = orderRepository.findById(id).get();
        updateOrder.setPrice(orderDto.getPrice());

        Status status = statusRepository.findByName(orderDto.getStatus()).get();
        if (!updateOrder.getStatus().getName().equals("Completed") && status.getName().equals("Completed")) {
            // TODO: Query the OrderItems, for each Product reduce the Stock by 1
            System.out.println("Stock to be reduced.");
        } else if (updateOrder.getStatus().getName().equals("Completed") && !status.getName().equals("Completed")) {
            // TODO: Query the OrderItems, for each Product increase the Stock by 1
            System.out.println("Stock to be increased, as this order is no longer valid.");
        }

        // TODO: Query the OrderItems, if any Product Stock = 0, then Status = Declined
        
        updateOrder.setStatus(status);
        // Date and User cannot be modified

        orderRepository.save(updateOrder);
        return updateOrder;
    }

    @Override
    public List<Order> getOrdersWithSort(String field, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
        Sort.by(field).ascending() :
        Sort.by(field).descending();

        return orderRepository.findAll(sort);
    }

    @Override
    public Status getStatus(Long id) {
        if (statusRepository.existsById(id)) return statusRepository.findById(id).get();
        return null;
    }
    
}
package com.tp32.ecommerceplatform.service;

import java.util.List;

import com.tp32.ecommerceplatform.dto.UpdateOrderDto;
import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.Status;

public interface OrderService {
    Order getOrder(Long id);
    Order updateOrder(Long id, UpdateOrderDto orderDto);
    Order deleteOrder(Long id);
    List<Order> getOrders();
    List<Order> getOrdersWithSort(String field, String direction);
    List<Order> getOrders(Long id);
    Status getStatus(Long id);
    List<Status> getStatus();
    Float sumPrice();
    long count();
    long count(Status status);
}

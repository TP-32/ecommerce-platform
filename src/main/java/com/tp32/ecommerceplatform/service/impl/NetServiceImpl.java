package com.tp32.ecommerceplatform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tp32.ecommerceplatform.model.Net;
import com.tp32.ecommerceplatform.model.NetItem;
import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.OrderItem;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.repository.NetRepository;
import com.tp32.ecommerceplatform.repository.StatusRepository;
import com.tp32.ecommerceplatform.service.NetService;
import com.tp32.ecommerceplatform.service.OrderItemsService;
import com.tp32.ecommerceplatform.service.OrderService;

@Service
public class NetServiceImpl implements NetService {

    private NetRepository netRepository;
    private OrderService orderService;
    private OrderItemsService orderItemsService;
    private StatusRepository statusRepository;
    
    public NetServiceImpl(NetRepository netRepository, OrderService orderService, OrderItemsService orderItemsService, StatusRepository statusRepository) {
        this.netRepository = netRepository;
        this.orderItemsService = orderItemsService;
        this.orderService = orderService;
        this.statusRepository = statusRepository;
    }

    @Override
    public Net create(User user) {
        Net net = new Net();
        net.setNetItems(new ArrayList<>());
        net.setPrice(0F);
        net.setUser(user);
        return netRepository.save(net);
    }

    @Override
    public Net save(Net net) {
        return netRepository.save(net);
    }

    @Override
    public Net getNet(Long id) {
        return netRepository.findById(id).get();
    }

    @Override
    public List<Net> getAllNets() {
        return netRepository.findAll();
    }

    @Override
    public Net close(Long id) {
        Net net = getNet(id);
        this.createOrder(net);
        return net;
    }

    private void createOrder(Net net) {
        Order order = new Order();
        order.setUser(net.getUser());
        order.setPrice(net.getPrice());
        order.setStatus(statusRepository.findByName("Pending").get());
        order.setTime(new Date());

        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (NetItem netItem : net.getNetItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(netItem.getProduct());
            orderItem.setQuantity(netItem.getQuantity());
            orderItems.add(orderItem);
            orderService.save(orderItem.getOrder());
            orderItemsService.save(orderItem);
        }

        netRepository.delete(net);
        order.setOrderItems(orderItems);
        orderService.save(order);
    }

    public Net findByUser(User user) {
        return netRepository.findByUser(user);
    }

}

package com.tp32.ecommerceplatform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tp32.ecommerceplatform.model.Net;
import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.repository.NetRepository;
import com.tp32.ecommerceplatform.repository.ProductRepository;
import com.tp32.ecommerceplatform.service.NetService;

@Service
public class NetServiceImpl implements NetService {

    private NetRepository netRepository;
    private ProductRepository productRepository;

    public NetServiceImpl(NetRepository netRepository, ProductRepository productRepository) {
        this.netRepository = netRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(User user) {
        List<Net> netList = netRepository.findAllByUser(user);
        List<Product> products = new ArrayList<>();
        
        for (Net net : netList) {
            products.add(net.getProduct());
        }
        return products;
    }

    @Override
    public Net addProduct(User user, Product product) {
        Net net = new Net();
        net.setProduct(product);
        net.setUser(user);
        net.setQuantity(1);
        netRepository.save(net);
        if (product.getInventory().getStock() - 1 >= 0) {
            product.getInventory().setStock(product.getInventory().getStock() - 1);
            productRepository.save(product);   
        } else return null;
        return net;
    }
}

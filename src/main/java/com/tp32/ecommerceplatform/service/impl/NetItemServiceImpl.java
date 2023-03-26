package com.tp32.ecommerceplatform.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tp32.ecommerceplatform.model.Inventory;
import com.tp32.ecommerceplatform.model.Net;
import com.tp32.ecommerceplatform.model.NetItem;
import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.repository.NetItemRepository;
import com.tp32.ecommerceplatform.service.NetItemService;
import com.tp32.ecommerceplatform.service.NetService;
import com.tp32.ecommerceplatform.service.ProductService;
import com.tp32.ecommerceplatform.service.UserService;

@Service
public class NetItemServiceImpl implements NetItemService {

    private NetItemRepository netItemRepository;
    private NetService netService;
    private ProductService productService;
    private UserService userService;

    public NetItemServiceImpl(NetItemRepository netItemRepository, NetService netService, ProductService productService, UserService userService) {
        this.netItemRepository = netItemRepository;
        this.netService = netService;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public NetItem create(Long userId, Long productId, int quantity) {
        Net net = netService.findByUser(userService.getUser(userId));
        if (net == null) net = netService.create(userService.getUser(userId));

        Product product = productService.getProduct(productId);
        List<NetItem> netItems = net.getNetItems();
        // If this Net has a NetItem that has a Product that matches product, then increase quantity instead of creating a new NetItem

        NetItem item = containsProduct(product, netItems);
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
            save(item);
            return item;
        }

        NetItem netItem = new NetItem();
        Inventory inventory = product.getInventory();
        inventory.setStock(inventory.getStock() - quantity);
        product.setInventory(product.getInventory());

        netItem.setNet(net);
        netItem.setProduct(product);
        netItem.setQuantity(quantity);
        save(netItem);

        net.getNetItems().add(netItem);
        net.setPrice(net.getPrice() + (product.getPrice() * quantity));
        netService.save(net);
        return netItem;
    }

    private NetItem containsProduct(Product product, List<NetItem> netItems) {
        for (NetItem netItem : netItems) {
            if (netItem.getProduct().getID().equals(product.getID())) return netItem;
        }
        return null;
    }

    @Override
    public NetItem save(NetItem netItem) {
        return netItemRepository.save(netItem);
    }

    @Override
    public List<NetItem> getAllNetItems() {
        return netItemRepository.findAll();
    }

    @Override
    public void removeNetItem(User user, Long productId) {
        Net net = netService.findByUser(user);
        Product product = productService.getProduct(productId);
        NetItem netItem = netItemRepository.findByNetProduct(net, product);
        net.getNetItems().remove(netItem);
        net.setPrice(net.getPrice() - (netItem.getProduct().getPrice() * netItem.getQuantity()));
        
        Inventory inventory = netItem.getProduct().getInventory();
        inventory.setStock(inventory.getStock() + netItem.getQuantity());
        netItem.getProduct().setInventory(inventory);
        netItemRepository.delete(netItem);    
    }
}
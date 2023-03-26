package com.tp32.ecommerceplatform.service;

import java.util.List;

import com.tp32.ecommerceplatform.model.NetItem;
import com.tp32.ecommerceplatform.model.User;

public interface NetItemService {
    NetItem create(Long userId, Long productId, int quantity);
    NetItem save(NetItem netItem);
    List<NetItem> getAllNetItems();
    void removeNetItem(User user, Long productId);
}

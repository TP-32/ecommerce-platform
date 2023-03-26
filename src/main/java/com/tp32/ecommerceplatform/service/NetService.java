package com.tp32.ecommerceplatform.service;

import java.util.List;

import com.tp32.ecommerceplatform.model.Net;
import com.tp32.ecommerceplatform.model.User;

public interface NetService {
    Net create(User user);
    Net save(Net net);
    Net getNet(Long id);
    List<Net> getAllNets();
    Net close(Long id);
    Net findByUser(User user);
}

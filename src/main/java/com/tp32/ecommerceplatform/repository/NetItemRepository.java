package com.tp32.ecommerceplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tp32.ecommerceplatform.model.Net;
import com.tp32.ecommerceplatform.model.NetItem;
import com.tp32.ecommerceplatform.model.Product;

public interface NetItemRepository extends JpaRepository<NetItem, Long> {
    @Query("SELECT ni FROM NetItem ni WHERE ni.net = ?1 and ni.product = ?2")
    NetItem findByNetProduct(Net net, Product product);
}
package com.tp32.ecommerceplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp32.ecommerceplatform.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}

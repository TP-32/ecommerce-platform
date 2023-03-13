package com.tp32.ecommerceplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp32.ecommerceplatform.model.Net;
import com.tp32.ecommerceplatform.model.User;

public interface NetRepository extends JpaRepository<Net, Long> {
    List<Net> findAllByUser(User user);
}

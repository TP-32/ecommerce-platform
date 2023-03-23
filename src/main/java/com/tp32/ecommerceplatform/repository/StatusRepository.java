package com.tp32.ecommerceplatform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp32.ecommerceplatform.model.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findAll();
    Optional<Status> findByName(String name);
    Boolean existsByName(String name);
}

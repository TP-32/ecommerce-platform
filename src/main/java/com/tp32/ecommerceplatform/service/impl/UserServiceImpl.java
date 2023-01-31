package com.tp32.ecommerceplatform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.repository.UserRepository;
import com.tp32.ecommerceplatform.service.UserService;

import jakarta.transaction.Transactional;

/**
 * Concrete implementation of UserService.
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    @Override
    public User findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        User localUser = userRepository.findByEmail(user.getEmail());

        // User roles should be queried here
        if (localUser == null) {
            localUser = userRepository.save(user);
        }

        return localUser;
    }
}

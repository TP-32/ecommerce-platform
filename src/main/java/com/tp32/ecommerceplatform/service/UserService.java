package com.tp32.ecommerceplatform.service;

import com.tp32.ecommerceplatform.model.User;

/**
 * Abstract implementation of services required for the User.
 */
public interface UserService {

    User findById(Long id);
    User findByFirstName(String firstName);
    User findByLastName(String lastName);
    User findByEmail(String email);
    User save(User user);
    
    User createUser(User user);
}

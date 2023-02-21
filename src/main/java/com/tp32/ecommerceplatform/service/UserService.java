package com.tp32.ecommerceplatform.service;

import com.tp32.ecommerceplatform.dto.LoginDto;
import com.tp32.ecommerceplatform.dto.RegisterDto;

/**
 * Abstract implementation of services required for the User.
 */
public interface UserService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}

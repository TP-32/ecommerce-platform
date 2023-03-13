package com.tp32.ecommerceplatform.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tp32.ecommerceplatform.dto.JwtResponse;
import com.tp32.ecommerceplatform.dto.LoginDto;
import com.tp32.ecommerceplatform.dto.RegisterDto;
import com.tp32.ecommerceplatform.exception.InputException;
import com.tp32.ecommerceplatform.model.Role;
import com.tp32.ecommerceplatform.model.Token;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.repository.RoleRepository;
import com.tp32.ecommerceplatform.repository.TokenRepository;
import com.tp32.ecommerceplatform.repository.UserRepository;
import com.tp32.ecommerceplatform.security.jwt.JwtToken;
import com.tp32.ecommerceplatform.service.UserService;

/**
 * Concrete implementation of UserService.
 */
@Service
public class UserServiceImpl implements UserService {

    private AuthenticationManager authManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private TokenRepository tokenRepository;
    private PasswordEncoder passwordEncoder;
    private JwtToken jwtToken;

    public UserServiceImpl(AuthenticationManager authManager, UserRepository userRepository,
            RoleRepository roleRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtToken jwtToken) {
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtToken = jwtToken;
    }

    /**
     * Validates the user with the details stored in the database
     */
    @Override
    public JwtResponse login(LoginDto loginDto) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        User user = userRepository.findByEmail(loginDto.getEmail())
            .orElseThrow();

        HashMap<String, Object> test = new HashMap<>();
        test.put("role", user.getRole());

        String token = jwtToken.generateToken(user, test);
        this.revokeUserToken(user);
        this.saveToken(user, token);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        return jwtResponse;
    }

    /**
     * Validates the registered user against any pre-existing users.
     * Creates a new user, with inputted values and the default role (USER)
     * Saves the user to the database and returns a response to the client
     */
    @Override
    public JwtResponse register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new InputException(HttpStatus.BAD_REQUEST, "This email is already being used.");
        }

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRole(userRole);

        userRepository.save(user);
        String token = jwtToken.generateToken(user);
        this.saveToken(user, token);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        return jwtResponse;
        //return new ResponseEntity<>("User has been successfully registered", HttpStatus.OK);
    }

    private void saveToken(User user, String jwtToken) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }

    private void revokeUserToken(User user) {
      List<Token> validTokens = tokenRepository.findAllValidTokenByUser(user.getID());
      if (validTokens.isEmpty()) return;
      validTokens.forEach(token -> {
        token.setExpired(true);
        token.setRevoked(true);
      });
      tokenRepository.saveAll(validTokens);
    }
}

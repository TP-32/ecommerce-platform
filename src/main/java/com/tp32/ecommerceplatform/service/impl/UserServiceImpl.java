package com.tp32.ecommerceplatform.service.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tp32.ecommerceplatform.dto.JwtResponse;
import com.tp32.ecommerceplatform.dto.LoginDto;
import com.tp32.ecommerceplatform.dto.RegisterDto;
import com.tp32.ecommerceplatform.dto.UpdateUserDto;
import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.Role;
import com.tp32.ecommerceplatform.model.Token;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.repository.OrderRepository;
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
    private OrderRepository orderRepository;
    private TokenRepository tokenRepository;
    private PasswordEncoder passwordEncoder;
    private JwtToken jwtToken;

    public UserServiceImpl(AuthenticationManager authManager, UserRepository userRepository,
            RoleRepository roleRepository, OrderRepository orderRepository, TokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            JwtToken jwtToken) {
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.orderRepository = orderRepository;
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

        HashMap<String, Object> role = new HashMap<>();
        role.put("role", user.getRole());

        String token = jwtToken.generateToken(user, role);
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
        if (validTokens.isEmpty())
            return;
        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    public User getUser(Long id) {
        if (userRepository.existsById(id))
            return userRepository.findById(id).get();

        return null;
    }

    @Override
    public List<User> getUsersWithSort(String field, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(field).ascending()
                : Sort.by(field).descending();

        return userRepository.findAll(sort);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public User updateUser(Long id, UpdateUserDto userDto) {
        User updateUser = userRepository.findById(id).get();

        updateUser.setFirstName(userDto.getFirstName());
        updateUser.setLastName(userDto.getLastName());
        updateUser.setEmail(userDto.getEmail());

        // Password remains the same if no password was received from the Dto
        if (!userDto.getPassword().isEmpty())
            updateUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        updateUser.setRole(roleRepository.findByName(userDto.getRole()).get());

        userRepository.save(updateUser);
        return updateUser;
    }

    @Override
    public User deleteUser(Long id) {
        User user = userRepository.findById(id).get();

        // Removes any tokens that were previously archived for this user.
        for (Token token : tokenRepository.findAllValidTokenByUser(id)) {
            tokenRepository.delete(token);
        }

        // Sets the role of the user to null (for db deletion)
        user.setRole(null);

        // Removes all orders that this user previously had, as this is no longer
        // relevant.
        for (Order order : orderRepository.findAllByUser(user)) {
            orderRepository.delete(order);
        }

        // Deletes the user's account
        userRepository.deleteById(id);
        return user;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}

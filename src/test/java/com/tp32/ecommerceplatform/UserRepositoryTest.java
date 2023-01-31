package com.tp32.ecommerceplatform;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.repository.UserRepository;

/**
 * Checks to see if the User Repository is working and stores the user into the database correctly.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("test1234");
        user.setFirstName("test");
        user.setLastName("bob");

        User savedUser;
        if (userRepository.findByEmail("test@gmail.com") != null) savedUser = userRepository.findByEmail("test@gmail.com");
        else
            savedUser = userRepository.save(user);

        User existUser = entityManager.find(User.class, savedUser.getID());
        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
    }
}

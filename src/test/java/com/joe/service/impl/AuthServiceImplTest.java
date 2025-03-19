package com.joe.service.impl;

import com.joe.model.User;
import com.joe.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceImplTest {

    @Autowired
    private AuthService authService;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password");

        User createdUser = authService.createUser(user);
        assertNotNull(createdUser.getId());
    }
}
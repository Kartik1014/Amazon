package com.sweetshop.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole("USER");
        
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
    }

    @Test
    void testUserWithId() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void testDefaultRole() {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("pass");
        user.setRole("USER");
        
        assertEquals("USER", user.getRole());
    }

    @Test
    void testAdminRole() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("adminpass");
        user.setRole("ADMIN");
        
        assertEquals("ADMIN", user.getRole());
    }
}

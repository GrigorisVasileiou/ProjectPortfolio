package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    private UserRepository userRepository;
    private CustomUserDetailsService service;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        service = new CustomUserDetailsService(userRepository);
    }

    @Test
    void shouldLoadExistingUser() {
        User user = new User();
        user.setUsername("george");
        user.setPassword("1234");

        when(userRepository.findByUsername("george")).thenReturn(user);

        UserDetails result = service.loadUserByUsername("george");

        assertEquals("george", result.getUsername());
        assertEquals("1234", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("unknown")
        );
    }
}
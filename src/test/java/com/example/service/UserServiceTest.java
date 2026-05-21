package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    private final UserService userService = new UserService(userRepository, passwordEncoder);

    @Test
    void testRegisterUser() {
        when(passwordEncoder.encode("1234")).thenReturn("encoded1234");
        userService.registerUser("stelios", "test@mail.com", "1234");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("stelios");

        when(userRepository.findByUsername("stelios")).thenReturn(user);
        User result = userService.findByUsername("stelios");
        assertNotNull(result);
        assertEquals("stelios", result.getUsername());
    }

    @Test
    void testSaveRawPassword() {
        User user = new User();

        when(passwordEncoder.encode("1234")).thenReturn("encoded1234");
        userService.saveRawPassword(user, "1234");
        assertEquals("encoded1234", user.getPassword());
        verify(userRepository).save(user);
    }
}
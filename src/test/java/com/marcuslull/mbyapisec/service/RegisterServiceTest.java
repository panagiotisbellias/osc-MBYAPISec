package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.exception.InvalidRegistrationFormatException;
import com.marcuslull.mbyapisec.exception.UsernameAlreadyExistsException;
import com.marcuslull.mbyapisec.model.entity.User;
import com.marcuslull.mbyapisec.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterService registerService;

    @Test
    void registerSuccess() {
        // Arrange
        Map<String, String> registrationInfo = new HashMap<>();
        registrationInfo.put("email", "email");
        registrationInfo.put("password", "password");
        registrationInfo.put("role", "role");
        User user = new User();
        when(userRepository.findUserByEmail("email")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        lenient().when(userRepository.save(user)).thenReturn(user);

        // Act
        registerService.register(registrationInfo);

        // Assert
        verify(userRepository, times(1)).findUserByEmail("email");
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void registerMissingInfo() {
        // Arrange
        Map<String, String> registrationInfo = new HashMap<>();
        registrationInfo.put("email", "email");
        registrationInfo.put("password", "password");

        // Act & Assert
        assertThrows(InvalidRegistrationFormatException.class, () -> registerService.register(registrationInfo));
    }

    @Test
    void registerUserExists() {
        // Arrange
        Map<String, String> registrationInfo = new HashMap<>();
        registrationInfo.put("email", "email");
        registrationInfo.put("password", "password");
        registrationInfo.put("role", "role");
        User user = new User();
        when(userRepository.findUserByEmail("email")).thenReturn(user);

        // Act & Assert
        assertThrows(UsernameAlreadyExistsException.class, () -> registerService.register(registrationInfo));
    }
}
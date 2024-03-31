package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.entity.User;
import com.marcuslull.mbyapisec.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void loadUserByUsernameSuccess() {
        // arrange
        when(userRepository.findUserByEmail("name")).thenReturn(new User());

        // act
        userService.loadUserByUsername("name");

        // assert
        verify(userRepository, atLeastOnce()).findUserByEmail("name");
    }

    @Test
    void loadUserByUsernameNull() {
        // arrange
        when(userRepository.findUserByEmail("name")).thenReturn(null);

        // act
        User user = (User) userService.loadUserByUsername("name");

        // assert
        assertNull(user);
    }
}
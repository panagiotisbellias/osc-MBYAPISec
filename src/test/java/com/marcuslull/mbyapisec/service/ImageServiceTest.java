package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.entity.User;
import com.marcuslull.mbyapisec.repository.ImageRepository;
import com.marcuslull.mbyapisec.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private CustomAuthenticationProviderService customAuthenticationProviderService;

    @InjectMocks
    private ImageService imageService;

    @Test
    void getAllForEntitySuccess() {
        // arrange
        User user = new User();
        user.setId(1L);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(userRepository.findUserByEmail("owner")).thenReturn(user);
        when(imageRepository.findImagesByOwnerIdAndYardId(1L, 1L)).thenReturn(new ArrayList<>());

        // act
        imageService.getAllImageForEntity("yard", 1L);

        // assert
        verify(imageRepository, atLeastOnce()).findImagesByOwnerIdAndYardId(anyLong(), anyLong());
    }

    @Test
    void getAllForEntityFail() {
        // arrange
        User user = new User();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(userRepository.findUserByEmail("owner")).thenReturn(user);

        // act & assert
        assertThrows(RuntimeException.class, () -> imageService.getAllImageForEntity("invalid", 1L));
    }
}
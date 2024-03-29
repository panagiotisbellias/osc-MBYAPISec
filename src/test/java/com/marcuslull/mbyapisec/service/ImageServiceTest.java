package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.ImageDto;
import com.marcuslull.mbyapisec.model.entity.Image;
import com.marcuslull.mbyapisec.model.entity.User;
import com.marcuslull.mbyapisec.model.record.StorageProperties;
import com.marcuslull.mbyapisec.repository.ImageRepository;
import com.marcuslull.mbyapisec.repository.UserRepository;
import com.marcuslull.mbyapisec.repository.YardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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
    @Mock
    private StorageService storageService;
    @Mock
    private YardRepository yardRepository;
    @Mock
    private StorageProperties storageProperties;
    @Mock
    private MapperService mapperService;

    @InjectMocks
    private ImageService imageService;

    @Test
    void getImageDataForEntitySuccess() {
        // arrange
        User user = new User();
        user.setId(1L);
        Image image = new Image();
        ImageDto imageDto = new ImageDto();
        List<ImageDto> imageDtoList = List.of(imageDto);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(userRepository.findUserByEmail("owner")).thenReturn(user);
        when(imageRepository.findImagesByOwnerIdAndYardId(1L, 1L)).thenReturn(List.of(image));
        when(mapperService.map(image)).thenReturn(imageDto);

        // act
        List<ImageDto> result = imageService.getImageDataForEntity("yard", 1L);

        // assert
        verify(userRepository, atLeastOnce()).findUserByEmail("owner");
        verify(imageRepository, atLeastOnce()).findImagesByOwnerIdAndYardId(anyLong(), anyLong());
        verify(mapperService, atLeastOnce()).map(image);
        assertEquals(result.size(), imageDtoList.size());
    }

    @Test
    void getImageDataForEntityFailInvalid() {
        // arrange & act & assert
        assertThrows(RuntimeException.class, () -> imageService.getImageDataForEntity("invalid", 1L));
    }

    @Test
    void getImageDataForEntityNull() {
        // arrange
        User user = new User();
        user.setId(1L);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(userRepository.findUserByEmail("owner")).thenReturn(user);
        when(imageRepository.findImagesByOwnerIdAndYardId(1L, 1L)).thenReturn(List.of());

        // act
        List<ImageDto> result = imageService.getImageDataForEntity("yard", 1L);

        // assert
        assert(result.isEmpty());
    }
}
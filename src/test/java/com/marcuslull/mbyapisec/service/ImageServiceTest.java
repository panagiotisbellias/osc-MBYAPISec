package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.ImageDto;
import com.marcuslull.mbyapisec.model.entity.Image;
import com.marcuslull.mbyapisec.model.entity.User;
import com.marcuslull.mbyapisec.model.entity.Yard;
import com.marcuslull.mbyapisec.model.record.StorageProperties;
import com.marcuslull.mbyapisec.repository.ImageRepository;
import com.marcuslull.mbyapisec.repository.UserRepository;
import com.marcuslull.mbyapisec.repository.YardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    Resource resource;
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
    void postImageSuccess() throws IOException {
        // arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("email");
        Long entityId = 1L;
        Yard yard = new Yard();
        yard.setId(1L);
        Image image = new Image();
        image.setYard(yard);
        image.setFileName("test.txt");
        image.setFileSize(1L);
        image.setOwnerId(1L);
        image.setPath("/images/images/location/1/test.txt");
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                "text/plain", "This is a test file".getBytes());
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(userRepository.findUserByEmail("owner")).thenReturn(user);
        doNothing().when(storageService).store(user.getId(), file);
        when(yardRepository.findYardByIdAndUserEmail(entityId, user.getEmail())).thenReturn(yard);
        when(storageProperties.location()).thenReturn("location");
        lenient().when(imageRepository.save(image)).thenReturn(image);

        // act
        imageService.postImage("yard", 1L, file);

        // assert
        verify(storageService, atLeastOnce()).store(user.getId(), file);
        verify(imageRepository, atLeastOnce()).save(any());
    }

    @Test
    void postImageFailInvalidEntity() {
        // arrange
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                "text/plain", "This is a test file".getBytes());

        // act & assert
        assertThrows(RuntimeException.class, () -> imageService.postImage("invalid", 1L, file));
    }

    @Test
    void postImageFailNoUser() {
        // arrange
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                "text/plain", "This is a test file".getBytes());
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(userRepository.findUserByEmail("owner")).thenReturn(null);

        // act & assert
        assertThrows(RuntimeException.class, () -> imageService.postImage("yard", 1L, file));
    }


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
        assert (result.isEmpty());
    }

    @Test
    void getImageSuccess() throws MalformedURLException {
        // arrange
        User user = new User();
        user.setId(1L);
        Image image = new Image();
        image.setOwnerId(1L);
        image.setFileName("imageName");
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(userRepository.findUserByEmail("owner")).thenReturn(user);
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));
        when(storageService.retrieve(image.getFileName(), user.getId())).thenReturn(resource);

        // act
        imageService.getImage(1L);

        // assert
        verify(storageService, atLeastOnce()).retrieve(image.getFileName(), user.getId());
    }

    @Test
    void getImageFailNoImage() {
        // arrange
        User user = new User();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(userRepository.findUserByEmail("owner")).thenReturn(user);
        when(imageRepository.findById(1L)).thenReturn(null);

        // act & assert
        assertThrows(RuntimeException.class, () -> imageService.getImage(1L));
    }

    @Test
    void getImageFailSecurity() {
        // arrange
        User user = new User();
        user.setId(1L);
        Image image = new Image();
        image.setOwnerId(2L);
        image.setFileName("imageName");
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(userRepository.findUserByEmail("owner")).thenReturn(user);
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));

        // act & assert
        assertThrows(SecurityException.class, () -> imageService.getImage(1L));
    }
}
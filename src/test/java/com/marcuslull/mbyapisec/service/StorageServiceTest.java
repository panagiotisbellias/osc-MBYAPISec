package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.record.StorageProperties;
import com.marcuslull.mbyapisec.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {
    private static MockedStatic<Files> mockedStatic;
    @Mock
    private CustomAuthenticationProviderService customAuthenticationProviderService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Path rootLocation;

    @BeforeAll
    static void beforeAll() {
        mockedStatic = mockStatic(Files.class);
    }

    @AfterAll
    static void tearDown() {
        if (!mockedStatic.isClosed()) {
            mockedStatic.close();
        }
    }

    @Test
    void testStoreIsSuccessful() throws IOException {

        StorageProperties storageProperties = Mockito.mock(StorageProperties.class);
        Mockito.when(storageProperties.location()).thenReturn("location");
        StorageService storageService = new StorageService(storageProperties);

        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        InputStream inputStream = Mockito.mock(InputStream.class);

        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("original-filename.txt");
        Mockito.when(multipartFile.getInputStream()).thenReturn(inputStream);
        storageService.store(0L, multipartFile);

        Mockito.verify(multipartFile).getOriginalFilename();
        Mockito.verify(multipartFile).getInputStream();

    }

    @Test
    void testStoreFileExists() throws IOException {

        StorageProperties storageProperties = Mockito.mock(StorageProperties.class);
        Mockito.when(storageProperties.location()).thenReturn("location");
        StorageService storageService = new StorageService(storageProperties);

        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        InputStream inputStream = Mockito.mock(InputStream.class);

        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("original-filename.txt");
        Mockito.when(multipartFile.getInputStream()).thenReturn(inputStream);
        storageService.store(0L, multipartFile);

        Mockito.verify(multipartFile).getOriginalFilename();
        Mockito.verify(multipartFile).getInputStream();

    }

    @Test
    void testRetrieveIsSuccessful() throws MalformedURLException {

        StorageProperties storageProperties = Mockito.mock(StorageProperties.class);
        Mockito.when(storageProperties.location()).thenReturn("location");
        StorageService storageService = new StorageService(storageProperties);

        Resource resource = storageService.retrieve("original-filename.txt", 0L);

    }

    @Test
    void testDeleteImage() throws IOException {

        StorageProperties storageProperties = Mockito.mock(StorageProperties.class);
        Mockito.when(storageProperties.location()).thenReturn("location");

        StorageService storageService = new StorageService(storageProperties);
        storageService.deleteImage("image path");

    }

}
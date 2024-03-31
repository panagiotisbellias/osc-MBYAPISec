package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.record.StorageProperties;
import com.marcuslull.mbyapisec.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

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
    @Mock
    private StorageProperties storageProperties;
    @Mock
    private MultipartFile multipartFile;
    @InjectMocks
    private StorageService storageService;

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

    //    @Test
    void storeSuccess() {
        // arrange

        // act

        // assert
        // TODO: Implement this test - There are so many Static methods and runtime pathing - Tough one!
    }

    //    @Test
    void storeFailFileSystem() {
        // arrange

        // act

        // assert
        // TODO: Implement this test - There are so many Static methods and runtime pathing - Tough one!
    }

    //    @Test
    void storeFailInvalidFile() {
        // arrange

        // act

        // assert
        // TODO: Implement this test - There are so many Static methods and runtime pathing - Tough one!
    }
}
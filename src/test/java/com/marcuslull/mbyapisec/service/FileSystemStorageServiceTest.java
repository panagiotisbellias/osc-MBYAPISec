package com.marcuslull.mbyapisec.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileSystemStorageServiceTest {

    @InjectMocks
    private FileSystemStorageService fileSystemStorageService;

    @Test
    void storeSuccess() {
        // arrange

        // act

        // assert
    }

    @Test
    void storeFailFileSystem() {
        // arrange

        // act

        // assert
    }

    @Test
    void storeFailInvalidFile() {
        // arrange

        // act

        // assert
    }
}
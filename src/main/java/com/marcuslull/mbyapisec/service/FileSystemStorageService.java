package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.entity.User;
import com.marcuslull.mbyapisec.model.record.StorageProperties;
import com.marcuslull.mbyapisec.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Objects;

@Service
public class FileSystemStorageService {
    private final CustomAuthenticationProviderService customAuthenticationProviderService;
    private final UserRepository userRepository;
    private final Path rootLocation;

    public FileSystemStorageService(
            StorageProperties storageProperties,
            CustomAuthenticationProviderService customAuthenticationProviderService,
            UserRepository userRepository)
    {
        this.customAuthenticationProviderService = customAuthenticationProviderService;
        this.userRepository = userRepository;

        if (!storageProperties.location().trim().isEmpty()) {
            this.rootLocation = Paths.get(storageProperties.location()); // TODO: handle this
        } else throw new RuntimeException("File cannot be empty"); // TODO: handle this
    }

    public void store(MultipartFile multipartFile) throws IOException { // TODO: handle this
        Path destinationUserPath = Paths.get(Objects.requireNonNull(setUserPath())); // TODO: handle this
        // add the root location to the user folder to the original file name
        Path destinationFile = this.rootLocation
                .resolve(destinationUserPath)
                .resolve(Paths.get(Objects.requireNonNull(multipartFile.getOriginalFilename()))) // TODO: handle this
                .normalize()
                .toAbsolutePath();
        InputStream inputStream = multipartFile.getInputStream();
        if (!Files.exists(this.rootLocation.resolve(destinationUserPath))) { // TODO: handle this
            Files.createDirectories(this.rootLocation.resolve(destinationUserPath)); // TODO: handle this
        }
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING); // TODO: handle this
    }

    private String setUserPath() {
        User user = userRepository.findUserByEmail(customAuthenticationProviderService.getAuthenticatedName());
        if (user != null) {
            return user.getId().toString() + "/";
        } else throw new RuntimeException("FileSystemStorageService:setUserPath says - User is not in the DB");
    }
}
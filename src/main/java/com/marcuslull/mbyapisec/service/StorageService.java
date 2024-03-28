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
public class StorageService {
    private final CustomAuthenticationProviderService customAuthenticationProviderService;
    private final UserRepository userRepository;
    private final Path rootLocation;

    public StorageService(
            StorageProperties storageProperties,
            CustomAuthenticationProviderService customAuthenticationProviderService,
            UserRepository userRepository)
    {
        this.customAuthenticationProviderService = customAuthenticationProviderService;
        this.userRepository = userRepository;

        if (!storageProperties.location().trim().isEmpty()) {
            this.rootLocation = Paths.get(storageProperties.location());
        } else throw new RuntimeException("StorageService:Constructor says - File cannot be empty");
    }

    public void store(MultipartFile multipartFile) throws IOException {
        Path destinationUserPath = Paths.get(Objects.requireNonNull(setUserPath()));
        // add the root location to the user folder to the original file name
        Path destinationFile = this.rootLocation
                .resolve(destinationUserPath)
                .resolve(Paths.get(Objects.requireNonNull(multipartFile.getOriginalFilename())))
                .normalize()
                .toAbsolutePath();
        InputStream inputStream = multipartFile.getInputStream();
        if (!Files.exists(this.rootLocation.resolve(destinationUserPath))) {
            Files.createDirectories(this.rootLocation.resolve(destinationUserPath));
        }
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
    }

    private String setUserPath() {
        User user = userRepository.findUserByEmail(customAuthenticationProviderService.getAuthenticatedName());
        if (user != null) {
            return user.getId().toString() + "/";
        } else throw new RuntimeException("FileSystemStorageService:setUserPath says - User is not in the DB");
    }
}
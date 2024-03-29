package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.record.StorageProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class StorageService {
    private final Path rootLocation;

    public StorageService(StorageProperties storageProperties) {
        if (!storageProperties.location().trim().isEmpty()) {
            this.rootLocation = Paths.get(storageProperties.location());
        } else throw new RuntimeException("StorageService:Constructor says - File cannot be empty");
    }

    public void store(Long userId, MultipartFile multipartFile) throws IOException {
        Path destinationUserPath = Paths.get(userId.toString() + "/");
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

    public Resource retrieve(String fileName, Long ownerId) throws MalformedURLException {
        Path userPath = Paths.get(ownerId.toString() + "/");
        Path name = Paths.get(fileName);
        Path file = this.rootLocation.resolve(userPath).resolve(name).normalize().toAbsolutePath();
        if (Files.exists(file)) {
            Resource resource = new UrlResource(file.toUri());
            if (resource.isReadable()) {
                return resource;
            } else throw new RuntimeException("StorageService:retrieve says - file is corrupt: " + file);
        } else throw new RuntimeException("StorageService:retrieve says - file does not exist: " + file);
    }
}
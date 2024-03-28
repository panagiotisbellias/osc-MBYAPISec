package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.ImageDto;
import com.marcuslull.mbyapisec.model.entity.Image;
import com.marcuslull.mbyapisec.model.entity.User;
import com.marcuslull.mbyapisec.model.record.StorageProperties;
import com.marcuslull.mbyapisec.repository.ImageRepository;
import com.marcuslull.mbyapisec.repository.UserRepository;
import com.marcuslull.mbyapisec.repository.YardRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final CustomAuthenticationProviderService customAuthenticationProviderService;
    private final StorageService storageService;
    private final YardRepository yardRepository;
    private final StorageProperties storageProperties;

    public ImageService(UserRepository userRepository, ImageRepository imageRepository, CustomAuthenticationProviderService customAuthenticationProviderService, StorageService storageService,
                        YardRepository yardRepository, StorageProperties storageProperties) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.customAuthenticationProviderService = customAuthenticationProviderService;
        this.storageService = storageService;
        this.yardRepository = yardRepository;
        this.storageProperties = storageProperties;
    }

    public void postImage(String entity, Long entityId, MultipartFile multipartFile) throws IOException {
        switch (entity.toLowerCase()) {
            case "yard" -> postImageFileForYard(entityId, multipartFile);
            case "plant", "animal" -> new ImageDto();
            default -> throw new RuntimeException("ImageService:postImage says - entity is not valid");
        }
    }

    private void postImageFileForYard(Long entityId, MultipartFile multipartFile) throws IOException {
        User user = userRepository.findUserByEmail(customAuthenticationProviderService.getAuthenticatedName());
        if (user != null) {
            storageService.store(user.getId(), multipartFile);
            Image image = new Image();
            image.setYard(yardRepository.findYardByIdAndUserEmail(entityId, user.getEmail()));
            image.setFileName(multipartFile.getOriginalFilename());
            image.setFileSize(multipartFile.getSize());
            image.setOwnerId(user.getId());
            image.setPath(storageProperties.location() + user.getId() + "/" + multipartFile.getOriginalFilename());
            imageRepository.save(image);
        } else throw new RuntimeException("StorageService:postImageForEntity says - User is not in the DB");
    }

    public List<Resource> getAllImageForEntity(String entity, Long entityId) {
        User user = userRepository.findUserByEmail(customAuthenticationProviderService.getAuthenticatedName());
        if (user != null) {
            return switch (entity.toLowerCase()) {
                case "yard" -> getImageFilesForYard(entityId, user.getId());
                case "plant", "animal" -> new ArrayList<>();
                default -> throw new RuntimeException("StorageService:getAllForEntity says - entity is not valid");
            };
        } else throw new RuntimeException("StorageService:getAllForEntity says - User is not in the DB");
    }

    private List<Resource> getImageFilesForYard(Long entityId, Long ownerId) {
        List<Image> imageList = imageRepository.findImagesByOwnerIdAndYardId(ownerId, entityId);
        return imageList.stream().map(image -> {
            try {
                return storageService.retrieve(image.getFileName(), ownerId);
            } catch (MalformedURLException e) {
                throw new RuntimeException("ImageService:getImageFilesForYard says - Malformed URL");
            }
        }).collect(Collectors.toList());
    }
}

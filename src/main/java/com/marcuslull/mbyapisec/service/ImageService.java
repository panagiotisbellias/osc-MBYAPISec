package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.exception.ImageQuotaExceededException;
import com.marcuslull.mbyapisec.model.dto.ImageDto;
import com.marcuslull.mbyapisec.model.entity.Image;
import com.marcuslull.mbyapisec.model.entity.User;
import com.marcuslull.mbyapisec.model.record.StorageProperties;
import com.marcuslull.mbyapisec.repository.ImageRepository;
import com.marcuslull.mbyapisec.repository.UserRepository;
import com.marcuslull.mbyapisec.repository.YardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class ImageService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final CustomAuthenticationProviderService customAuthenticationProviderService;
    private final StorageService storageService;
    private final YardRepository yardRepository;
    private final StorageProperties storageProperties;
    private final MapperService mapperService;

    private final Integer IMAGE_UPLOAD_QUOTA = 20;

    public ImageService(UserRepository userRepository,
                        ImageRepository imageRepository,
                        CustomAuthenticationProviderService customAuthenticationProviderService,
                        StorageService storageService,
                        YardRepository yardRepository,
                        StorageProperties storageProperties,
                        MapperService mapperService) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.customAuthenticationProviderService = customAuthenticationProviderService;
        this.storageService = storageService;
        this.yardRepository = yardRepository;
        this.storageProperties = storageProperties;
        this.mapperService = mapperService;
    }

    public void postImage(String entity, Long entityId, MultipartFile multipartFile) throws IOException {
        if (userOverQuota()) {
            throw new ImageQuotaExceededException("Your total image upload count has reached the maximum allowed");
        } else {
            switch (entity.toLowerCase()) {
                case "yard" -> postImageFileForYard(entityId, multipartFile);
                case "plant", "animal" -> new ImageDto();
                default -> throw new EntityNotFoundException("ImageService:postImage says - entity is not valid");
            }
        }
    }

    private boolean userOverQuota() {
        return imageRepository.countByOwnerId(userRepository.findUserByEmail(customAuthenticationProviderService
                .getAuthenticatedName()).getId()) >= IMAGE_UPLOAD_QUOTA;
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
            imageRepository.save(image); // TODO: This should return to the controller
        } else throw new NoSuchElementException("StorageService:postImageForEntity says - User is not in the DB");
    }

    public List<ImageDto> getImageDataForEntity(String entity, Long entityId) {
        return switch (entity.toLowerCase()) {
            case "yard" -> getImageDataForYard(entityId);
            case "plant", "animal" -> new ArrayList<>();
            default -> throw new EntityNotFoundException("StorageService:getAllForEntity says - entity is not valid");
        };
    }

    private List<ImageDto> getImageDataForYard(Long yardId) {
        User user = userRepository.findUserByEmail(customAuthenticationProviderService.getAuthenticatedName());
        List<Image> imageList = imageRepository.findImagesByOwnerIdAndYardId(user.getId(), yardId);
        if (imageList != null) {
            return imageList.stream().map(image -> ((ImageDto) mapperService.map(image))).toList();
        } else return new ArrayList<>();
    }

    public Resource getImage(Long imageId) throws MalformedURLException {
        User user = userRepository.findUserByEmail(customAuthenticationProviderService.getAuthenticatedName());
        Image image = imageRepository.findById(imageId).orElseThrow(() -> new NoSuchElementException("No image found"));
        if (user.getId().equals(image.getOwnerId())) {
            return storageService.retrieve(image.getFileName(), image.getOwnerId());
        } else throw new SecurityException("user and image owner dont match up");
    }

    @Transactional
    public void deleteImage(Long imageId) throws IOException {
        User user = userRepository.findUserByEmail(customAuthenticationProviderService.getAuthenticatedName());
        Image image = imageRepository.findById(imageId).orElseThrow();
        if (Objects.equals(image.getOwnerId(), user.getId())) {
            imageRepository.deleteImageByIdAndOwnerId(imageId, user.getId());
            storageService.deleteImage(image.getPath());
        }
    }
}

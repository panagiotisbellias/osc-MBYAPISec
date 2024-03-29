package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.model.dto.ImageDto;
import com.marcuslull.mbyapisec.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/api/{entity}/{entityId}/images")
    public ResponseEntity<List<ImageDto>> getAllImageData(@PathVariable String entity, @PathVariable Long entityId) {
        List<ImageDto> images = imageService.getImageDataForEntity(entity, entityId);
        return ResponseEntity.ok().body(images);
    }

    @GetMapping("/api/image/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable Long imageId) throws MalformedURLException {
        Resource resource = imageService.getImage(imageId);
        return ResponseEntity.ok().body(resource);
    }

    @PostMapping("/api/{entity}/{id}/images")
    public ResponseEntity<String> postUpload(
            @PathVariable String entity,
            @PathVariable Long id,
            @RequestPart("file") MultipartFile multipartFile) throws IOException {
        imageService.postImage(entity, id, multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

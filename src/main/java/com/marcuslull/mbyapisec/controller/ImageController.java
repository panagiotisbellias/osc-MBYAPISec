package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/api/{entity}/{id}/images")
    public ResponseEntity<Resource> getAllImagesForEntity(@PathVariable String entity, @PathVariable Long id) {
        List<Resource> images = imageService.getAllImageForEntity(entity, id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + images.getFirst().getFilename()).body(images.getFirst());
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

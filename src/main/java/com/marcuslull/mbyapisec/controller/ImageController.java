package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ImageController {

    private final StorageService storageService;

    public ImageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/api/uploads")
    public ResponseEntity<String> postUpload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        storageService.store(multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

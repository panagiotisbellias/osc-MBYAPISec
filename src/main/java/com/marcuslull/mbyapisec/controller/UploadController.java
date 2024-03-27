package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.service.FileSystemStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UploadController {

    private final FileSystemStorageService fileSystemStorageService;

    public UploadController(FileSystemStorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }

    @PostMapping("/api/uploads")
    public ResponseEntity<String> postUpload(@RequestPart("file") MultipartFile multipartFile) throws IOException { // TODO: handle this
        fileSystemStorageService.store(multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

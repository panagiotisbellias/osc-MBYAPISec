package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.service.FileSystemStorageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UploadController {

    private final FileSystemStorageService fileSystemStorageService;

    public UploadController(FileSystemStorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }

    @PostMapping("/api/uploads")
    public void postUpload(@RequestPart("file") MultipartFile multipartFile) {
        fileSystemStorageService.store(multipartFile);
    }
}

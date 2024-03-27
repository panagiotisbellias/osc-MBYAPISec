package com.marcuslull.mbyapisec.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileSystemStorageService {
    private List<MultipartFile> fileList = new ArrayList<>();

    public void store(MultipartFile multipartFile) {
        fileList.add(multipartFile);
    }
}

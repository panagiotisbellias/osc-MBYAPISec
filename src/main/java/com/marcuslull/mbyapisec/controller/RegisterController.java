package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {
    // All NULLs are thrown to GlobalExceptionHandler at the service layer
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> registrationInfo) {
        // This is disabled until I get some email verification
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        registerService.register(registrationInfo);
//        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

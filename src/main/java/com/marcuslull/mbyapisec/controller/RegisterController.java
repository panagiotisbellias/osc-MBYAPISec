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
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> registrationInfo) { // TODO: Exception possibility HttpMessageNotReadableException MethodArgumentTypeMismatchException
        // This is disabled until I get some email verification
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        if (registerService.register(registrationInfo)) { // TODO: Exception possibility null
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
    }
}

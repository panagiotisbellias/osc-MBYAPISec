package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    // All NULLs are thrown to GlobalExceptionHandler at the service layer
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        // HttpMessageNotReadableException MethodArgumentTypeMismatchException caught in GlobalExceptionHandler
        return tokenService.generateToken(authentication);
    }
}

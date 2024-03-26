package com.marcuslull.mbyapisec.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TokenService tokenService;

    @Test
    void generateToken() {
        // Arrange
        String expectedToken = "token";
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        when(authentication.getAuthorities()).thenReturn((Collection) List.of(authority));
        when(authentication.getName()).thenReturn("user");
        when(jwtEncoder.encode(org.mockito.ArgumentMatchers.any())).thenReturn(Jwt.withTokenValue(expectedToken)
                .header("header", "value")
                .claim("claim", "value")
                .build()
        );

        // Act
        String actualToken = tokenService.generateToken(authentication);

        // Assert
        assertEquals(expectedToken, actualToken);
    }
}
package com.marcuslull.mbyapisec.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationProviderServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @Mock
    private GrantedAuthority grantedAuthority;

    @Mock
    private Object object;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private CustomAuthenticationProviderService customAuthenticationProviderService;

    @BeforeAll
    static void setUp() {
        mockStatic(SecurityContextHolder.class);
    }

    @Test
    void authenticateSuccess() {
        // Arrange
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken("name","password", List.of(grantedAuthority));
        UserDetails userDetails = new User("name", "password", List.of(grantedAuthority));
        when(authentication.getName()).thenReturn("name");
        when(authentication.getCredentials()).thenReturn(object);
        when(object.toString()).thenReturn("password");
        when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        // Act
        Authentication authenticationResult = customAuthenticationProviderService.authenticate(authentication);

        // Assert
        assertEquals(usernamePasswordAuthenticationToken, authenticationResult);
    }

    @Test
    void authenticateFailureEmail() {
        // Arrange
        when(authentication.getName()).thenReturn("name");
        when(authentication.getCredentials()).thenReturn(object);
        when(object.toString()).thenReturn("password");
        when(userService.loadUserByUsername(anyString())).thenReturn(null);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> customAuthenticationProviderService.authenticate(authentication));
    }

    @Test
    void authenticateFailurePassword() {
        // Arrange
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken("name","password", List.of(grantedAuthority));
        UserDetails userDetails = new User("name", "password", List.of(grantedAuthority));
        when(authentication.getName()).thenReturn("name");
        when(authentication.getCredentials()).thenReturn(object);
        when(object.toString()).thenReturn("password");
        when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> customAuthenticationProviderService.authenticate(authentication));
    }

    @Test
    void getAuthenticatedNameSuccess() {
        // Arrange
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("name");

        // Act
        String result = customAuthenticationProviderService.getAuthenticatedName();

        // Assert
        assertEquals("name", result);
    }

    @Test
    void getAuthenticatedNameFailure() {
        // Arrange
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> customAuthenticationProviderService.getAuthenticatedName());
    }
}
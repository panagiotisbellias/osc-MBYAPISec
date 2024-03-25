package com.marcuslull.mbyapisec.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProviderService implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProviderService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException { // AuthenticationException caught in the GlobalExceptionHandler
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userService.loadUserByUsername(email); // UsernameNotFoundException caught in the GlobalExceptionHandler
        if (userDetails != null) {
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                return new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());
            } else
                throw new BadCredentialsException("Invalid Password!"); // BadCredentialsException caught in the GlobalExceptionHandler
        } else
            throw new BadCredentialsException("Invalid email!"); // BadCredentialsException caught in the GlobalExceptionHandler
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication); // NullPointerException caught in the GlobalExceptionHandler
    }

    protected String getAuthenticatedName() {
        Authentication authentication = null; // Let's explicitly start fresh every time
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        } else
            throw new RuntimeException("AnimalService:getAuthenticatedName() says: authentication is null - No authentication info is available");
    }
}

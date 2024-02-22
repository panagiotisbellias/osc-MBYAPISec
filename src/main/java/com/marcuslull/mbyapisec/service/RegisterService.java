package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.entity.User;
import com.marcuslull.mbyapisec.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean register(Map<String, String> registrationInfo) {
        User user = userRepository.findUserByEmail(registrationInfo.get("email"));
        if (user == null) {
            user = new User();
            user.setEmail(registrationInfo.get("email"));
            user.setPassword(passwordEncoder.encode(registrationInfo.get("password")));
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(registrationInfo.get("role"));
            List<GrantedAuthority> grantedAuthorities = List.of(grantedAuthority);
            user.setGrantedAuthority(grantedAuthorities);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}

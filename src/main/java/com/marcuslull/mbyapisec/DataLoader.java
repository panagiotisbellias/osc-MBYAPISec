package com.marcuslull.mbyapisec;

import com.marcuslull.mbyapisec.model.entity.User;
import com.marcuslull.mbyapisec.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // DEMO USERS - THERE IS NOTHING IMPORTANT HERE
        User user = new User();
        user.setEmail("user@email.com");
        user.setPassword("$2a$12$OFa9q8h00epJLgBok.bo8.p5hzG9XCU9DjFEwjzUfCGoZT7OQIZoe");
        user.setEnabled(true);
        user.setGrantedAuthority(List.of(new SimpleGrantedAuthority("USER")));
        userRepository.save(user);

        User admin = new User();
        admin.setEmail("admin@email.com");
        admin.setPassword("$2a$12$HCkrs1z9lygxZH5gNrLOD.YtHT6e/a09MaqBPuw22pVLMVYhlAgmC");
        admin.setEnabled(true);
        admin.setGrantedAuthority(List.of(new SimpleGrantedAuthority("ADMIN")));
        userRepository.save(admin);
    }
}

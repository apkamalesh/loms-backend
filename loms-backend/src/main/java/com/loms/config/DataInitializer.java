package com.loms.config;

import com.loms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        userRepository.findAll().forEach(user -> {
            String pwd = user.getPassword();
            if (pwd != null && pwd.startsWith("{noop}")) {
                user.setPassword(passwordEncoder.encode(pwd.substring(6)));
                userRepository.save(user);
                log.info("Password encoded for: {}", user.getEmail());
            }
        });
    }
}

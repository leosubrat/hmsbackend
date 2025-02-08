package com.hospitalityhub.bootstrapdata;

import com.hospitalityhub.entity.Role;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserBootStrapData implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // For secure password encoding

    @Override
    public void run(String... args) throws Exception {
        Optional<User> user = userRepository.findByEmail("admin@example.com");
        if (user.isEmpty()) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("Admin123@@@"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Admin user created: " + admin.getEmail());
        } else {
            System.out.println("Users already exist, no admin user created.");
        }
    }
}
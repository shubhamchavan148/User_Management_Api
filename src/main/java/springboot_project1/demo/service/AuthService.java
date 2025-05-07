package springboot_project1.demo.service;

import springboot_project1.demo.dto.UserDto;
import springboot_project1.demo.Model.*;
import springboot_project1.demo.repository.UserRepository;
import springboot_project1.demo.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String register(UserDto dto) {
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User(
                null,
                dto.getName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                Role.USER
        );
        userRepo.save(user);
        return jwtService.generateToken(user);
    }

    public String login(String email, String rawPassword) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtService.generateToken(user);
    }
}

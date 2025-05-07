package com.example.demo.service;

import com.example.demo.authentication.JwtUtil;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.EmailAlreadyExists;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String signup(User user) {
        logger.info("signup() method called");
        logger.debug("Received user details: {}", user);
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new EmailAlreadyExists("Email already exists");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(user.getRole() == null ? Role.USER : user.getRole());
            userRepository.save(user);
            return "User registered successfully!";
        } catch (EmailAlreadyExists e) {
            logger.error("EmailAlreadyExists exception: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error during user signup: {}", e.getMessage(), e);
            throw new RuntimeException("An error occurred while registering the user.");
        }
    }

    public Map<String, String> login(String email, String password) {
        logger.info("Login method called for email: {}", email);
        logger.debug("Login parameters - email: {}, password: [PROTECTED]", email);
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
                String accessToken = jwtUtil.generateAccessToken(email);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("userId", String.valueOf(user.get().getId()));
                tokens.put("name", String.valueOf(user.get().getFullName()));
                tokens.put("mobileNumber", String.valueOf(user.get().getMobileNumber()));
                return tokens;
            }
            throw new AuthenticationException("Invalid credentials!");
        }catch (Exception e){
            logger.error("Login failed for email: {}", email, e);
            throw e;
        }
    }

    public void logout(String accessToken) {
        try {
            String jwtAccessToken = accessToken.substring(7);
            logger.info("Successfully logged out user with accessToken: {}", jwtAccessToken);
        } catch (Exception e) {
            logger.error("Unexpected error during logout: {}", e.getMessage(), e);
            throw new RuntimeException("An error occurred during logout.");
        }
    }
}


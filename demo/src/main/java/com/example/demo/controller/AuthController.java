package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken) {
        String jwtAccessToken = accessToken.substring(7); // Remove "Bearer " prefix
        authService.logout(jwtAccessToken);
        return ResponseEntity.ok("Logged out successfully!");
    }
}

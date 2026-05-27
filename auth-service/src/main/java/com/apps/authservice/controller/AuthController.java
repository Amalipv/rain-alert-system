package com.apps.authservice.controller;

import com.apps.authservice.dto.*;
import com.apps.authservice.service.AuthService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        System.out.println("Response: " + response); // ← add this debug!
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        System.out.println("Response: " + response); // ← add this debug!
        return ResponseEntity.ok(response);
    }
}
package com.example.cartas.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.cartas.service.AuthService;
import com.example.cartas.dto.AuthResponse;
import com.example.cartas.dto.LoginRequest;
import com.example.cartas.dto.RegisterRequest;
import com.example.cartas.dto.MobileRegisterRequest;
import com.example.cartas.dto.ApiResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // GET /api/auth/status → true si SA déjà enregistré
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Boolean>> status() {
        return ResponseEntity.ok(ApiResponse.ok("ok", authService.isSuperAdminRegistered()));
    }

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Super Admin créé.", authService.register(req)));
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Connexion réussie.", authService.login(req)));
    }

    // POST /api/auth/mobile/register
    @PostMapping(value = "/mobile/register")
    public ResponseEntity<ApiResponse<Void>> registerMobile(
            @Valid MobileRegisterRequest req,
            @RequestParam(value = "document", required = false) MultipartFile document) {
        authService.registerMobile(req, document);
        return ResponseEntity.ok(ApiResponse.ok("Inscription réussie. Vous pouvez maintenant vous connecter.", null));
    }
}

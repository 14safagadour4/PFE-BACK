package com.example.cartas.controller;
import com.example.cartas.service.UserManagementService;
import com.example.cartas.security.JwtService;
import com.example.cartas.dto.ApiResponse;
import com.example.cartas.entity.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService service;
    private final JwtService jwt;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AppUser>>> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.ok("ok", service.getAll(status, page, size)));
    }

    @PatchMapping("/{id}/validate")
    public ResponseEntity<ApiResponse<Void>> validate(
            @PathVariable Long id, HttpServletRequest req) {
        service.validate(id, extractActorId(req), extractActorType(req));
        return ResponseEntity.ok(ApiResponse.ok("Utilisateur validé.", null));
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<ApiResponse<Void>> block(
            @PathVariable Long id, HttpServletRequest req) {
        service.block(id, extractActorId(req), extractActorType(req));
        return ResponseEntity.ok(ApiResponse.ok("Utilisateur bloqué.", null));
    }

    @PatchMapping("/{id}/unblock")
    public ResponseEntity<ApiResponse<Void>> unblock(
            @PathVariable Long id, HttpServletRequest req) {
        service.unblock(id, extractActorId(req), extractActorType(req));
        return ResponseEntity.ok(ApiResponse.ok("Utilisateur réactivé.", null));
    }

    // Retourne Long directement
    private Long extractActorId(HttpServletRequest req) {
        String token = req.getHeader("Authorization").substring(7);
        return jwt.extractActorId(token);
    }

    // Retourne String directement
    private String extractActorType(HttpServletRequest req) {
        String token = req.getHeader("Authorization").substring(7);
        return jwt.extractRole(token);
    }
}
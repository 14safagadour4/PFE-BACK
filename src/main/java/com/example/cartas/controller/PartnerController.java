package com.example.cartas.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.cartas.service.PartnerService;
import com.example.cartas.repository.RoleRepository;
import com.example.cartas.security.JwtService;
import com.example.cartas.dto.ApiResponse;
import com.example.cartas.dto.CreatePartnerRequest;
import com.example.cartas.entity.Partner;
@RestController
@RequestMapping("/api/super-admin/partners")
@PreAuthorize("hasRole('SUPER_ADMIN')")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService service;
    private final RoleRepository roleRepo;
    private final JwtService     jwt;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Partner>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.ok("ok", service.getAll(page, size)));
    }

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<?>> getRoles() {
        return ResponseEntity.ok(ApiResponse.ok("ok", roleRepo.findAll()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Partner>> create(
            @Valid @RequestBody CreatePartnerRequest req,
            HttpServletRequest httpReq) {
        Long actorId = extractActorId(httpReq);
        return ResponseEntity.ok(ApiResponse.ok("Partenaire créé.", service.create(req, actorId)));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse<Void>> toggle(
            @PathVariable Long id, HttpServletRequest httpReq) {
        service.toggleActive(id, extractActorId(httpReq));
        return ResponseEntity.ok(ApiResponse.ok("Statut modifié.", null));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<ApiResponse<Partner>> updateRole(
            @PathVariable Long id,
            @RequestParam Long roleId,
            HttpServletRequest httpReq) {
        return ResponseEntity.ok(ApiResponse.ok("Rôle mis à jour.",
                service.updateRole(id, roleId, extractActorId(httpReq))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id, HttpServletRequest httpReq) {
        service.delete(id, extractActorId(httpReq));
        return ResponseEntity.ok(ApiResponse.ok("Partenaire supprimé.", null));
    }

    private Long extractActorId(HttpServletRequest req) {
        String token = req.getHeader("Authorization").substring(7);
        return jwt.extractActorId(token);
    }
}

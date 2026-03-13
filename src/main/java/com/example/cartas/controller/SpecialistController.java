package com.example.cartas.controller;
import com.example.cartas.repository.SpecialistRepository;
import com.example.cartas.service.ActivityLogService;
import com.example.cartas.security.JwtService;
import com.example.cartas.dto.ApiResponse;
import com.example.cartas.entity.Specialist;
import com.example.cartas.entity.Specialist.SpecialistStatus;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/specialists")
public class SpecialistController {

    private final SpecialistRepository repo;
    private final ActivityLogService   logService;
    private final JwtService           jwt;

    public SpecialistController(SpecialistRepository repo, ActivityLogService logService, JwtService jwt) {
        this.repo = repo;
        this.logService = logService;
        this.jwt = jwt;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<Specialist>>> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Specialist> result = status != null
                ? repo.findByStatus(SpecialistStatus.valueOf(status), pageable)
                : repo.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.ok("ok", result));
    }

    @PatchMapping("/{id}/validate")
    public ResponseEntity<ApiResponse<Void>> validate(
            @PathVariable Long id, HttpServletRequest req) {
        var s = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Spécialiste introuvable."));
        s.setStatus(SpecialistStatus.ACTIVE);
        s.setValidatedAt(LocalDateTime.now());
        repo.save(s);
        String token = req.getHeader("Authorization").substring(7);
        logService.log(jwt.extractActorId(token), jwt.extractRole(token),
                "SPECIALIST_VALIDATED", "Specialist", id, null, null);
        return ResponseEntity.ok(ApiResponse.ok("Spécialiste validé.", null));
    }
}

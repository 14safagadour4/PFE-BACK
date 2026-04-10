package com.example.cartas.controller;

import com.example.cartas.repository.ArtTherapistRepository;
import com.example.cartas.service.ActivityLogService;
import com.example.cartas.security.JwtService;
import com.example.cartas.dto.ApiResponse;
import com.example.cartas.dto.RefusalRequest;
import com.example.cartas.entity.ArtTherapist;
import com.example.cartas.entity.ArtTherapist.TherapistStatus;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/art-therapists")
public class ArtTherapistController {

    private final ArtTherapistRepository repo;
    private final ActivityLogService logService;
    private final JwtService jwt;

    public ArtTherapistController(ArtTherapistRepository repo, ActivityLogService logService, JwtService jwt) {
        this.repo = repo;
        this.logService = logService;
        this.jwt = jwt;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ArtTherapist>>> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ArtTherapist> result = (status != null && !status.isEmpty())
                ? repo.findByStatus(TherapistStatus.valueOf(status), pageable)
                : repo.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.ok("ok", result));
    }

    @PatchMapping("/{id}/validate")
    public ResponseEntity<ApiResponse<Void>> validate(
            @PathVariable Long id, HttpServletRequest req) {
        var therapist = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Art-Thérapeute introuvable."));
        
        therapist.setStatus(TherapistStatus.ACTIVE);
        therapist.setValidatedAt(LocalDateTime.now());
        repo.save(therapist);

        String token = req.getHeader("Authorization").substring(7);
        logService.log(jwt.extractActorId(token), jwt.extractRole(token),
                "ART_THERAPIST_VALIDATED", "ArtTherapist", id, null, null);

        return ResponseEntity.ok(ApiResponse.ok("Art-Thérapeute validé.", null));
    }

    @PatchMapping("/{id}/refuse")
    public ResponseEntity<ApiResponse<Void>> refuse(
            @PathVariable Long id, @RequestBody RefusalRequest req, HttpServletRequest servletReq) {
        var therapist = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Art-Thérapeute introuvable."));
        
        therapist.setStatus(TherapistStatus.REFUSED);
        therapist.setRefusalReason(req.getReason());
        repo.save(therapist);

        String authHeader = servletReq.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            logService.log(jwt.extractActorId(token), jwt.extractRole(token),
                    "ART_THERAPIST_REFUSED", "ArtTherapist", id, null, req.getReason());
        }

        return ResponseEntity.ok(ApiResponse.ok("Art-Thérapeute refusé.", null));
    }
}

package com.example.cartas.controller;

import com.example.cartas.repository.ActivityLogRepository;
import com.example.cartas.dto.ApiResponse;
import com.example.cartas.entity.ActivityLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;           // ← AJOUTE
import org.springframework.data.domain.PageRequest;  // ← AJOUTE
import org.springframework.data.domain.Pageable;     // ← AJOUTE
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class ActivityLogController {
    private ActivityLogRepository repo;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ActivityLog>>> getLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ActivityLog> logs = repo.findAll(pageable);
        
        return ResponseEntity.ok(ApiResponse.ok("Logs récupérés avec succès", logs));
    }
}
package com.example.cartas.controller;
import com.example.cartas.repository.ActivityLogRepository;
import com.example.cartas.dto.ApiResponse;
import com.example.cartas.entity.ActivityLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class ActivityLogController {

    private final ActivityLogRepository repo;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ActivityLog>>> getLogs(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok("ok",
                repo.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))));
    }
}

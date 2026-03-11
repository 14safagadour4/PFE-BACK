package com.example.cartas.controller;
import com.example.cartas.service.DashboardService;
import com.example.cartas.dto.DashboardStats;
import com.example.cartas.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    // GET /api/dashboard/stats
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStats>> stats() {
        return ResponseEntity.ok(ApiResponse.ok("ok", dashboardService.getStats()));
    }
}

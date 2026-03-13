package com.example.cartas.controller;
import com.example.cartas.service.DashboardService;
import com.example.cartas.dto.DashboardStats;
import com.example.cartas.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    // GET /api/dashboard/stats
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStats>> stats() {
        return ResponseEntity.ok(ApiResponse.ok("ok", dashboardService.getStats()));
    }
}

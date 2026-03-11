package com.example.cartas.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DashboardStats {
    private long totalUsers;
    private long activeUsers;
    private long pendingUsers;
    private long blockedUsers;
    private long newUsersThisWeek;
    private double userGrowthPercent;

    private long totalSpecialists;
    private long pendingSpecialists;

    private long totalTherapists;
    private long pendingTherapists;

    private long totalContent;
    private long publishedContent;

    private long totalPartners;
    private long activePartners;
}
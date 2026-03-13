package com.example.cartas.dto;

import lombok.*;

 @Builder @NoArgsConstructor @AllArgsConstructor
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

    // Getters
    public long getTotalUsers() { return totalUsers; }
    public long getActiveUsers() { return activeUsers; }
    public long getPendingUsers() { return pendingUsers; }
    public long getBlockedUsers() { return blockedUsers; }
    public long getNewUsersThisWeek() { return newUsersThisWeek; }
    public double getUserGrowthPercent() { return userGrowthPercent; }
    public long getTotalSpecialists() { return totalSpecialists; }
    public long getPendingSpecialists() { return pendingSpecialists; }
    public long getTotalTherapists() { return totalTherapists; }
    public long getPendingTherapists() { return pendingTherapists; }
    public long getTotalContent() { return totalContent; }
    public long getPublishedContent() { return publishedContent; }
    public long getTotalPartners() { return totalPartners; }
    public long getActivePartners() { return activePartners; }

    // Setters
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
    public void setActiveUsers(long activeUsers) { this.activeUsers = activeUsers; }
    public void setPendingUsers(long pendingUsers) { this.pendingUsers = pendingUsers; }
    public void setBlockedUsers(long blockedUsers) { this.blockedUsers = blockedUsers; }
    public void setNewUsersThisWeek(long newUsersThisWeek) { this.newUsersThisWeek = newUsersThisWeek; }
    public void setUserGrowthPercent(double userGrowthPercent) { this.userGrowthPercent = userGrowthPercent; }
    public void setTotalSpecialists(long totalSpecialists) { this.totalSpecialists = totalSpecialists; }
    public void setPendingSpecialists(long pendingSpecialists) { this.pendingSpecialists = pendingSpecialists; }
    public void setTotalTherapists(long totalTherapists) { this.totalTherapists = totalTherapists; }
    public void setPendingTherapists(long pendingTherapists) { this.pendingTherapists = pendingTherapists; }
    public void setTotalContent(long totalContent) { this.totalContent = totalContent; }
    public void setPublishedContent(long publishedContent) { this.publishedContent = publishedContent; }
    public void setTotalPartners(long totalPartners) { this.totalPartners = totalPartners; }
    public void setActivePartners(long activePartners) { this.activePartners = activePartners; }
}
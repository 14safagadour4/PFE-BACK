package com.example.cartas.dto;

import lombok.Data;


public class CreatePartnerRequest {

    private String firstName;
    private String lastName;
    private String email;
    private Long roleId;

    private Boolean canViewDashboard;
    private Boolean canManageUsers;
    private Boolean canAddContent;
    private Boolean canEditContent;
    private Boolean canManageSpecialists;
    private Boolean canViewActivityLogs;
    private Boolean canViewStatistics;

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public Long getRoleId() { return roleId; }
    public Boolean getCanViewDashboard() { return canViewDashboard; }
    public Boolean getCanManageUsers() { return canManageUsers; }
    public Boolean getCanAddContent() { return canAddContent; }
    public Boolean getCanEditContent() { return canEditContent; }
    public Boolean getCanManageSpecialists() { return canManageSpecialists; }
    public Boolean getCanViewActivityLogs() { return canViewActivityLogs; }
    public Boolean getCanViewStatistics() { return canViewStatistics; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }
    public void setCanViewDashboard(Boolean canViewDashboard) { this.canViewDashboard = canViewDashboard; }
    public void setCanManageUsers(Boolean canManageUsers) { this.canManageUsers = canManageUsers; }
    public void setCanAddContent(Boolean canAddContent) { this.canAddContent = canAddContent; }
    public void setCanEditContent(Boolean canEditContent) { this.canEditContent = canEditContent; }
    public void setCanManageSpecialists(Boolean canManageSpecialists) { this.canManageSpecialists = canManageSpecialists; }
    public void setCanViewActivityLogs(Boolean canViewActivityLogs) { this.canViewActivityLogs = canViewActivityLogs; }
    public void setCanViewStatistics(Boolean canViewStatistics) { this.canViewStatistics = canViewStatistics; }

    
}

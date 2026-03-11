package com.example.cartas.dto;

import lombok.Data;

@Data
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
}

package com.example.cartas.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partners",
       uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
   @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    private String password;           // null jusqu'à acceptation

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    // Permissions personnalisées (override du rôle)
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean canViewDashboard    = false;

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean canManageUsers      = false;

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean canAddContent       = false;

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean canEditContent      = false;

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean canManageSpecialists = false;

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean canViewActivityLogs = false;

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean canViewStatistics   = false;

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean invitationAccepted = false;

    @Column(length = 64)
    private String invitationToken;    // UUID envoyé par email

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() { createdAt = LocalDateTime.now(); 
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getInvitationAccepted() {
        return this.invitationAccepted;
    }

    public void setInvitationAccepted(Boolean invitationAccepted) {
        this.invitationAccepted = invitationAccepted;
    }
// --- ID ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // --- Nom et Prénom ---
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    // --- Email ---
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // --- Mot de passe ---
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // --- Rôle ---
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    // --- Permissions ---
    public Boolean getCanViewDashboard() { return canViewDashboard; }
    public void setCanViewDashboard(Boolean canViewDashboard) { this.canViewDashboard = canViewDashboard; }

    public Boolean getCanManageUsers() { return canManageUsers; }
    public void setCanManageUsers(Boolean canManageUsers) { this.canManageUsers = canManageUsers; }

    public Boolean getCanAddContent() { return canAddContent; }
    public void setCanAddContent(Boolean canAddContent) { this.canAddContent = canAddContent; }

    public Boolean getCanEditContent() { return canEditContent; }
    public void setCanEditContent(Boolean canEditContent) { this.canEditContent = canEditContent; }

    public Boolean getCanManageSpecialists() { return canManageSpecialists; }
    public void setCanManageSpecialists(Boolean canManageSpecialists) { this.canManageSpecialists = canManageSpecialists; }

    public Boolean getCanViewActivityLogs() { return canViewActivityLogs; }
    public void setCanViewActivityLogs(Boolean canViewActivityLogs) { this.canViewActivityLogs = canViewActivityLogs; }

    public Boolean getCanViewStatistics() { return canViewStatistics; }
    public void setCanViewStatistics(Boolean canViewStatistics) { this.canViewStatistics = canViewStatistics; }

    // --- Token et Dates ---
    public String getInvitationToken() { return invitationToken; }
    public void setInvitationToken(String invitationToken) { this.invitationToken = invitationToken; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

}

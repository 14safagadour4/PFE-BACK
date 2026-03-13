package com.example.cartas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "specialists")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Specialist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 100)
    private String specialty;

    @Column(length = 50)
    private String licenseNumber;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 500)
    private String avatarUrl;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PENDING','ACTIVE','BLOCKED') DEFAULT 'PENDING'")
    private SpecialistStatus status = SpecialistStatus.PENDING;

    public SpecialistStatus getStatus() { return status; }
    public void setStatus(SpecialistStatus status) { this.status = status; }

    private LocalDateTime validatedAt;

    public LocalDateTime getValidatedAt() { return validatedAt; }
    public void setValidatedAt(LocalDateTime validatedAt) { this.validatedAt = validatedAt; }

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() { createdAt = LocalDateTime.now(); }

    public enum SpecialistStatus { PENDING, ACTIVE, BLOCKED }
}


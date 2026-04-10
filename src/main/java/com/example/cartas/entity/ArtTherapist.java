package com.example.cartas.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "art_therapists")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ArtTherapist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String artDiscipline;       // ex: Peinture, Musique, Théâtre...

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 500)
    private String avatarUrl;

    @Column(length = 500)
    private String certificateUrl;

    @Column(name = "refusal_reason", columnDefinition = "TEXT")
    private String refusalReason;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TherapistStatus status = TherapistStatus.PENDING;

    private LocalDateTime validatedAt;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() { createdAt = LocalDateTime.now(); }

    public enum TherapistStatus { PENDING, ACTIVE, BLOCKED, REFUSED }
}

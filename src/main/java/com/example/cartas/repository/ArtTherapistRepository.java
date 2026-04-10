package com.example.cartas.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.cartas.entity.ArtTherapist;
import com.example.cartas.entity.ArtTherapist.TherapistStatus;

public interface ArtTherapistRepository extends JpaRepository<ArtTherapist, Long> {
    Optional<ArtTherapist> findByEmail(String email);
    Page<ArtTherapist> findByStatus(TherapistStatus status, Pageable pageable);
    long countByStatus(TherapistStatus status);
}

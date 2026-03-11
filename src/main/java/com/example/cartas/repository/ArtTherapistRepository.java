package com.example.cartas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.cartas.entity.ArtTherapist;
import com.example.cartas.entity.ArtTherapist.TherapistStatus;

public interface ArtTherapistRepository extends JpaRepository<ArtTherapist, Long> {
    Page<ArtTherapist> findByStatus(TherapistStatus status, Pageable pageable);
    long countByStatus(TherapistStatus status);
}

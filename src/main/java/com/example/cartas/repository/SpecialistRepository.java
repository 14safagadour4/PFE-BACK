package com.example.cartas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cartas.entity.Specialist;
import com.example.cartas.entity.Specialist.SpecialistStatus;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
    Page<Specialist> findByStatus(SpecialistStatus status, Pageable pageable);
    long countByStatus(SpecialistStatus status);
}

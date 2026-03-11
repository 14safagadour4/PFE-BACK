package com.example.cartas.repository;
import com.example.cartas.entity.EducationalContent; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EducationalContentRepository extends JpaRepository<EducationalContent, Long> {
    Page<EducationalContent> findByIsPublished(Boolean isPublished, Pageable pageable);
    long countByIsPublished(Boolean isPublished);
}
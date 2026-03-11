package com.example.cartas.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cartas.entity.Partner;
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Partner> findByInvitationToken(String token);
    Page<Partner> findAllByOrderByCreatedAtDesc(Pageable pageable);
}

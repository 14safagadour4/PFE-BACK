package com.example.cartas.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cartas.entity.AppUser;
import com.example.cartas.entity.AppUser.UserStatus;
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    Page<AppUser> findByStatus(UserStatus status, Pageable pageable);
    long countByStatus(UserStatus status);
    long countByCreatedAtAfter(LocalDateTime date);
}
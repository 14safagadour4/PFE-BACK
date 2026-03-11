package com.example.cartas.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.cartas.entity.SuperAdmin;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
    Optional<SuperAdmin> findByEmail(String email);

    @Query("SELECT COUNT(sa) > 0 FROM SuperAdmin sa")
    boolean isSuperAdminRegistered();
}


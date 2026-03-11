package com.example.cartas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.cartas.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserIdAndUserType(Long userId, String userType);
}

package com.example.cartas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.example.cartas.repository.AppUserRepository;
import com.example.cartas.entity.AppUser;
import com.example.cartas.entity.AppUser.UserStatus;
@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final AppUserRepository repo;
    private final ActivityLogService logService;

    public Page<AppUser> getAll(String status, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        if (status != null && !status.isBlank()) {
            return repo.findByStatus(UserStatus.valueOf(status), pageable);
        }
        return repo.findAll(pageable);
    }

    public void validate(Long id, Long actorId, String actorType) {
        var u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));
        u.setStatus(UserStatus.ACTIVE);
        repo.save(u);
        logService.log(actorId, actorType, "USER_VALIDATED", "AppUser", id,
                "Utilisateur " + u.getEmail() + " activé.", null);
    }

    public void block(Long id, Long actorId, String actorType) {
        var u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));
        u.setStatus(UserStatus.BLOCKED);
        repo.save(u);
        logService.log(actorId, actorType, "USER_BLOCKED", "AppUser", id, null, null);
    }

    public void unblock(Long id, Long actorId, String actorType) {
        var u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));
        u.setStatus(UserStatus.ACTIVE);
        repo.save(u);
        logService.log(actorId, actorType, "USER_UNBLOCKED", "AppUser", id, null, null);
    }
}

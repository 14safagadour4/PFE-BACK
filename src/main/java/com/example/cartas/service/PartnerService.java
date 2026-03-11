package com.example.cartas.service;
import com.example.cartas.repository.PartnerRepository;
import com.example.cartas.repository.RoleRepository;
import com.example.cartas.entity.Partner;
import com.example.cartas.dto.CreatePartnerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerRepository partnerRepo;
    private final RoleRepository    roleRepo;
    private final ActivityLogService logService;

    public Page<Partner> getAll(int page, int size) {
        return partnerRepo.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }

    public Partner create(CreatePartnerRequest req, Long actorId) {
        if (partnerRepo.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email déjà utilisé.");

        var role = roleRepo.findById(req.getRoleId())
                .orElseThrow(() -> new RuntimeException("Rôle introuvable."));

        var partner = Partner.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .role(role)
                .invitationToken(UUID.randomUUID().toString())
                .canViewDashboard(req.getCanViewDashboard())
                .canManageUsers(req.getCanManageUsers())
                .canAddContent(req.getCanAddContent())
                .canEditContent(req.getCanEditContent())
                .canManageSpecialists(req.getCanManageSpecialists())
                .canViewActivityLogs(req.getCanViewActivityLogs())
                .canViewStatistics(req.getCanViewStatistics())
                .build();
        var saved = partnerRepo.save(partner);

        logService.log(actorId, "SUPER_ADMIN", "PARTNER_CREATED",
                "Partner", saved.getId(),
                "Partenaire " + req.getEmail() + " créé avec rôle " + role.getDisplayName(),
                null);

        //  envoyer email d'invitation avec token
        return saved;
    }

    public void toggleActive(Long id, Long actorId) {
        var p = partnerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Partenaire introuvable."));
        p.setIsActive(!p.getIsActive());
        partnerRepo.save(p);
        logService.log(actorId, "SUPER_ADMIN",
                p.getIsActive() ? "PARTNER_ACTIVATED" : "PARTNER_DEACTIVATED",
                "Partner", id, null, null);
    }

    public void delete(Long id, Long actorId) {
        partnerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Partenaire introuvable."));
        partnerRepo.deleteById(id);
        logService.log(actorId, "SUPER_ADMIN", "PARTNER_DELETED",
                "Partner", id, null, null);
    }

    public Partner updateRole(Long id, Long roleId, Long actorId) {
        var p = partnerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Partenaire introuvable."));
        var role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rôle introuvable."));
        p.setRole(role);
        var saved = partnerRepo.save(p);
        logService.log(actorId, "SUPER_ADMIN", "PARTNER_ROLE_CHANGED",
                "Partner", id, "Nouveau rôle: " + role.getDisplayName(), null);
        return saved;
    }
}

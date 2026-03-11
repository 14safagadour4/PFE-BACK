package com.example.cartas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cartas.repository.AppUserRepository;
import com.example.cartas.repository.SpecialistRepository;
import com.example.cartas.repository.ArtTherapistRepository;
import com.example.cartas.repository.EducationalContentRepository;
import com.example.cartas.repository.PartnerRepository;

import com.example.cartas.dto.DashboardStats;
import com.example.cartas.entity.AppUser.UserStatus;
import com.example.cartas.entity.Specialist.SpecialistStatus;
import com.example.cartas.entity.ArtTherapist.TherapistStatus;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AppUserRepository       userRepo;
    private final SpecialistRepository    specRepo;
    private final ArtTherapistRepository  therRepo;
    private final EducationalContentRepository contentRepo;
    private final PartnerRepository       partnerRepo;

    public DashboardStats getStats() {
        long totalUsers   = userRepo.count();
        long activeUsers  = userRepo.countByStatus(UserStatus.ACTIVE);
        long blockedUsers = userRepo.countByStatus(UserStatus.BLOCKED);
        long newThisWeek  = userRepo.countByCreatedAtAfter(LocalDateTime.now().minusDays(7));

        double growth = totalUsers > 0 ? Math.round((newThisWeek * 100.0 / totalUsers) * 10) / 10.0 : 0;

        return DashboardStats.builder()
                .totalUsers(totalUsers).activeUsers(activeUsers)
                .pendingUsers(0L).blockedUsers(blockedUsers)
                .newUsersThisWeek(newThisWeek).userGrowthPercent(growth)
                .totalSpecialists(specRepo.count())
                .pendingSpecialists(specRepo.countByStatus(SpecialistStatus.PENDING))
                .totalTherapists(therRepo.count())
                .pendingTherapists(therRepo.countByStatus(TherapistStatus.PENDING))
                .totalContent(contentRepo.count())
                .publishedContent(contentRepo.countByIsPublished(true))
                .totalPartners(partnerRepo.count())
                .activePartners(partnerRepo.findAll().stream().filter(p -> p.getIsActive()).count())
                .build();
    }
}

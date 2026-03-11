package com.example.cartas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cartas.repository.ActivityLogRepository;
import com.example.cartas.entity.ActivityLog;
@Service
@RequiredArgsConstructor

public class ActivityLogService {

    private final ActivityLogRepository repo;

    public void log(Long actorId, String actorType, String action,
                    String targetType, Long targetId, String details, String ip) {
        repo.save(ActivityLog.builder()
                .actorId(actorId).actorType(actorType).action(action)
                .targetType(targetType).targetId(targetId)
                .details(details).ipAddress(ip)
                .build());
    }
}

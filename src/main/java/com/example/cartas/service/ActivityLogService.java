package com.example.cartas.service;

import org.springframework.stereotype.Service;
import com.example.cartas.repository.ActivityLogRepository;


import com.example.cartas.entity.ActivityLog;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivityLogService {

    private final ActivityLogRepository repo;

    public ActivityLogService(ActivityLogRepository repo) {
        this.repo = repo;
    }


    public void log(Long actorId, String actorType, String action,
                    String targetType, Long targetId, String details, String ip) {
        ActivityLog log = ActivityLog.builder()
                .actorId(actorId).actorType(actorType).action(action)
                .targetType(targetType).targetId(targetId)
                .details(details).ipAddress(ip)
                .build();
        repo.save(log);
    }
}

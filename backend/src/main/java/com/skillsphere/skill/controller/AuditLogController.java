package com.skillsphere.skill.controller;

import com.skillsphere.skill.model.AuditLog;
import com.skillsphere.skill.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/audit")
@CrossOrigin(origins = "*")
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogController(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @GetMapping("/logs")
    public List<AuditLog> getLogs(@RequestParam(required = false) String entity) {
        if (entity != null && !entity.isBlank()) {
            return auditLogRepository.findAllByEntity(entity);
        }
        return auditLogRepository.findAllByOrderByTimestampDesc();
    }

    @PostMapping("/logs")
    @ResponseStatus(HttpStatus.CREATED)
    public AuditLog createLog(@RequestBody AuditLog log, Authentication auth) {
        if (log.getTimestamp() == null) {
            log.setTimestamp(Instant.now());
        }
        if (log.getUsername() == null || log.getUsername().isBlank()) {
            log.setUsername(auth != null ? auth.getName() : "system");
        }
        return auditLogRepository.save(log);
    }
}

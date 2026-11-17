package com.skillsphere.skill.repository;

import com.skillsphere.skill.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findAllByEntity(String entity);
    List<AuditLog> findAllByOrderByTimestampDesc();
}

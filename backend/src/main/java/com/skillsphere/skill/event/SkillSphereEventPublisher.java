package com.skillsphere.skill.event;

import com.skillsphere.skill.model.AuditLog;
import com.skillsphere.skill.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SkillSphereEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(SkillSphereEventPublisher.class);
    private static final String TOPIC = "skillsphere-events";

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void publishEvent(SkillSphereEvent event) {
        logger.info("Publishing domain event: [type={}, entity={}, id={}]",
                event.eventType(), event.entityType(), event.entityId());

        // 1. Send to Kafka if broker is available
        if (kafkaTemplate != null) {
            try {
                String message = String.format("{\"eventId\":\"%s\",\"type\":\"%s\",\"entity\":\"%s\",\"id\":%d,\"payload\":\"%s\",\"by\":\"%s\",\"time\":\"%s\"}",
                        event.eventId(), event.eventType(), event.entityType(), event.entityId(),
                        event.payload(), event.triggeredBy(), event.timestamp());
                kafkaTemplate.send(TOPIC, event.entityType(), message);
                logger.info("Event successfully dispatched to Kafka topic [{}]", TOPIC);
            } catch (Exception e) {
                logger.warn("Kafka event dispatch skipped (broker offline or unreachable): {}", e.getMessage());
            }
        }

        // 2. Persist in immutable audit trail for traceability
        try {
            AuditLog audit = new AuditLog();
            audit.setEntity(event.entityType());
            audit.setAction(event.eventType() + ": " + event.payload());
            audit.setUsername(event.triggeredBy() != null ? event.triggeredBy() : "system");
            audit.setTimestamp(Instant.now());
            auditLogRepository.save(audit);
        } catch (Exception e) {
            logger.error("Failed to write audit log for event: {}", e.getMessage());
        }
    }
}

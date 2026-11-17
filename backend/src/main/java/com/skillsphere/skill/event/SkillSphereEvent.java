package com.skillsphere.skill.event;

import java.time.Instant;
import java.util.UUID;

public record SkillSphereEvent(
    UUID eventId,
    String eventType,
    String entityType,
    Long entityId,
    String payload,
    String triggeredBy,
    Instant timestamp
) {
    public static SkillSphereEvent of(String eventType, String entityType, Long entityId, String payload, String triggeredBy) {
        return new SkillSphereEvent(UUID.randomUUID(), eventType, entityType, entityId, payload, triggeredBy, Instant.now());
    }
}

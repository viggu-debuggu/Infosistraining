package com.skillsphere.skill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaHealthController {

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaHealthController() {
    }

    public KafkaHealthController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Simple health check that attempts to send a ping message to a health topic.
     * Returns 200 if the send succeeds, otherwise 500.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        if (kafkaTemplate == null) {
            return ResponseEntity.ok("Kafka is optional and disabled in local mode");
        }
        try {
            kafkaTemplate.send("health-check", "ping").get();
            return ResponseEntity.ok("Kafka is healthy");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Kafka health check failed: " + e.getMessage());
        }
    }
}


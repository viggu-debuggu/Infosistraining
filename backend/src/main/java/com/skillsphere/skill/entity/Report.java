package com.skillsphere.skill.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "system_reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID reportId = UUID.randomUUID();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type; // SKILL_MANAGEMENT, LEARNING_MANAGEMENT, CERTIFICATION_COMPLIANCE, CAREER_ANALYTICS, EXECUTIVE_TALENT

    @Column(columnDefinition = "TEXT")
    private String data; // JSON string payload

    private LocalDateTime generatedAt = LocalDateTime.now();

    private String generatedBy = "System Administrator";

    public Report() {}

    public Report(String title, String type, String data, String generatedBy) {
        this.title = title;
        this.type = type;
        this.data = data;
        this.generatedBy = generatedBy;
        this.reportId = UUID.randomUUID();
        this.generatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UUID getReportId() { return reportId; }
    public void setReportId(UUID reportId) { this.reportId = reportId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }

    public String getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(String generatedBy) { this.generatedBy = generatedBy; }
}

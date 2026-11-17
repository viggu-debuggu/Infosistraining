package com.skillsphere.skill.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "trainings")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID trainId = UUID.randomUUID();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String trainer;

    private String type = "BOOTCAMP"; // BOOTCAMP, WORKSHOP, WEBINAR

    private String status = "SCHEDULED"; // SCHEDULED, IN_PROGRESS, COMPLETED

    private Integer capacity = 50;

    private Integer enrolledCount = 0;

    private Long courseId;

    private String locationOrUrl = "Virtual Classroom / Zoom";

    public Training() {}

    public Training(String title, LocalDateTime date, String trainer, String type, Long courseId) {
        this.title = title;
        this.date = date;
        this.trainer = trainer;
        this.type = type;
        this.courseId = courseId;
        this.trainId = UUID.randomUUID();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UUID getTrainId() { return trainId; }
    public void setTrainId(UUID trainId) { this.trainId = trainId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getTrainer() { return trainer; }
    public void setTrainer(String trainer) { this.trainer = trainer; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getEnrolledCount() { return enrolledCount; }
    public void setEnrolledCount(Integer enrolledCount) { this.enrolledCount = enrolledCount; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getLocationOrUrl() { return locationOrUrl; }
    public void setLocationOrUrl(String locationOrUrl) { this.locationOrUrl = locationOrUrl; }
}

package com.skillsphere.skill.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "skill_competency")
public class SkillCompetency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private String skillName;

    private int level; // 0-10 rating

    private Instant assessedAt;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public Instant getAssessedAt() { return assessedAt; }
    public void setAssessedAt(Instant assessedAt) { this.assessedAt = assessedAt; }
}


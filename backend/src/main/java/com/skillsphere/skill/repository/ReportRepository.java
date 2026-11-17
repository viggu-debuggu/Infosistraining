package com.skillsphere.skill.repository;

import com.skillsphere.skill.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByReportId(UUID reportId);
    List<Report> findByType(String type);
    List<Report> findAllByOrderByGeneratedAtDesc();
}

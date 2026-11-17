package com.skillsphere.skill.controller;

import com.skillsphere.skill.entity.Report;
import com.skillsphere.skill.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uuid/{reportId}")
    public ResponseEntity<Report> getReportByReportId(@PathVariable UUID reportId) {
        return reportService.getReportByReportId(reportId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/generate/{milestoneNumber}")
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'EMPLOYEE', 'STUDENT')")
    @ResponseStatus(HttpStatus.CREATED)
    public Report generateMilestoneReport(@PathVariable int milestoneNumber, Authentication auth) {
        String username = auth != null ? auth.getName() : "HR Administrator";
        return reportService.generateMilestoneReport(milestoneNumber, username);
    }
}

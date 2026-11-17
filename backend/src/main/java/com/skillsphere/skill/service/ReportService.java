package com.skillsphere.skill.service;

import com.skillsphere.skill.entity.Report;
import com.skillsphere.skill.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAllByOrderByGeneratedAtDesc();
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public Optional<Report> getReportByReportId(UUID reportId) {
        return reportRepository.findByReportId(reportId);
    }

    @Transactional
    public Report createReport(Report report) {
        if (report.getReportId() == null) {
            report.setReportId(UUID.randomUUID());
        }
        if (report.getGeneratedAt() == null) {
            report.setGeneratedAt(LocalDateTime.now());
        }
        return reportRepository.save(report);
    }

    @Transactional
    public Report generateMilestoneReport(int milestoneNumber, String user) {
        String title;
        String type;
        String jsonData;

        switch (milestoneNumber) {
            case 1:
                title = "Milestone 1 - Employee Skill Management & Competency Report";
                type = "SKILL_MANAGEMENT";
                jsonData = "{\"milestone\":1,\"employeesActive\":\"12.4K\",\"skillsTracked\":2847,\"assessmentsMonthly\":\"847/month\",\"targetCoverage\":\"87%\",\"featuredEmployee\":{\"name\":\"John Smith\",\"role\":\"Developer\",\"dept\":\"Engineering\",\"skills\":[{\"name\":\"Java\",\"score\":\"8/10\"},{\"name\":\"Spring Boot\",\"score\":\"7/10\"},{\"name\":\"Angular\",\"score\":\"6/10\"}],\"assessmentScore\":87,\"competencyGap\":\"Angular 3 points\",\"experience\":\"5 years\",\"projects\":12,\"rating\":\"4.2/5\"}}";
                break;
            case 2:
                title = "Milestone 2 - Learning Management & LMS Completion Report";
                type = "LEARNING_MANAGEMENT";
                jsonData = "{\"milestone\":2,\"coursesAvailable\":847,\"monthlyEnrollments\":\"12.4K\",\"completionRate\":\"87%\",\"featuredCourse\":{\"title\":\"Spring Boot 4 Microservices\",\"duration\":\"40h\",\"enrolled\":247,\"completed\":189,\"inProgress\":58,\"rating\":4.8,\"learningPath\":\"Java Full Stack (67%)\",\"assessmentExamScore\":92}}";
                break;
            case 3:
                title = "Milestone 3 - Certification Tracking & Expiry Compliance Report";
                type = "CERTIFICATION_COMPLIANCE";
                jsonData = "{\"milestone\":3,\"activeCertifications\":\"8.4K\",\"expiringNext30Days\":247,\"onTimeRenewalRate\":\"94%\",\"featuredCert\":{\"title\":\"AWS Solutions Architect\",\"employee\":\"John Smith\",\"expiry\":\"15-Mar-2028\",\"status\":\"Valid\",\"dueInDays\":247,\"notificationSent\":true,\"expiredCerts\":[\"Java OCP\"]}}";
                break;
            case 4:
                title = "Milestone 4 - Career Planning & Internal Mobility Report";
                type = "CAREER_ANALYTICS";
                jsonData = "{\"milestone\":4,\"activeCareerPlans\":2847,\"annualPromotions\":247,\"departmentSkillCoverage\":\"87%\",\"featuredPlan\":{\"employee\":\"John Smith\",\"currentRole\":\"Developer\",\"targetRole\":\"Tech Lead\",\"progress\":\"67%\",\"gaps\":[\"Angular +3\",\"Leadership +2\"],\"mentor\":\"Jane Doe\",\"promotionEligibility\":\"Eligible in 3 months (Criteria: 80%)\",\"jobMatches\":12,\"openings\":3}}";
                break;
            default:
                title = "Master Integrated Talent Command Center Report";
                type = "EXECUTIVE_TALENT";
                jsonData = "{\"milestone\":\"All\",\"platform\":\"SkillSphere Nexus\",\"integratedStatus\":\"Healthy\",\"activeWorkforce\":\"12.4K\",\"totalSkills\":2847,\"totalCourses\":847,\"certifications\":8400,\"careerPlans\":2847,\"promotions\":247,\"systemAuditOrder\":\"Kafka Event-Driven\",\"compliance\":\"94%\"}";
                break;
        }

        Report report = new Report(title, type, jsonData, user != null ? user : "System Administrator");
        return reportRepository.save(report);
    }
}

package com.skillsphere.skill.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.skillsphere.skill.entity.ApprovalRequest;
import com.skillsphere.skill.entity.CareerPlan;
import com.skillsphere.skill.entity.Certification;
import com.skillsphere.skill.entity.CertificationStatus;
import com.skillsphere.skill.entity.CompetencyFramework;
import com.skillsphere.skill.entity.Course;
import com.skillsphere.skill.entity.Employee;
import com.skillsphere.skill.entity.EmployeeSkillMapping;
import com.skillsphere.skill.entity.FrameworkSkillRequirement;
import com.skillsphere.skill.entity.InternalJob;
import com.skillsphere.skill.entity.LearningPath;
import com.skillsphere.skill.entity.PromotionCriteria;
import com.skillsphere.skill.entity.Report;
import com.skillsphere.skill.entity.Skill;
import com.skillsphere.skill.entity.SkillAssessment;
import com.skillsphere.skill.entity.Student;
import com.skillsphere.skill.entity.Training;
import com.skillsphere.skill.entity.VerificationStatus;
import com.skillsphere.skill.model.AuditLog;
import com.skillsphere.skill.repository.ApprovalRequestRepository;
import com.skillsphere.skill.repository.AuditLogRepository;
import com.skillsphere.skill.repository.CareerPlanRepository;
import com.skillsphere.skill.repository.CertificationRepository;
import com.skillsphere.skill.repository.CompetencyFrameworkRepository;
import com.skillsphere.skill.repository.CourseRepository;
import com.skillsphere.skill.repository.EmployeeRepository;
import com.skillsphere.skill.repository.EmployeeSkillMappingRepository;
import com.skillsphere.skill.repository.FrameworkSkillRequirementRepository;
import com.skillsphere.skill.repository.InternalJobRepository;
import com.skillsphere.skill.repository.LearningPathRepository;
import com.skillsphere.skill.repository.PromotionCriteriaRepository;
import com.skillsphere.skill.repository.ReportRepository;
import com.skillsphere.skill.repository.SkillAssessmentRepository;
import com.skillsphere.skill.repository.SkillRepository;
import com.skillsphere.skill.repository.StudentRepository;
import com.skillsphere.skill.repository.TrainingRepository;
import com.skillsphere.skill.security.AppUser;
import com.skillsphere.skill.security.AppUserRepository;
import com.skillsphere.skill.security.UserRole;

@Component
@org.springframework.context.annotation.Profile({"local", "docker", "demo"})
public class EnterpriseDemoDataInitializer implements ApplicationRunner {

    private final CourseRepository courseRepository;
    private final LearningPathRepository learningPathRepository;
    private final InternalJobRepository internalJobRepository;
    private final SkillRepository skillRepository;
    private final EmployeeRepository employeeRepository;
    private final StudentRepository studentRepository;
    private final AppUserRepository appUserRepository;
    private final CareerPlanRepository careerPlanRepository;
    private final CertificationRepository certificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompetencyFrameworkRepository competencyFrameworkRepository;
    private final FrameworkSkillRequirementRepository requirementRepository;
    private final EmployeeSkillMappingRepository employeeSkillMappingRepository;
    private final SkillAssessmentRepository skillAssessmentRepository;
    private final IssuedCertificateService issuedCertificateService;
    private final TrainingRepository trainingRepository;
    private final ReportRepository reportRepository;
    private final AuditLogRepository auditLogRepository;
    private final PromotionCriteriaRepository promotionCriteriaRepository;
    private final ApprovalRequestRepository approvalRequestRepository;

    public EnterpriseDemoDataInitializer(CourseRepository courseRepository,
                                         LearningPathRepository learningPathRepository,
                                         InternalJobRepository internalJobRepository,
                                         SkillRepository skillRepository,
                                         EmployeeRepository employeeRepository,
                                         StudentRepository studentRepository,
                                         AppUserRepository appUserRepository,
                                         CareerPlanRepository careerPlanRepository,
                                         CertificationRepository certificationRepository,
                                         PasswordEncoder passwordEncoder,
                                         CompetencyFrameworkRepository competencyFrameworkRepository,
                                         FrameworkSkillRequirementRepository requirementRepository,
                                         EmployeeSkillMappingRepository employeeSkillMappingRepository,
                                         SkillAssessmentRepository skillAssessmentRepository,
                                         IssuedCertificateService issuedCertificateService,
                                         TrainingRepository trainingRepository,
                                         ReportRepository reportRepository,
                                         AuditLogRepository auditLogRepository,
                                         PromotionCriteriaRepository promotionCriteriaRepository,
                                         ApprovalRequestRepository approvalRequestRepository) {
        this.courseRepository = courseRepository;
        this.learningPathRepository = learningPathRepository;
        this.internalJobRepository = internalJobRepository;
        this.skillRepository = skillRepository;
        this.employeeRepository = employeeRepository;
        this.studentRepository = studentRepository;
        this.appUserRepository = appUserRepository;
        this.careerPlanRepository = careerPlanRepository;
        this.certificationRepository = certificationRepository;
        this.passwordEncoder = passwordEncoder;
        this.competencyFrameworkRepository = competencyFrameworkRepository;
        this.requirementRepository = requirementRepository;
        this.employeeSkillMappingRepository = employeeSkillMappingRepository;
        this.skillAssessmentRepository = skillAssessmentRepository;
        this.issuedCertificateService = issuedCertificateService;
        this.trainingRepository = trainingRepository;
        this.reportRepository = reportRepository;
        this.auditLogRepository = auditLogRepository;
        this.promotionCriteriaRepository = promotionCriteriaRepository;
        this.approvalRequestRepository = approvalRequestRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // Seed Skills
        if (skillRepository.count() == 0) {
            skillRepository.saveAll(List.of(
                new Skill("Java 25 & Spring Boot 4", "Backend", "Enterprise core framework, virtual threads, reactive and microservices architecture."),
                new Skill("Angular 20 & TypeScript", "Frontend", "Signals, Standalone components, state management, and modern responsive UI design."),
                new Skill("Kubernetes & Docker", "DevOps", "Containerization, cluster configuration, Helm charts, ingress, and production deployment."),
                new Skill("AWS Cloud Solutions", "Cloud", "VPC, ECS, Lambda, IAM policies, S3, and scalable enterprise cloud design."),
                new Skill("PostgreSQL & Database Optimization", "Data", "ACID transactions, relational modeling, indexing, and Flyway schema migrations."),
                new Skill("Kafka Event Streaming", "Architecture", "Distributed event messaging, topic partitioning, consumers, and event-driven architecture."),
                new Skill("REST & GraphQL APIs", "Integration", "High-performance API contracts, OpenAPI 3 specifications, and authentication."),
                new Skill("Agile Scrum & Technical Leadership", "Management", "Sprint planning, pair programming, technical backlog grooming, and code reviews.")
            ));
        }

        // Seed Courses
        if (courseRepository.count() == 0) {
            Course c1 = new Course("Spring Boot 4 Microservices & Cloud", "Enterprise microservices architecture, event streaming with Kafka, and containerized deployment.", 40, "Jane Doe", "ONLINE", 4.8, "Backend");
            c1.setTotalEnrolled(247);
            c1.setTotalCompleted(189);
            courseRepository.save(c1);

            Course c2 = new Course("Angular 20 Full-Stack Architecture", "Modern frontend engineering with Angular 20, Signals, Standalone Components, and Material design.", 35, "Alex Chen", "WORKSHOP", 4.9, "Frontend");
            c2.setTotalEnrolled(180);
            c2.setTotalCompleted(150);
            courseRepository.save(c2);

            Course c3 = new Course("Cloud & DevOps: AWS, Docker & Kubernetes", "Cloud native architecture, container orchestration with Kubernetes, CI/CD pipelines and Prometheus monitoring.", 45, "Sarah Miller", "BOOTCAMP", 4.7, "DevOps");
            c3.setTotalEnrolled(210);
            c3.setTotalCompleted(175);
            courseRepository.save(c3);

            Course c4 = new Course("Enterprise Banking Domain & Financial Systems", "Core banking workflows, compliance, regulatory reporting, and payment gateway security.", 30, "Michael Vance", "WEBINAR", 4.6, "Domain");
            c4.setTotalEnrolled(140);
            c4.setTotalCompleted(115);
            courseRepository.save(c4);
        }

        // Seed Learning Paths
        if (learningPathRepository.count() == 0) {
            LearningPath p1 = new LearningPath("Java Full Stack Developer Track", "Comprehensive track covering Java 25, Spring Boot 4, Angular 20, and PostgreSQL.", "Full Stack Developer", 4, 67, "Java 25, Spring Boot 4, Angular 20, PostgreSQL");
            learningPathRepository.save(p1);

            LearningPath p2 = new LearningPath("Cloud Architect & DevOps Mastery", "Production Kubernetes, AWS architecture, Prometheus metrics, and CI/CD automation.", "Cloud Solutions Architect", 5, 45, "AWS, Kubernetes, Docker, Terraform");
            learningPathRepository.save(p2);
        }

        // Seed Internal Jobs
        if (internalJobRepository.count() == 0) {
            InternalJob j1 = new InternalJob("Tech Lead - Cloud Platforms", "Engineering", "Lead the engineering team building enterprise cloud microservices and event architecture.", "Java, Spring Boot, Angular, AWS", 3);
            internalJobRepository.save(j1);

            InternalJob j2 = new InternalJob("Senior DevOps & Site Reliability Engineer", "Infrastructure", "Architect and maintain Kubernetes clusters, Kafka streaming, and observability.", "Docker, Kubernetes, Kafka, CI/CD", 2);
            internalJobRepository.save(j2);

            InternalJob j3 = new InternalJob("Full Stack Software Engineer", "Core Banking", "Develop end-to-end features for core banking workflows and payments.", "Java, Angular, PostgreSQL, REST", 4);
            internalJobRepository.save(j3);
        }

        // Seed John Smith (Core PDF Milestone Employee)
        Employee johnSmith = employeeRepository.findAll().stream()
                .filter(e -> "john.smith@skillsphere.com".equalsIgnoreCase(e.getEmail()))
                .findFirst()
                .orElseGet(() -> {
                    Employee e = new Employee("John Smith", "john.smith@skillsphere.com", "Developer", "Engineering");
                    return employeeRepository.save(e);
                });

        if (!appUserRepository.existsByUsername("john.smith@skillsphere.com")) {
            AppUser johnUser = new AppUser("john.smith@skillsphere.com", passwordEncoder.encode("john12345"), UserRole.EMPLOYEE, johnSmith.getId());
            appUserRepository.save(johnUser);
        }

        Employee demoEmployee = employeeRepository.findAll().stream()
                .filter(e -> "employee@skillsphere.com".equalsIgnoreCase(e.getEmail()))
                .findFirst()
                .orElseGet(() -> {
                    Employee e = new Employee("Alex Johnson", "employee@skillsphere.com", "Full Stack Engineer", "Engineering");
                    return employeeRepository.save(e);
                });

        if (!appUserRepository.existsByUsername("employee@skillsphere.com")) {
            AppUser empUser = new AppUser("employee@skillsphere.com", passwordEncoder.encode("employee12345"), UserRole.EMPLOYEE, demoEmployee.getId());
            appUserRepository.save(empUser);
        }

        // Skills for demo mappings
        List<Skill> skills = skillRepository.findAll();
        Skill javaS = skills.stream().filter(s -> s.getName().contains("Java")).findFirst().orElse(null);
        Skill springS = skills.stream().filter(s -> s.getName().contains("Spring")).findFirst().orElse(javaS);
        Skill angularS = skills.stream().filter(s -> s.getName().contains("Angular")).findFirst().orElse(null);

        // Seed Alex Johnson demo data for employee@skillsphere.com
        if (javaS != null && !employeeSkillMappingRepository.existsByEmployee_IdAndSkill_Id(demoEmployee.getId(), javaS.getId())) {
            EmployeeSkillMapping m = new EmployeeSkillMapping();
            m.setEmployee(demoEmployee);
            m.setSkill(javaS);
            m.setProficiency(8);
            m.setVerificationStatus(VerificationStatus.VERIFIED);
            m.setAssessmentDate(LocalDate.now().minusMonths(1));
            employeeSkillMappingRepository.save(m);
        }
        if (angularS != null && !employeeSkillMappingRepository.existsByEmployee_IdAndSkill_Id(demoEmployee.getId(), angularS.getId())) {
            EmployeeSkillMapping m = new EmployeeSkillMapping();
            m.setEmployee(demoEmployee);
            m.setSkill(angularS);
            m.setProficiency(7);
            m.setVerificationStatus(VerificationStatus.VERIFIED);
            m.setAssessmentDate(LocalDate.now().minusWeeks(2));
            employeeSkillMappingRepository.save(m);
        }
        if (certificationRepository.findByEmployee_Id(demoEmployee.getId()).isEmpty()) {
            Certification devCert = new Certification();
            devCert.setName("AWS Certified Developer - Associate");
            devCert.setIssuingOrganization("Amazon Web Services");
            devCert.setCredentialId("AWS-DEV-77123");
            devCert.setEmployee(demoEmployee);
            devCert.setIssueDate(LocalDate.of(2025, 4, 1));
            devCert.setExpiryDate(LocalDate.of(2028, 4, 1));
            devCert.setStatus(CertificationStatus.ACTIVE);
            devCert.setVerificationStatus(VerificationStatus.VERIFIED);
            certificationRepository.save(devCert);
        }
        if (careerPlanRepository.findByEmployeeId(demoEmployee.getId()).isEmpty()) {
            CareerPlan cp = new CareerPlan();
            cp.setEmployee(demoEmployee);
            cp.setCurrentRole("Full Stack Engineer");
            cp.setTargetRole("Lead Cloud Architect");
            cp.setProgressPercentage(75);
            cp.setSkillGaps("Kubernetes +2 points, Distributed Systems +1 point");
            cp.setMentorName("Jane Doe");
            cp.setPromotionReadinessScore(85);
            cp.setPromotionEligibility("Eligible in 1 month (Criteria: 80%)");
            cp.setStatus("ACTIVE");
            careerPlanRepository.save(cp);
        }
        if (promotionCriteriaRepository.findByEmployeeId(demoEmployee.getId()).isEmpty()) {
            PromotionCriteria pc = new PromotionCriteria();
            pc.setEmployeeId(demoEmployee.getId());
            pc.setCriteriaJson("{\"requiredSkillScore\":80,\"requiredYearsExperience\":4,\"targetRole\":\"Lead Cloud Architect\",\"currentScore\":85,\"isEligible\":true,\"promotionCycle\":\"Q4 Enterprise Review\"}");
            promotionCriteriaRepository.save(pc);
        }

        // John Smith Skill Mappings: Java 8/10, Spring Boot 7/10, Angular 6/10

        if (javaS != null && !employeeSkillMappingRepository.existsByEmployee_IdAndSkill_Id(johnSmith.getId(), javaS.getId())) {
            EmployeeSkillMapping m = new EmployeeSkillMapping();
            m.setEmployee(johnSmith);
            m.setSkill(javaS);
            m.setProficiency(8);
            m.setVerificationStatus(VerificationStatus.VERIFIED);
            m.setAssessmentDate(LocalDate.now().minusMonths(1));
            employeeSkillMappingRepository.save(m);
        }
        if (springS != null && !employeeSkillMappingRepository.existsByEmployee_IdAndSkill_Id(johnSmith.getId(), springS.getId())) {
            EmployeeSkillMapping m = new EmployeeSkillMapping();
            m.setEmployee(johnSmith);
            m.setSkill(springS);
            m.setProficiency(7);
            m.setVerificationStatus(VerificationStatus.VERIFIED);
            m.setAssessmentDate(LocalDate.now().minusWeeks(2));
            employeeSkillMappingRepository.save(m);
        }
        if (angularS != null && !employeeSkillMappingRepository.existsByEmployee_IdAndSkill_Id(johnSmith.getId(), angularS.getId())) {
            EmployeeSkillMapping m = new EmployeeSkillMapping();
            m.setEmployee(johnSmith);
            m.setSkill(angularS);
            m.setProficiency(6);
            m.setVerificationStatus(VerificationStatus.PENDING_VERIFICATION);
            m.setAssessmentDate(LocalDate.now().minusWeeks(3));
            employeeSkillMappingRepository.save(m);
        }

        // John Smith Certifications: AWS SAA (Valid until 15-Mar-2028), Java OCP (Expired)
        if (certificationRepository.findByEmployee_Id(johnSmith.getId()).isEmpty()) {
            Certification awsCert = new Certification();
            awsCert.setName("AWS Solutions Architect Associate (SAA)");
            awsCert.setIssuingOrganization("Amazon Web Services");
            awsCert.setCredentialId("AWS-SAA-99214");
            awsCert.setEmployee(johnSmith);
            awsCert.setIssueDate(LocalDate.of(2025, 3, 15));
            awsCert.setExpiryDate(LocalDate.of(2028, 3, 15));
            awsCert.setStatus(CertificationStatus.ACTIVE);
            awsCert.setVerificationStatus(VerificationStatus.VERIFIED);
            certificationRepository.save(awsCert);

            Certification ocpCert = new Certification();
            ocpCert.setName("Oracle Certified Professional: Java SE (OCP)");
            ocpCert.setIssuingOrganization("Oracle");
            ocpCert.setCredentialId("OCP-JAVA-44810");
            ocpCert.setEmployee(johnSmith);
            ocpCert.setIssueDate(LocalDate.of(2021, 1, 10));
            ocpCert.setExpiryDate(LocalDate.of(2024, 1, 10));
            ocpCert.setStatus(CertificationStatus.EXPIRED);
            ocpCert.setVerificationStatus(VerificationStatus.VERIFIED);
            certificationRepository.save(ocpCert);
        }

        // John Smith Verified Assessment: Spring Boot, Score: 87%
        if (springS != null && skillAssessmentRepository.findByEmployee_Id(johnSmith.getId()).isEmpty()) {
            SkillAssessment sa = new SkillAssessment();
            sa.setEmployee(johnSmith);
            sa.setSkill(springS);
            sa.setScore(87);
            sa.setMaxScore(100);
            sa.setAssessmentDate(LocalDate.now().minusDays(14));
            sa.setAssessmentMethod("Spring Boot Advanced Architecture Assessment");
            sa.setVerificationStatus(VerificationStatus.VERIFIED);
            sa.setAssessorName("Enterprise Tech Lead Panel");
            skillAssessmentRepository.save(sa);
        }

        // John Smith Career Plan: Developer -> Tech Lead, Progress: 67%, Gaps: Angular +3, Leadership +2, Mentor: Jane Doe
        if (careerPlanRepository.findByEmployeeId(johnSmith.getId()).isEmpty()) {
            CareerPlan cp = new CareerPlan();
            cp.setEmployee(johnSmith);
            cp.setCurrentRole("Developer");
            cp.setTargetRole("Tech Lead");
            cp.setProgressPercentage(67);
            cp.setSkillGaps("Angular +3 points, Technical Leadership +2 points");
            cp.setMentorName("Jane Doe");
            cp.setPromotionReadinessScore(80);
            cp.setPromotionEligibility("Eligible in 3 months (Criteria: 80%)");
            cp.setStatus("ACTIVE");
            careerPlanRepository.save(cp);
        }

        // John Smith Promotion Criteria
        if (promotionCriteriaRepository.findByEmployeeId(johnSmith.getId()).isEmpty()) {
            PromotionCriteria pc = new PromotionCriteria();
            pc.setEmployeeId(johnSmith.getId());
            pc.setCriteriaJson("{\"requiredSkillScore\":80,\"requiredYearsExperience\":5,\"targetRole\":\"Tech Lead\",\"currentScore\":80,\"isEligible\":true,\"promotionCycle\":\"Q3 Enterprise Review\"}");
            promotionCriteriaRepository.save(pc);
        }

        // Seed Trainings (Milestone 2 & 4 requirement)
        if (trainingRepository.count() == 0) {
            Course courseRef = courseRepository.findAll().stream().findFirst().orElse(null);
            Long cid = courseRef != null ? courseRef.getId() : null;

            Training t1 = new Training("Angular 20 Mastery Bootcamp", LocalDateTime.now().plusDays(7), "Jane Doe", "BOOTCAMP", cid);
            t1.setCapacity(50);
            t1.setEnrolledCount(24);
            t1.setLocationOrUrl("Virtual Lab Room A");
            trainingRepository.save(t1);

            Training t2 = new Training("Spring Boot 4 Microservices & Kafka Workshop", LocalDateTime.now().plusDays(14), "Alex Chen", "WORKSHOP", cid);
            t2.setCapacity(40);
            t2.setEnrolledCount(35);
            t2.setLocationOrUrl("Enterprise Training Center - Room 302");
            trainingRepository.save(t2);

            Training t3 = new Training("Cloud Native AWS & Kubernetes Deep-Dive", LocalDateTime.now().plusDays(21), "Sarah Miller", "WEBINAR", cid);
            t3.setCapacity(100);
            t3.setEnrolledCount(68);
            t3.setLocationOrUrl("Cloud Academy Zoom Webinar");
            trainingRepository.save(t3);
        }

        // Seed Reports for all milestones (M1 - M4 + Talent Command)
        if (reportRepository.count() == 0) {
            reportRepository.save(new Report(
                "Milestone 1 - Employee Skill Management & Competency Report",
                "SKILL_MANAGEMENT",
                "{\"milestone\":1,\"employeesActive\":\"12.4K\",\"skillsTracked\":2847,\"assessmentsMonthly\":\"847/month\",\"targetCoverage\":\"87%\",\"featuredEmployee\":{\"name\":\"John Smith\",\"role\":\"Developer\",\"dept\":\"Engineering\",\"skills\":[{\"name\":\"Java\",\"score\":\"8/10\"},{\"name\":\"Spring Boot\",\"score\":\"7/10\"},{\"name\":\"Angular\",\"score\":\"6/10\"}],\"assessmentScore\":87,\"competencyGap\":\"Angular 3 points\",\"experience\":\"5 years\",\"projects\":12,\"rating\":\"4.2/5\"}}",
                "HR Manager"
            ));

            reportRepository.save(new Report(
                "Milestone 2 - Learning Management & LMS Completion Report",
                "LEARNING_MANAGEMENT",
                "{\"milestone\":2,\"coursesAvailable\":847,\"monthlyEnrollments\":\"12.4K\",\"completionRate\":\"87%\",\"featuredCourse\":{\"title\":\"Spring Boot 4 Microservices\",\"duration\":\"40h\",\"enrolled\":247,\"completed\":189,\"inProgress\":58,\"rating\":4.8,\"learningPath\":\"Java Full Stack (67%)\",\"assessmentExamScore\":92}}",
                "HR Manager"
            ));

            reportRepository.save(new Report(
                "Milestone 3 - Certification Tracking & Expiry Compliance Report",
                "CERTIFICATION_COMPLIANCE",
                "{\"milestone\":3,\"activeCertifications\":\"8.4K\",\"expiringNext30Days\":247,\"onTimeRenewalRate\":\"94%\",\"featuredCert\":{\"title\":\"AWS Solutions Architect\",\"employee\":\"John Smith\",\"expiry\":\"15-Mar-2028\",\"status\":\"Valid\",\"dueInDays\":247,\"notificationSent\":true,\"expiredCerts\":[\"Java OCP\"]}}",
                "Compliance Officer"
            ));

            reportRepository.save(new Report(
                "Milestone 4 - Career Planning & Internal Mobility Report",
                "CAREER_ANALYTICS",
                "{\"milestone\":4,\"activeCareerPlans\":2847,\"annualPromotions\":247,\"departmentSkillCoverage\":\"87%\",\"featuredPlan\":{\"employee\":\"John Smith\",\"currentRole\":\"Developer\",\"targetRole\":\"Tech Lead\",\"progress\":\"67%\",\"gaps\":[\"Angular +3\",\"Leadership +2\"],\"mentor\":\"Jane Doe\",\"promotionEligibility\":\"Eligible in 3 months (Criteria: 80%)\",\"jobMatches\":12,\"openings\":3}}",
                "Talent Director"
            ));

            reportRepository.save(new Report(
                "Master Integrated Talent Command Center Report",
                "EXECUTIVE_TALENT",
                "{\"milestone\":\"All\",\"platform\":\"SkillSphere Nexus\",\"integratedStatus\":\"Healthy\",\"activeWorkforce\":\"12.4K\",\"totalSkills\":2847,\"totalCourses\":847,\"certifications\":8400,\"careerPlans\":2847,\"promotions\":247,\"systemAuditOrder\":\"Kafka Event-Driven\",\"compliance\":\"94%\"}",
                "Chief People Officer"
            ));
        }

        // Seed Approval Requests (Critical Actions Require Approval - Page 9 requirement)
        if (approvalRequestRepository.count() == 0) {
            ApprovalRequest req1 = new ApprovalRequest(
                "PROMOTION_APPROVAL",
                johnSmith.getId(),
                johnSmith.getName(),
                "Promotion to Tech Lead",
                "John Smith has met 80% promotion benchmark, completed 5 years experience, and closed Angular gaps."
            );
            approvalRequestRepository.save(req1);

            ApprovalRequest req2 = new ApprovalRequest(
                "CERTIFICATION_RENEWAL",
                johnSmith.getId(),
                johnSmith.getName(),
                "AWS Solutions Architect Renewal Voucher",
                "Corporate sponsorship approval requested for upcoming AWS renewal exam."
            );
            approvalRequestRepository.save(req2);

            ApprovalRequest req3 = new ApprovalRequest(
                "TRAINING_NOMINATION",
                johnSmith.getId(),
                johnSmith.getName(),
                "Angular 20 Mastery Bootcamp Nomination",
                "Advanced training session enrollment to satisfy Tech Lead leadership competency."
            );
            req3.setStatus("APPROVED");
            req3.setReviewedBy("admin@skillsphere.com");
            req3.setReviewedAt(Instant.now());
            req3.setReviewNotes("Approved by HR Director");
            approvalRequestRepository.save(req3);
        }

        // Seed Audit Logs for immutable learning tracking (Page 9 requirement)
        if (auditLogRepository.count() == 0) {
            AuditLog log1 = new AuditLog();
            log1.setEntity("SkillAssessment");
            log1.setAction("ASSESSMENT_VERIFIED: Spring Boot 4 for John Smith (87%)");
            log1.setUsername("admin@skillsphere.com");
            log1.setTimestamp(Instant.now().minusSeconds(86400 * 2));
            auditLogRepository.save(log1);

            AuditLog log2 = new AuditLog();
            log2.setEntity("CourseEnrollment");
            log2.setAction("ENROLLMENT_COMPLETED: Java Full Stack Developer Track for Sarah Connor");
            log2.setUsername("sarah.connor@skillsphere.com");
            log2.setTimestamp(Instant.now().minusSeconds(86400));
            auditLogRepository.save(log2);

            AuditLog log3 = new AuditLog();
            log3.setEntity("Certification");
            log3.setAction("RENEWAL_NOTIFICATION_DISPATCHED: AWS Solutions Architect for John Smith");
            log3.setUsername("system-cron");
            log3.setTimestamp(Instant.now().minusSeconds(3600 * 4));
            auditLogRepository.save(log3);
        }

        // Seed Student User
        if (!appUserRepository.existsByUsername("student@skillsphere.com")) {
            Student student = studentRepository.findAll().stream().findFirst().orElseGet(() -> {
                Student s = new Student("David Kim", "david.kim@skillsphere.com", "Full-Stack Cloud Graduate Program");
                return studentRepository.save(s);
            });

            AppUser studentUser = new AppUser("student@skillsphere.com", passwordEncoder.encode("student12345"), UserRole.STUDENT, null, student.getId());
            appUserRepository.save(studentUser);
        }

        // Seed Competency Frameworks
        if (competencyFrameworkRepository.count() == 0) {
            CompetencyFramework f1 = new CompetencyFramework(
                "Full Stack Cloud Engineer Framework",
                "Core competency model for enterprise full-stack cloud engineers across modern stack layers.",
                "Senior Full Stack Engineer"
            );
            competencyFrameworkRepository.save(f1);

            if (javaS != null) {
                FrameworkSkillRequirement req1 = new FrameworkSkillRequirement();
                req1.setFramework(f1);
                req1.setSkill(javaS);
                req1.setRequiredProficiency(8);
                req1.setNotes("Virtual threads, Spring Boot 4 microservices, and security");
                requirementRepository.save(req1);
            }
            if (angularS != null) {
                FrameworkSkillRequirement req2 = new FrameworkSkillRequirement();
                req2.setFramework(f1);
                req2.setSkill(angularS);
                req2.setRequiredProficiency(7);
                req2.setNotes("Angular 20 Signals, reactive forms, and state management");
                requirementRepository.save(req2);
            }
        }
    }
}

package com.skillsphere.skill.controller;

import com.skillsphere.skill.entity.CourseEnrollment;
import com.skillsphere.skill.security.AuthorizationService;
import com.skillsphere.skill.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/learning/enrollments")
@PreAuthorize("hasAnyRole('EMPLOYEE','STUDENT','HR_ADMIN','MANAGER')")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final AuthorizationService authService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService, AuthorizationService authService) {
        this.enrollmentService = enrollmentService;
        this.authService = authService;
    }

    @GetMapping
    public List<CourseEnrollment> getMyEnrollments() {
        if (authService.isEmployee()) {
            return enrollmentService.getEnrollmentsByEmployee(authService.getCurrentEmployeeId());
        } else if (authService.isStudent()) {
            return enrollmentService.getEnrollmentsByStudent(authService.getCurrentStudentId());
        }
        return List.of();
    }
}


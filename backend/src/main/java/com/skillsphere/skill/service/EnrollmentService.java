package com.skillsphere.skill.service;

import com.skillsphere.skill.entity.CourseEnrollment;
import com.skillsphere.skill.repository.CourseEnrollmentRepository;
import com.skillsphere.skill.security.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final CourseEnrollmentRepository repository;
    private final AuthorizationService authService;

    @Autowired
    public EnrollmentService(CourseEnrollmentRepository repository, AuthorizationService authService) {
        this.repository = repository;
        this.authService = authService;
    }

    public List<CourseEnrollment> getEnrollmentsByEmployee(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public List<CourseEnrollment> getEnrollmentsByStudent(Long studentId) {
        return repository.findByStudentId(studentId);
    }
}


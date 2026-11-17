package com.skillsphere.skill.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.skillsphere.skill.entity.CourseEnrollment;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
    @Query("SELECT c FROM CourseEnrollment c WHERE c.employee.id = :employeeId")
    List<CourseEnrollment> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT c FROM CourseEnrollment c WHERE c.student.id = :studentId")
    List<CourseEnrollment> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT c FROM CourseEnrollment c WHERE c.course.id = :courseId AND c.employee.id = :employeeId")
    Optional<CourseEnrollment> findByCourseIdAndEmployeeId(@Param("courseId") Long courseId, @Param("employeeId") Long employeeId);

    @Query("SELECT c FROM CourseEnrollment c WHERE c.course.id = :courseId AND c.student.id = :studentId")
    Optional<CourseEnrollment> findByCourseIdAndStudentId(@Param("courseId") Long courseId, @Param("studentId") Long studentId);

    long countByStatus(String status);
}

package com.skillsphere.skill.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.skillsphere.skill.entity.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    @Query("SELECT j FROM JobApplication j WHERE j.employee.id = :employeeId")
    List<JobApplication> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT j FROM JobApplication j WHERE j.student.id = :studentId")
    List<JobApplication> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT j FROM JobApplication j WHERE j.job.id = :jobId AND j.employee.id = :employeeId")
    Optional<JobApplication> findByJobIdAndEmployeeId(@Param("jobId") Long jobId, @Param("employeeId") Long employeeId);

    @Query("SELECT j FROM JobApplication j WHERE j.job.id = :jobId AND j.student.id = :studentId")
    Optional<JobApplication> findByJobIdAndStudentId(@Param("jobId") Long jobId, @Param("studentId") Long studentId);
}

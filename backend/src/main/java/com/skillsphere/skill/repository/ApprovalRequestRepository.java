package com.skillsphere.skill.repository;

import com.skillsphere.skill.entity.ApprovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Long> {
    Optional<ApprovalRequest> findByRequestId(UUID requestId);
    List<ApprovalRequest> findByStatus(String status);
    List<ApprovalRequest> findByEmployeeId(Long employeeId);
    List<ApprovalRequest> findAllByOrderBySubmittedAtDesc();
}

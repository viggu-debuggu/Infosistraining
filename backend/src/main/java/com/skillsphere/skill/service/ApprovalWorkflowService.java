package com.skillsphere.skill.service;

import com.skillsphere.skill.entity.ApprovalRequest;
import com.skillsphere.skill.model.AuditLog;
import com.skillsphere.skill.repository.ApprovalRequestRepository;
import com.skillsphere.skill.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApprovalWorkflowService {

    private final ApprovalRequestRepository approvalRepository;
    private final AuditLogRepository auditLogRepository;

    public ApprovalWorkflowService(ApprovalRequestRepository approvalRepository, AuditLogRepository auditLogRepository) {
        this.approvalRepository = approvalRepository;
        this.auditLogRepository = auditLogRepository;
    }

    public List<ApprovalRequest> getAllRequests() {
        return approvalRepository.findAllByOrderBySubmittedAtDesc();
    }

    public List<ApprovalRequest> getPendingRequests() {
        return approvalRepository.findByStatus("PENDING");
    }

    public Optional<ApprovalRequest> getById(Long id) {
        return approvalRepository.findById(id);
    }

    @Transactional
    public ApprovalRequest submitRequest(ApprovalRequest request) {
        if (request.getRequestId() == null) {
            request.setRequestId(UUID.randomUUID());
        }
        request.setStatus("PENDING");
        request.setSubmittedAt(Instant.now());
        ApprovalRequest saved = approvalRepository.save(request);

        // Record in immutable audit trail
        AuditLog audit = new AuditLog();
        audit.setEntity("ApprovalRequest");
        audit.setAction("APPROVAL_SUBMITTED: " + saved.getRequestType() + " for " + saved.getEmployeeName());
        audit.setUsername(saved.getEmployeeName() != null ? saved.getEmployeeName() : "employee");
        audit.setTimestamp(Instant.now());
        auditLogRepository.save(audit);

        return saved;
    }

    @Transactional
    public ApprovalRequest approveRequest(Long id, String reviewer, String notes) {
        ApprovalRequest request = approvalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Approval request not found: " + id));
        request.setStatus("APPROVED");
        request.setReviewedBy(reviewer != null ? reviewer : "HR Administrator");
        request.setReviewedAt(Instant.now());
        request.setReviewNotes(notes);
        ApprovalRequest updated = approvalRepository.save(request);

        // Record in immutable audit trail
        AuditLog audit = new AuditLog();
        audit.setEntity("ApprovalRequest");
        audit.setAction("APPROVAL_APPROVED: " + updated.getRequestType() + " for " + updated.getEmployeeName() + " by " + updated.getReviewedBy());
        audit.setUsername(updated.getReviewedBy());
        audit.setTimestamp(Instant.now());
        auditLogRepository.save(audit);

        return updated;
    }

    @Transactional
    public ApprovalRequest rejectRequest(Long id, String reviewer, String notes) {
        ApprovalRequest request = approvalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Approval request not found: " + id));
        request.setStatus("REJECTED");
        request.setReviewedBy(reviewer != null ? reviewer : "HR Administrator");
        request.setReviewedAt(Instant.now());
        request.setReviewNotes(notes);
        ApprovalRequest updated = approvalRepository.save(request);

        // Record in immutable audit trail
        AuditLog audit = new AuditLog();
        audit.setEntity("ApprovalRequest");
        audit.setAction("APPROVAL_REJECTED: " + updated.getRequestType() + " for " + updated.getEmployeeName() + " by " + updated.getReviewedBy());
        audit.setUsername(updated.getReviewedBy());
        audit.setTimestamp(Instant.now());
        auditLogRepository.save(audit);

        return updated;
    }
}

package com.skillsphere.skill.controller;

import com.skillsphere.skill.entity.ApprovalRequest;
import com.skillsphere.skill.service.ApprovalWorkflowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/approvals")
@CrossOrigin(origins = "*")
public class ApprovalWorkflowController {

    private final ApprovalWorkflowService approvalService;

    public ApprovalWorkflowController(ApprovalWorkflowService approvalService) {
        this.approvalService = approvalService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'EMPLOYEE', 'STUDENT')")
    public List<ApprovalRequest> getRequests(@RequestParam(required = false) String status) {
        if ("PENDING".equalsIgnoreCase(status)) {
            return approvalService.getPendingRequests();
        }
        return approvalService.getAllRequests();
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'EMPLOYEE', 'STUDENT')")
    public List<ApprovalRequest> getPending() {
        return approvalService.getPendingRequests();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'EMPLOYEE', 'STUDENT')")
    public ApprovalRequest submitRequest(@RequestBody ApprovalRequest request) {
        return approvalService.submitRequest(request);
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<ApprovalRequest> approveRequest(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body,
            Authentication auth) {
        String reviewer = auth != null ? auth.getName() : "HR Administrator";
        String notes = body != null ? body.getOrDefault("notes", "Approved by HR Admin") : "Approved by HR Admin";
        return ResponseEntity.ok(approvalService.approveRequest(id, reviewer, notes));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<ApprovalRequest> rejectRequest(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body,
            Authentication auth) {
        String reviewer = auth != null ? auth.getName() : "HR Administrator";
        String notes = body != null ? body.getOrDefault("notes", "Rejected by HR Admin") : "Rejected by HR Admin";
        return ResponseEntity.ok(approvalService.rejectRequest(id, reviewer, notes));
    }
}

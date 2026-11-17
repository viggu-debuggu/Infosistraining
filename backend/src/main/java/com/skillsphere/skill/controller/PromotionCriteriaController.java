package com.skillsphere.skill.controller;

import com.skillsphere.skill.entity.PromotionCriteria;
import com.skillsphere.skill.service.PromotionCriteriaService;
import com.skillsphere.skill.security.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/career/promotion-criteria")
@PreAuthorize("hasAnyRole('HR_ADMIN','MANAGER','EMPLOYEE','STUDENT')")
@CrossOrigin(origins = "*")
public class PromotionCriteriaController {

    private final PromotionCriteriaService promotionCriteriaService;
    private final AuthorizationService authService;

    @Autowired
    public PromotionCriteriaController(PromotionCriteriaService promotionCriteriaService, AuthorizationService authService) {
        this.promotionCriteriaService = promotionCriteriaService;
        this.authService = authService;
    }

    @GetMapping
    public List<PromotionCriteria> getAllCriteria() {
        return promotionCriteriaService.getAllCriteria();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PromotionCriteria saveCriteria(@RequestBody PromotionCriteria criteria) {
        // Ensure employeeId matches current user if employee role
        if (authService.isEmployee() && !authService.getCurrentEmployeeId().equals(criteria.getEmployeeId())) {
            throw new org.springframework.security.access.AccessDeniedException("Employees can only save their own criteria");
        }
        return promotionCriteriaService.saveCriteria(criteria);
    }

    @GetMapping("/employee/{empId}")
    public List<PromotionCriteria> getByEmployee(@PathVariable Long empId) {
        authService.requireEmployeeAccess(empId);
        return promotionCriteriaService.getCriteriaByEmployee(empId);
    }
}

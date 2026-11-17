package com.skillsphere.skill.service;

import com.skillsphere.skill.entity.PromotionCriteria;
import com.skillsphere.skill.repository.PromotionCriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionCriteriaService {

    private final PromotionCriteriaRepository repository;

    @Autowired
    public PromotionCriteriaService(PromotionCriteriaRepository repository) {
        this.repository = repository;
    }

    public PromotionCriteria saveCriteria(PromotionCriteria criteria) {
        return repository.save(criteria);
    }

    public List<PromotionCriteria> getCriteriaByEmployee(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public List<PromotionCriteria> getAllCriteria() {
        return repository.findAll();
    }
}

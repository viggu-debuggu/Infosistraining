package com.skillsphere.skill.repository;

import com.skillsphere.skill.entity.PromotionCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionCriteriaRepository extends JpaRepository<PromotionCriteria, Long> {
    List<PromotionCriteria> findByEmployeeId(Long employeeId);
}


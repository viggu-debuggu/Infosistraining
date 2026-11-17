package com.skillsphere.skill.repository;

import com.skillsphere.skill.model.SkillCompetency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillCompetencyRepository extends JpaRepository<SkillCompetency, Long> {
    List<SkillCompetency> findByEmployeeId(Long employeeId);
}


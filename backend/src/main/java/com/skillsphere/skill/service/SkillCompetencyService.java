package com.skillsphere.skill.service;

import com.skillsphere.skill.model.SkillCompetency;
import com.skillsphere.skill.repository.SkillCompetencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillCompetencyService {

    private final SkillCompetencyRepository repository;

    @Autowired
    public SkillCompetencyService(SkillCompetencyRepository repository) {
        this.repository = repository;
    }

    public List<SkillCompetency> getCompetencyByEmployee(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }
}


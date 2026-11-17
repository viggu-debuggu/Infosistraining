package com.skillsphere.skill.controller;

import com.skillsphere.skill.model.SkillCompetency;
import com.skillsphere.skill.service.SkillCompetencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills/competency")
@PreAuthorize("hasAnyRole('HR_ADMIN','MANAGER','EMPLOYEE','STUDENT')")
public class SkillCompetencyController {

    private final SkillCompetencyService skillCompetencyService;

    @Autowired
    public SkillCompetencyController(SkillCompetencyService skillCompetencyService) {
        this.skillCompetencyService = skillCompetencyService;
    }

    @GetMapping("/employee/{empId}")
    public List<SkillCompetency> getCompetencyByEmployee(@PathVariable Long empId) {
        return skillCompetencyService.getCompetencyByEmployee(empId);
    }
}


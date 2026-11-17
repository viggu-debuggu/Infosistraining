
package com.skillsphere.skill.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skillsphere.skill.entity.Skill;
import com.skillsphere.skill.repository.SkillRepository;

@Service
@Transactional
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Cacheable("skills")
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Optional<Skill> getSkillById(Long id) {
        return skillRepository.findById(id);
    }

    @CacheEvict(value = "skills", allEntries = true)
    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @CacheEvict(value = "skills", allEntries = true)
    public Skill updateSkill(Long id, Skill updatedSkill) {
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Skill not found with id: " + id));

        existingSkill.setName(updatedSkill.getName());
        existingSkill.setCategory(updatedSkill.getCategory());
        existingSkill.setDescription(updatedSkill.getDescription());

        return skillRepository.save(existingSkill);
    }

    @CacheEvict(value = "skills", allEntries = true)
    public void deleteSkill(Long id) {
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Skill not found with id: " + id));

        skillRepository.delete(existingSkill);
    }
}

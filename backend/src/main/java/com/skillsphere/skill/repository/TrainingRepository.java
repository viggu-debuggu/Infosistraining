package com.skillsphere.skill.repository;

import com.skillsphere.skill.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    Optional<Training> findByTrainId(UUID trainId);
    List<Training> findByStatus(String status);
    List<Training> findByTrainer(String trainer);
}

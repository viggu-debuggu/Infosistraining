package com.skillsphere.skill.service;

import com.skillsphere.skill.entity.Training;
import com.skillsphere.skill.repository.TrainingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    public Optional<Training> getTrainingById(Long id) {
        return trainingRepository.findById(id);
    }

    public Optional<Training> getTrainingByTrainId(UUID trainId) {
        return trainingRepository.findByTrainId(trainId);
    }

    @Transactional
    public Training scheduleTraining(Training training) {
        if (training.getTrainId() == null) {
            training.setTrainId(UUID.randomUUID());
        }
        if (training.getDate() == null) {
            training.setDate(LocalDateTime.now().plusDays(7));
        }
        return trainingRepository.save(training);
    }

    @Transactional
    public Training enrollParticipant(Long trainingId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training not found: " + trainingId));
        if (training.getEnrolledCount() == null) {
            training.setEnrolledCount(0);
        }
        training.setEnrolledCount(training.getEnrolledCount() + 1);
        return trainingRepository.save(training);
    }

    @Transactional
    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }
}

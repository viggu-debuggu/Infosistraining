package com.skillsphere.skill.controller;

import com.skillsphere.skill.entity.Training;
import com.skillsphere.skill.service.TrainingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trainings")
@CrossOrigin(origins = "*")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Training> getTrainingById(@PathVariable Long id) {
        return trainingService.getTrainingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uuid/{trainId}")
    public ResponseEntity<Training> getTrainingByTrainId(@PathVariable UUID trainId) {
        return trainingService.getTrainingByTrainId(trainId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'EMPLOYEE', 'STUDENT')")
    @ResponseStatus(HttpStatus.CREATED)
    public Training scheduleTraining(@RequestBody Training training) {
        return trainingService.scheduleTraining(training);
    }

    @PostMapping("/{id}/enroll")
    @PreAuthorize("hasAnyRole('HR_ADMIN', 'EMPLOYEE', 'STUDENT')")
    public Training enrollInTraining(@PathVariable Long id) {
        return trainingService.enrollParticipant(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }
}

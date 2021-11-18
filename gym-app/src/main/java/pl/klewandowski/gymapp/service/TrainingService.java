package pl.klewandowski.gymapp.service;

import pl.klewandowski.gymapp.model.Exercise;
import pl.klewandowski.gymapp.model.Training;

import java.util.List;
import java.util.Set;

public interface TrainingService {

    List<Training> getAllTrainings();

    Training addTraining(Training training, String username);

    void deleteTraining(long id);

    Training updateTraining(long id, Training training);

    Training getTrainingById(long id);

    boolean checkIfOwner(long id, String username);

    Training addExercise(long id, Set<Exercise> exerciseSet);
}

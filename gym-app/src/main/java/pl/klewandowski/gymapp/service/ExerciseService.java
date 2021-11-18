package pl.klewandowski.gymapp.service;

import pl.klewandowski.gymapp.model.Exercise;

import java.util.List;

public interface ExerciseService {
    List<Exercise> getAllExercises();

    Exercise addExercise(Exercise exercise, String username);

    void deleteExercise(long id);

    Exercise updateExercise(long id, Exercise exercise);

    Exercise getExerciseById(long id);
}

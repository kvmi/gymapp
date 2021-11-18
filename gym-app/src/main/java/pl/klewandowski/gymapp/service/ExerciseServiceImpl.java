package pl.klewandowski.gymapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.klewandowski.gymapp.model.Exercise;
import pl.klewandowski.gymapp.repository.ExerciseRepository;

import java.util.Collections;
import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }


    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public Exercise addExercise(Exercise exercise, String username) {
        exercise.setUsername(username);
        return exerciseRepository.save(exercise);
    }

    @Override
    public void deleteExercise(long id) {
        exerciseRepository.deleteById(id);
    }

    @Override
    public Exercise updateExercise(long id, Exercise exercise) {

        Exercise exerciseToUpdate = getExerciseById(id);
        exerciseToUpdate.setName(exercise.getName());
        exerciseToUpdate.setImagePath(exercise.getImagePath());
        exerciseToUpdate.setDescription(exercise.getDescription());
        exerciseToUpdate.setLevel(exercise.getLevel());
        return exerciseRepository.save(exerciseToUpdate);
    }

    @Override
    public Exercise getExerciseById(long id) {
        return exerciseRepository.findById(id).orElse(null);
    }
}

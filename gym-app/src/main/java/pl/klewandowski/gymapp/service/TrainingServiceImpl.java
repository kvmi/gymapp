package pl.klewandowski.gymapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.klewandowski.gymapp.model.Exercise;
import pl.klewandowski.gymapp.model.Training;
import pl.klewandowski.gymapp.repository.TrainingRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TrainingServiceImpl implements TrainingService {

    private TrainingRepository trainingRepository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public Training addTraining(Training training, String username) {
        training.setUsername(username);
        return trainingRepository.save(training);
    }

    @Override
    public void deleteTraining(long id) {
        trainingRepository.deleteById(id);
    }

    @Override
    public Training updateTraining(long id, Training training) {
        Training trainingToUpdate = getTrainingById(id);
        trainingToUpdate.setName(training.getName());
        trainingToUpdate.setExercises(training.getExercises());
        trainingToUpdate.setUsername(training.getUsername());

        return trainingRepository.save(trainingToUpdate);
    }

    @Override
    public Training getTrainingById(long id) {

        return trainingRepository.findById(id).orElse(null);
    }

    @Override
    public boolean checkIfOwner(long id, String username) {
        Training trainingById = getTrainingById(id);
        return username.equals(trainingById.getUsername());
    }

    @Override
    public Training addExercise(long id, Set<Exercise> exerciseSet) {
        Training training = trainingRepository.findById(id).orElse(null);
        assert training != null;
        Set<Exercise> exercises = training.getExercises();
        exercises.add(exerciseSet.iterator().next());
        training.setExercises(exercises);
        return trainingRepository.save(training);
    }
}

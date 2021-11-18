package pl.klewandowski.gymapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.klewandowski.gymapp.model.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise getExerciseByName(String name);
}

package pl.klewandowski.gymapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.klewandowski.gymapp.model.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
}

package pl.klewandowski.gymapp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    private String username;

    @OneToMany
    private Set<Exercise> exercises;

    public Training() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String exercisesToString() {
        List<String> exerciseList = new ArrayList<>();
        getExercises().iterator().forEachRemaining(exercise -> {
            exerciseList.add(exercise.getName());
        });
        return String.join(", ", exerciseList);
    }
}

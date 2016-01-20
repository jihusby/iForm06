package no.husby.iform06.model;

import java.util.ArrayList;
import java.util.List;

public class Day {
    private List<Exercise> exercises;
    private String name;

    public Day(String name) {
        this.name = name;
        exercises = new ArrayList<Exercise>();
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public String getName() {
        return name;
    }
    public List<Exercise> getExercises() {
        return exercises;
    }

    public List<String> getExerciseNames() {
        List<String> exerciseNames = new ArrayList<String>(exercises.size());
        int i=0;
        for(Exercise e : exercises) {
            exerciseNames.add(i, e.getName());
            i++;
        }
        return exerciseNames;
    }
}

package no.husby.iform06.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Day {
    @SerializedName("e")
    private List<Exercise> exercises;
    @SerializedName("n")
    private String name;
    private int id;

    public Day(int id, String name) {
        this.id = id;
        this.name = name;
        exercises = new ArrayList<>();
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
        List<String> exerciseNames = new ArrayList<>(exercises.size());
        int i=0;
        for(Exercise e : exercises) {
            exerciseNames.add(i, e.getName() + " " + e.getSetCount() + " X " + e.getRepCountMin());
            i++;
        }
        return exerciseNames;
    }

    public Exercise getExerciseAt(int index) {
        return exercises.get(index);
    }

    public int getIndex(int exerciseId) {
        for (Exercise exercise : exercises) {
            if (exerciseId == exercise.getId()) {
                return exercises.indexOf(exercise);
            }
        }
        return -1;
    }

    public Exercise getExercise(int exerciseId) {
        for (Exercise exercise : exercises) {
            if (exerciseId == exercise.getId()) {
                return exercise;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }
}

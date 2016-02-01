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
            exerciseNames.add(i, e.getName() + " " + e.getSetCount() + " X " + e.getRepCountMin());
            i++;
        }
        return exerciseNames;
    }

    public Exercise getExercise(int index) {
        return exercises.get(index);
    }

    public int getIndex(Exercise e) {
        if (e != null) {
            int i = 0;

            for (Exercise exercise : exercises) {
                if (e.getId() == exercise.getId()) {
                    return i;

                }
                i++;
            }
            return -1;
        }
        return -1;
    }

    public int getId() {
        return id;
    }
}

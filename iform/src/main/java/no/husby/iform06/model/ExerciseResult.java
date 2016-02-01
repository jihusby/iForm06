package no.husby.iform06.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ExerciseResult {

    @SerializedName("d")
    private final long datePerformed;
    @SerializedName("p")
    private final int pause;
    @SerializedName("eid")
    private int exerciseId;
    @SerializedName("w")
    private double weight;
    @SerializedName("s")
    private int[] reps;

    public ExerciseResult(Exercise exercise) {
        exerciseId = exercise.getId();
        datePerformed = new Date().getTime();
        reps = new int[exercise.getSetCount()];
        pause = exercise.getPause();
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    public void setRepResult(int reps, int repIndex) {
        this.reps[repIndex] = reps;
    }

    public long getDatePerformed() {
        return datePerformed;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public double getWeight() {
        return weight;
    }

    public int[] getReps() {
        return reps;
    }

    public int getRepAt(int index) {
        return reps[index];
    }
}

package no.husby.iform06.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ExerciseLogItem {

    @SerializedName("date")
    private final Date date;
    @SerializedName("weight")
    private float weight;
    @SerializedName("reps")
    private int[] reps;

    public ExerciseLogItem(float weight, int[] reps) {
        this.date = new Date();
        this.weight = weight;
        this.reps = reps;
    }

    public Date getDate() {
        return date;
    }

    public float getWeight() {
        return weight;
    }

    public int[] getReps() {
        return reps;
    }

    public int getSumReps() {
        int sum = 0;
        for(int rep: reps) {
            sum += rep;
        }
        return sum;
    }

}

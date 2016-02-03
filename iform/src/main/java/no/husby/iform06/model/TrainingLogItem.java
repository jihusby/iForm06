package no.husby.iform06.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrainingLogItem {

    @SerializedName("dayid")
    private final int dayId;
    @SerializedName("exid")
    private int exerciseId;
    @SerializedName("exn")
    private String exerciseName;

    private List<ExerciseLogItem> exerciseLogItems;
    private String filename;

    public TrainingLogItem(String exerciseName, int exerciseId, int dayId) {
        this.exerciseName = exerciseName;
        this.dayId = dayId;
        this.exerciseId = exerciseId;
        exerciseLogItems = new ArrayList<>();
    }

    public void addExerciseLogItem(ExerciseLogItem item) {
        exerciseLogItems.add(item);
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public float[] getWeightProgression() {
        float[] weightList = new float[exerciseLogItems.size()];
        int i = 0;
        for(ExerciseLogItem item: exerciseLogItems) {
            weightList[i] = item.getWeight();
            i++;
        }
        return weightList;
    }

    public float[] getWorkProgression() {
        float[] progression = new float[exerciseLogItems.size()];
        int i = 0;
        for(ExerciseLogItem item: exerciseLogItems) {
            progression[i] = item.getWeight() * item.getSumReps();
            i++;
        }
        return progression;
    }

    public int getDayId() {
        return dayId;
    }

    @Override
    public String toString() {
        String res = "";
        for(ExerciseLogItem item: exerciseLogItems) {
            res += ((int) (item.getWeight() * item.getSumReps())) + " ";
        }
        return String.valueOf(this.exerciseName + ": " + res);
    }

}

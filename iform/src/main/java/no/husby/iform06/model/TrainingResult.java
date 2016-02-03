package no.husby.iform06.model;

import com.google.gson.annotations.SerializedName;
import no.husby.iform06.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TrainingResult {

    @SerializedName("dp")
    private final long datePerformed;
    @SerializedName("did")
    private int dayId;
    @SerializedName("rs")
    private List<ExerciseResult> exerciseResults;

    public TrainingResult(Day day, long datePerformed) {
        super();
        this.datePerformed = datePerformed;
        this.dayId = day.getId();
        exerciseResults = new ArrayList<ExerciseResult>();
        for(Exercise e: day.getExercises()) {
            exerciseResults.add(new ExerciseResult(e));
        }
    }

    public long getDatePerformed() {
        return datePerformed;
    }

    public int getDayId() {
        return dayId;
    }

    public void setResultAt(int index, ExerciseResult exerciseResult) {
        exerciseResults.set(index, exerciseResult);
    }

    public ExerciseResult getExerciseResultByIndex(int index) {
        return exerciseResults.get(index);
    }

    @Override
    public String toString() {
        return String.valueOf(this.dayId);
    }
}

package no.husby.iform06.model;

import com.google.gson.annotations.SerializedName;
import no.husby.iform06.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TrainingResult {

    @SerializedName("did")
    private int dayId;
    @SerializedName("dt")
    private String date;
    @SerializedName("rs")
    private List<ExerciseResult> exerciseResults;

    public TrainingResult(Day day) {
        setDateString();
        this.dayId = day.getId();
        exerciseResults = new ArrayList<ExerciseResult>();
        for(Exercise e: day.getExercises()) {
            exerciseResults.add(new ExerciseResult(e));
        }
    }

    private void setDateString() {
        date = Utils.getDateString();
    }

    public int getDayId() {
        return dayId;
    }

    public String getDateString() {
        return date;
    }

    public void setResultAt(int index, ExerciseResult exerciseResult) {
        exerciseResults.set(index, exerciseResult);
    }

    public ExerciseResult getExerciseResult(int index) {
        return exerciseResults.get(index);
    }

    public ExerciseResult getExerciseResult(Exercise exercise) {
        if(null!=exercise) {
            int i = 0;
            for(ExerciseResult r: exerciseResults) {
                if(r!=null){
                    if(r.getExerciseId() == exercise.getId()) {
                        return exerciseResults.get(i);
                    }
                }
                i++;
            }
        }
        return null;
    }

    public List<ExerciseResult> getExerciseResults() {
        return exerciseResults;
    }
}

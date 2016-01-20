package no.husby.iform06.model;

import java.util.ArrayList;
import java.util.List;

public class TrainingResult {

    private int dayId;
    private long date;
    private List<Result> results;


    public TrainingResult() {
        results = new ArrayList<Result>();
    }
}

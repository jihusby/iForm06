package no.husby.iform06.model;

import com.google.gson.annotations.SerializedName;

public class Exercise {

    @SerializedName("ps")
    private final int pause;
    @SerializedName("tr")
    private final String track;
    @SerializedName("id")
    private int id;
    @SerializedName("n")
    private String name;
    @SerializedName("s")
    private int setCount;
    @SerializedName("r")
    private int repCountMin;
    private int repCountMax;

    public Exercise(int id, String name, int set, int repCountMin, int repCountMax, int pause, String track) {
        this.id = id;
        this.name = name;
        this.setCount = set;
        this.repCountMin = repCountMin;
        this.repCountMax = repCountMax;
        this.pause = pause;
        this.track = track;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRepCountMin() {
        return repCountMin;
    }

    public int getRepCountMax() {
        return repCountMax;
    }

    public int getSetCount() {
        return setCount;
    }

    public int getPause() {
        return pause;
    }

    public String getTrack() {
        return track;
    }

}

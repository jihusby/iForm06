package no.husby.iform06.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Program {

    @SerializedName("n")
    private String name;
    @SerializedName("d")
    private List<Day> days;
    @SerializedName("dsc")
    private String description;
    private String userId;

    public Program(String name) {
        this.name = name;
        days = new ArrayList<Day>();
    }

    public String getName() {
        return name;
    }

    public Day getDay(int index) {
        return days.get(index);
    }

    public List<Day> getDays() {
        return days;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}

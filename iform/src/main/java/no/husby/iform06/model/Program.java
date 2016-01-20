package no.husby.iform06.model;

import java.util.ArrayList;
import java.util.List;

public class Program {

    private String name;
    private List<Day> days;
    private String description;

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
}

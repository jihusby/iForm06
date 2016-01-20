package no.husby.iform06.model;

public class Exercise {

    private int id;
    private String name;
    private int set;
    private int reps;

    public Exercise(int id, String name, int set, int reps) {
        this.id = id;
        this.name = name;
        this.set = set;
        this.reps = reps;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getReps() {
        return reps;
    }

    public int getSet() {
        return set;
    }
}

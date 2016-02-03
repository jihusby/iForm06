package no.husby.iform06.data;

import android.content.Context;
import android.content.res.AssetManager;
import no.husby.iform06.model.Day;
import no.husby.iform06.model.Exercise;
import no.husby.iform06.model.Program;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProgramReader {

    private final AssetManager assetManager;
    private List<Program> programs;

    public ProgramReader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    public Program getProgram(int id) {

        // TODO: Create programs from assets folder
        //Program programs = new Program(loadJSONFromAsset(assetManager));
        programs = new ArrayList<Program>();
        programs.add(id, createMockProgram());
        return programs.get(0);
    }
/*
    private static String loadJSONFromAsset(AssetManager assetManager) {
        String json = null;
        try {
            InputStream is = assetManager.open("name.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
*/

    private Program createMockProgram() {
        int kortPause = 5;
        int langPause = 10;
        String track = "";
        String track1 = "spotify:track:3cwDSDzTiWr5H5xMQhQ6Mx";
        String track2 = "spotify:track:2WPSy9E6Tet3GzfneXoCfq";
        Program program = new Program("My demo program");
        Day day1 = new Day(1, "Dag 1");
        Day day2 = new Day(2, "Dag 2");
        Day day3 = new Day(3, "Dag 3");
        Day day4 = new Day(4, "Dag 4");
        Day day5 = new Day(5, "Dag 5");
        day1.addExercise(new Exercise(1, "Markløft", 3, 3, 5, langPause, track1));
        day1.addExercise(new Exercise(2, "Pullups", 2, 6, 10, langPause, track2));
        day1.addExercise(new Exercise(3, "Assisted chins", 2, 6, 10, langPause, track));
        day1.addExercise(new Exercise(4, "Benkpress", 3, 3, 5, langPause, track));
        day1.addExercise(new Exercise(5, "Dips", 2, 6, 10, langPause, track));
        day1.addExercise(new Exercise(6, "Arnoldpress", 3, 6, 10, langPause, track));
        day1.addExercise(new Exercise(7, "Hammercurl", 3, 6, 10, langPause, track));
        day1.addExercise(new Exercise(8, "Franskpress", 3, 6, 10, langPause, track));

        day2.addExercise(new Exercise(1, "Knebøy", 3, 3, 5, langPause, track));
        day2.addExercise(new Exercise(2, "Beinpress", 2, 6, 10, langPause, track));
        day2.addExercise(new Exercise(3, "Utspark", 2, 6, 10, langPause, track));
        day2.addExercise(new Exercise(4, "Strake markløft", 3, 5, 8, langPause, track));
        day2.addExercise(new Exercise(5, "Dips", 2, 6, 10, langPause, track));
        day2.addExercise(new Exercise(6, "Liggende lårcurl", 2, 6, 10, langPause, track));
        day2.addExercise(new Exercise(7, "Stående tåhev", 2, 6, 10, langPause, track));
        day2.addExercise(new Exercise(8, "Sittende tåhev", 2, 6, 10, langPause, track));

        program.setDescription("5-split program, brukes høsten 2015-våren 2016.");
        program.getDays().add(day1);
        program.getDays().add(day2);
        program.getDays().add(day3);
        program.getDays().add(day4);
        program.getDays().add(day5);
        return program;
    }

}

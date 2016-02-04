package no.husby.iform06.data;

import android.app.Activity;
import android.util.Log;
import com.google.gson.Gson;
import no.husby.iform06.model.Day;
import no.husby.iform06.model.Exercise;
import no.husby.iform06.model.Program;
import no.husby.iform06.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MockUps {

    private void printFiles(Activity a) {
        File files = a.getFilesDir();
        Log.d("FILES", "FILES");

    }

    private Program createMockProgram() {
        int kortPause = 5;
        int langPause = 10;
        String track = "";
        String track1 = "spotify:track:3cwDSDzTiWr5H5xMQhQ6Mx";
        String track2 = "spotify:track:2WPSy9E6Tet3GzfneXoCfq";
        Program program = new Program("Mitt program");
        program.setUserId("10153306651711301");
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

        program.setDescription("5-split program, brukes høsten 2015 - våren 2016.");
        program.getDays().add(day1);
        program.getDays().add(day2);
        program.getDays().add(day3);
        program.getDays().add(day4);
        program.getDays().add(day5);
        return program;
    }

    public void saveJson(Activity a, String filename, Program item) {

        String result = new Gson().toJson(item);
        try {
            FileOutputStream fileout = a.openFileOutput(filename, a.MODE_PRIVATE);
            Utils.writeJsonToOutputStream(result, fileout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

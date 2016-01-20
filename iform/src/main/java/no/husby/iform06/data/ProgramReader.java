package no.husby.iform06.data;

import android.content.res.AssetManager;
import no.husby.iform06.model.Day;
import no.husby.iform06.model.Exercise;
import no.husby.iform06.model.Program;

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
        Program program = new Program("My demo programs");
        Day day1 = new Day("Dag 1");
        Day day2 = new Day("Dag 2");
        day1.addExercise(new Exercise(1, "Knebøy"));
        day1.addExercise(new Exercise(2, "Benkpress"));
        day1.addExercise(new Exercise(3, "Dips"));
        day2.addExercise(new Exercise(1, "Markløft"));
        day2.addExercise(new Exercise(2, "Pullups"));
        day2.addExercise(new Exercise(3, "Sittende roing"));

        program.setDescription("This is a demo program, containing a couple of exercises, a couple of days etc.");
        program.getDays().add(day1);
        program.getDays().add(day2);
        return program;
    }

}

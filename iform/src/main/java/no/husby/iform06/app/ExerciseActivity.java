package no.husby.iform06.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;
import android.support.v4.app.FragmentManager;
import no.husby.iform06.model.Day;
import no.husby.iform06.model.Exercise;
import no.husby.iform06.model.ExerciseResult;
import no.husby.iform06.model.TrainingResult;
import no.husby.iform06.utils.Direction;
import no.husby.iform06.utils.Utils;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import static no.husby.iform06.app.R.layout.activity_exercise;

@ContentView(activity_exercise)
public class ExerciseActivity extends RoboFragmentActivity implements ExerciseResultFragment.OnExerciseChangedListener {

    private Day day;
    private TrainingResult trainingResult;
    private Exercise[] allExerciseResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String dayString = getIntent().getStringExtra("day");
        if (dayString != null) {
            day = new Gson().fromJson(dayString, Day.class);
            String fileName = day.getId() + "_" + Utils.getDateString() + ".json";
            trainingResult = getFromStorage(fileName);
            ExerciseResult exerciseResult;
            if(trainingResult == null || trainingResult.getExerciseResults().size() != day.getExercises().size()) {
                trainingResult = new TrainingResult(day);
                exerciseResult = trainingResult.getExerciseResult(0);
            }else{
                exerciseResult = trainingResult.getExerciseResult(day.getExercise(0));
            }

            setFragment(day.getExercise(0), exerciseResult, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onExerciseChanged(Exercise exercise, ExerciseResult exerciseResult, Direction direction) {
        trainingResult.setResultAt(day.getIndex(exercise), exerciseResult);
        setFragment(exercise, exerciseResult, direction);
    }

    private Exercise[] getAllResults(Exercise exercise) {
        return new Exercise[0];
    }

    public void setFragment(Exercise exercise, ExerciseResult exerciseResult, Enum direction) {
        ExerciseResultFragment fragment = new ExerciseResultFragment();
        FragmentManager fm = getSupportFragmentManager();

        int index = getNextIndex(exercise, direction);
        if(index < 0) {
            saveToStorage(day, trainingResult);
        }else if(index >= day.getExercises().size()) {
            saveToStorage(day, trainingResult);
        }
        else {
            exerciseResult = trainingResult.getExerciseResult(day.getExercise(index));
            Bundle b = new Bundle();
            b.putString("exercise", new Gson().toJson(day.getExercise(index)));
            b.putString("exerciseResult", new Gson().toJson(exerciseResult));
            fragment.setArguments(b);
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
       }
    }

    private int getNextIndex(Exercise exercise, Enum direction) {
        int index = 0;
        if(exercise!=null) {
            index = day.getIndex(exercise);
            if (direction == Direction.NEXT) {
                index += 1;
            } else if (direction == Direction.PREV) {
                index -= 1;
            }
        }
        return index;
    }

    public void saveToStorage(Day day, TrainingResult trainingResult) {
        String filename = day.getId() + "_" + trainingResult.getDateString() + ".json";
        FileOutputStream outputStream;
        String result = new Gson().toJson(trainingResult);
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(result.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TrainingResult getFromStorage(String fileName) {

        TrainingResult result = null;
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(fileName);
            int c;
            String resultString="";
            while( (c = inputStream.read()) != -1){
                resultString = resultString + Character.toString((char)c);
            }
            inputStream.close();
            result = new Gson().fromJson(resultString, TrainingResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

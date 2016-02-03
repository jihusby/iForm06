package no.husby.iform06.app;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;
import no.husby.iform06.model.TrainingLogItem;
import no.husby.iform06.model.TrainingResult;
import no.husby.iform06.utils.Utils;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static no.husby.iform06.app.R.layout.activity_exercise;
import static no.husby.iform06.app.R.layout.activity_statistics;

@ContentView(activity_statistics)
public class StatisticsActivity extends RoboFragmentActivity {

    private ArrayList<TrainingLogItem> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();
        Bundle b = new Bundle();
        int day = Integer.parseInt(getIntent().getStringExtra("dayId"));
        results = getAllFromStorage(day);
        b.putString("trainingResults", new Gson().toJson(results));
        StatisticsActivityFragment fragment = new StatisticsActivityFragment();
        fragment.setArguments(b);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
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

    public ArrayList<TrainingLogItem> getAllFromStorage_(int dayId) {
        String[] fileList = fileList();
        for(String filename: fileList) {
            deleteFile(filename);
        }
        return null;
    }

    public ArrayList<TrainingLogItem> getAllFromStorage(int dayId) {
        ArrayList<TrainingLogItem> results = new ArrayList<>();
        try {
            String[] fileList = fileList();
            for(String filename: fileList) {
                if(filename.contains("_day" + dayId) && filename.contains(".json")) {
                    FileInputStream inputStream = openFileInput(filename);
                    String jsonData = Utils.readJsonFromInputStream(inputStream);
                    results.add(new Gson().fromJson(jsonData, TrainingLogItem.class));
                    Log.d("OK", "OK");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

}

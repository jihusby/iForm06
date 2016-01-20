package no.husby.iform06.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.google.gson.Gson;
import no.husby.iform06.model.Day;
import no.husby.iform06.model.Exercise;
import no.husby.iform06.model.Program;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import java.util.List;

import static no.husby.iform06.app.R.layout.activity_day;

@ContentView(activity_day)
public class DayActivity extends RoboActivity {
    private Day day;

    @InjectView(R.id.dayName)
    TextView dayName;

    @InjectView(R.id.exercises)
    ListView exercises;

    @InjectView(R.id.startTraining)
    Button startTraining;

    private View.OnClickListener startTrainingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent exerciseIntent = new Intent(DayActivity.this, ExerciseActivity.class);
            String exercise = "";
            exerciseIntent.putExtra("exercise", exercise);
            startActivity(exerciseIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String target = getIntent().getStringExtra("day");
        if (target != null) {
            Day day = new Gson().fromJson(target, Day.class);
            dayName.setText(day.getName());


            List<String> exerciseNames = day.getExerciseNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, exerciseNames);

            // Assign adapter to ListView
            exercises.setAdapter(adapter);

            // ListView Item Click Listener
            exercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // ListView Clicked item index
                    int itemPosition = position;

                    // ListView Clicked item value
                    String itemValue = (String) exercises.getItemAtPosition(position);

                    // Show Alert
                    Toast.makeText(getApplicationContext(),
                            "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                            .show();
                }

            });
        }

        startTraining.setOnClickListener(startTrainingListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day, menu);
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
}

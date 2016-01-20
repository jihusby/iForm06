package no.husby.iform06.app;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import no.husby.iform06.data.ProgramReader;
import no.husby.iform06.model.Program;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import static no.husby.iform06.app.R.layout.activity_main;

@ContentView(activity_main)
public class MainActivity extends RoboActivity {

    @InjectView(R.id.programName)
    TextView programName;
    @InjectView(R.id.programDescription)
    TextView programDescription;
    @InjectView(R.id.startDay)
    Button startDay;

    private Program program;

    private View.OnClickListener startDayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent dayIntent = new Intent(MainActivity.this, DayActivity.class);
            dayIntent.putExtra("program", new Gson().toJson(program));
            startActivity(dayIntent);
        }
    };
    private AssetManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        manager = getAssets();
        program = new ProgramReader(manager).getProgram(0);
        programName.setText(program.getName());
        programDescription.setText(program.getDescription());
        startDay.setOnClickListener(startDayListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

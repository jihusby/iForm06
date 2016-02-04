package no.husby.iform06.app;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import no.husby.iform06.data.ProgramReader;
import no.husby.iform06.model.Day;
import no.husby.iform06.model.Program;
import no.husby.iform06.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    @InjectView(R.id.linearLayoutButtons)
    LinearLayout linearLayoutButtons;
    @InjectView(R.id.linearLayoutProgram)
    LinearLayout linearLayoutProgram;

    private CallbackManager callbackManager;
    private Program program;
    private AssetManager manager;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;

    private View.OnClickListener startDayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent dayIntent = new Intent(MainActivity.this, DayActivity.class);
            dayIntent.putExtra("program", new Gson().toJson(program));
            startActivity(dayIntent);
        }
    };
    private User user;

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app
        // deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        super.onCreate(savedInstanceState);

        setExerciseProgram();

        // Other app specific specialization

        // Callback registration


        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                setExerciseProgram();
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {

                                JSONObject o = response.getJSONObject();
                                try {

                                    String name = (String) o.get("name");
                                    String id = (String) o.get("id");
                                    user = new User(name, id);
                                    setExerciseProgram();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("LOGIN CANCEL", "LOGIN CANCEL");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("LOGIN ERROR", "LOGIN ERROR");
                exception.printStackTrace();
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                setExerciseProgram();
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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

    public void setExerciseProgram() {
        int containerId = 999;
        LinearLayout container = new LinearLayout(getApplicationContext());
        container.setOrientation(LinearLayout.VERTICAL);

        container.setId(containerId);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        container.setLayoutParams(containerParams);
        if(accessToken != null && user != null) {

            // TODO: Find the user's program
            manager = getAssets();
            program = new ProgramReader(manager).getProgram(this, user);
            programName.setText(program.getName());
            programDescription.setText(program.getDescription());

            if(linearLayoutButtons.findViewById(containerId) == null) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                params.setMargins(0, 10, 0, 0);
                int i = 0;
                for (final Day d : program.getDays()) {
                    Button button = new Button(this);
                    button.setBackground(getResources().getDrawable(R.drawable.mybutton));
                    button.setLayoutParams(params);
                    button.setBottom(20);
                    button.setId(i);
                    button.setTextColor(Color.WHITE);
                    button.setText("Start " + d.getName());

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent dayIntent = new Intent(MainActivity.this, DayActivity.class);
                            dayIntent.putExtra(getResources().getString(R.string.day), new Gson().toJson(d));
                            startActivity(dayIntent);
                        }
                    });
                    container.addView(button);
                    i++;
                }
                linearLayoutButtons.addView(container);
            }
        }else {
            if(linearLayoutButtons!=null) {
                linearLayoutButtons.removeAllViews();
                programName.setText("");
                programDescription.setText("");
            }
        }

    }
}

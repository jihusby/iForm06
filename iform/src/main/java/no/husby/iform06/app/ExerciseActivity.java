package no.husby.iform06.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import com.google.gson.Gson;
import android.support.v4.app.FragmentManager;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.*;
import no.husby.iform06.model.*;
import no.husby.iform06.utils.Direction;
import no.husby.iform06.utils.Utils;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;

import java.io.*;
import java.util.Date;

import static no.husby.iform06.app.R.layout.activity_exercise;


@ContentView(activity_exercise)
public class ExerciseActivity extends RoboFragmentActivity implements ExerciseResultFragment.OnExerciseChangedListener, ConnectionStateCallback, PlayerNotificationCallback {

    private Day day;
    private TrainingResult trainingResult;

    private static final String CLIENT_ID = "4955249cffa6433b951ed93181cef13c";
    private static final String REDIRECT_URI = "abc://iform.no";
    private static final int REQUEST_CODE = 1337;

    private Player mPlayer;
    private String track;
    private Config playerConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String dayString = getIntent().getStringExtra("day");

        authenticateSpotify();

        if (dayString != null) {
            day = new Gson().fromJson(dayString, Day.class);
            trainingResult = new TrainingResult(day, new Date().getTime());
            ExerciseResult firstResult = trainingResult.getExerciseResultByIndex(0);
            openExerciseFragment(firstResult);
        }
    }

    private void authenticateSpotify() {
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
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
    public void onExerciseChanged(ExerciseResult result, Direction direction) {

        trainingResult.setResultAt(day.getIndex(result.getExerciseId()), result);
        saveToStorage(result);

        ExerciseResult nextResult = getNextExerciseResult(result, direction);
        if(nextResult != null) {
            openExerciseFragment(nextResult);
        }else {
            Spotify.destroyPlayer(this);
            openStatisticsActivity();
        }
    }

    public void openExerciseFragment(ExerciseResult result) {
        ExerciseResultFragment fragment = new ExerciseResultFragment();
        FragmentManager fm = getSupportFragmentManager();
        Bundle b = new Bundle();
        Exercise exercise = day.getExercise(result.getExerciseId());
        track = exercise.getTrack();

        b.putString(getResources().getString(R.string.exerciseHeader), exercise.getName() + " " + exercise.getSetCount() + " X " + exercise.getRepCountMin() + "-" + exercise.getRepCountMax());
        b.putString(getResources().getString(R.string.exerciseResult), new Gson().toJson(result));
        fragment.setArguments(b);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
        playSpotifyTrack(track);
    }

    private ExerciseResult getNextExerciseResult(ExerciseResult result, Enum direction) {
        int exerciseId = result.getExerciseId();
        int index = 0;
        if(direction == Direction.NEXT){
            index = day.getIndex(exerciseId)+1;
        }else if(direction == Direction.PREV){
            index = day.getIndex(exerciseId)-1;
        }
        if(index >= 0 && index < day.getExercises().size()){
            return trainingResult.getExerciseResultByIndex(index);
        }
        return null;
    }

    private void openStatisticsActivity() {
        Intent dayIntent = new Intent(ExerciseActivity.this, StatisticsActivity.class);
        dayIntent.putExtra("dayId", String.valueOf(day.getId()));
        startActivity(dayIntent);
    }

    public void saveToStorage(ExerciseResult result) {
        ExerciseLogItem item = new ExerciseLogItem((float) result.getWeight(), result.getReps());
        String filename = "_exercise" + result.getExerciseId() + "_day" + trainingResult.getDayId() + ".json";
        TrainingLogItem oldTraining = getJson(filename);
        Exercise e = day.getExercise(result.getExerciseId());
        if(oldTraining==null) {
            oldTraining = new TrainingLogItem(e.getName(), result.getExerciseId(), day.getId());
        }
        oldTraining.addExerciseLogItem(item);
        saveJson(filename, oldTraining);
    }

    public TrainingLogItem getJson(String filename) {
        String result = null;
        try {
            FileInputStream inputStream = openFileInput(filename);
            result = Utils.readJsonFromInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(result, TrainingLogItem.class);
    }

    public void saveJson(String filename, TrainingLogItem item) {
        FileOutputStream outputStream;
        String result = new Gson().toJson(item);
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            Utils.writeJsonToOutputStream(result, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playSpotifyTrack(final String track) {
        if(playerConfig != null && track != null & !track.equals("")) {
            mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                @Override
                public void onInitialized(Player player) {
                    mPlayer.addConnectionStateCallback(ExerciseActivity.this);
                    mPlayer.addPlayerNotificationCallback(ExerciseActivity.this);
                    mPlayer.play(track);
                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e("ExerciseActivity", "Could not initialize player: " + throwable.getMessage());
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                playSpotifyTrack(track);
            }
        }
    }

    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Throwable throwable) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("ExerciseActivity", "Playback event received: " + eventType.name());
        switch (eventType) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("ExerciseActivity", "Playback error received: " + errorType.name());
        switch (errorType) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        String DEBUG_TAG = "HALLELUJAH";
        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d(DEBUG_TAG,"Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(DEBUG_TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(DEBUG_TAG,"Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }


}

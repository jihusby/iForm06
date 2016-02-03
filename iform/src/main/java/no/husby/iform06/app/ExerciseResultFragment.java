package no.husby.iform06.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.Button;
import com.google.gson.Gson;
import no.husby.iform06.model.Exercise;
import no.husby.iform06.model.ExerciseResult;
import no.husby.iform06.utils.Direction;
import roboguice.fragment.RoboFragment;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.fragment_exercise_result)
public class ExerciseResultFragment extends RoboFragment {

    @InjectView(R.id.exerciseName) TextView exerciseName;

    @InjectView(R.id.linearLayoutReps)
    LinearLayout linearLayoutReps;

    @InjectView(R.id.weight)
    private EditText weight;

    @InjectView(R.id.prevExercise)
    Button prevExercise;

    @InjectView(R.id.nextExercise)
    Button nextExercise;

    private ViewGroup container;
    private ExerciseResult exerciseResult;



    public ExerciseResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Bundle a = getArguments();

        if(a != null) {
            exerciseResult = new Gson().fromJson(a.getString(getResources().getString(R.string.exerciseResult)), ExerciseResult.class);
            exerciseName.setText(a.getString(getResources().getString(R.string.exerciseHeader)));

            // TODO: Get data from previous trainingresult
            String w = String.valueOf(exerciseResult.getWeight());
            weight.setText(w, TextView.BufferType.EDITABLE);
            drawSetList(exerciseResult.getReps().length, exerciseResult);
            drawNavigationButtons();
        }
    }

    private void drawNavigationButtons() {
        prevExercise.setOnClickListener(buildNavigationButtonListener(exerciseResult, Direction.PREV));
        nextExercise.setOnClickListener(buildNavigationButtonListener(exerciseResult, Direction.NEXT));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_result, container, false);
    }

    private View.OnClickListener buildNavigationButtonListener(final ExerciseResult exerciseResult, final Direction direction) {
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    ((OnExerciseChangedListener) getActivity()).onExerciseChanged(exerciseResult, direction);
                }
                catch (ClassCastException cce){
                }
            }
        };

        return listener;
    }

    public void drawSetList(int setCount, ExerciseResult exerciseResult) {
        for (int i = 0; i < setCount; i++) {
            int defaultValue = exerciseResult.getRepAt(i);
            drawSetRow(i, defaultValue);
        }
    }

    public void drawSetRow(int index, final int defaultValue) {

        final LinearLayout rowLayoutContainer = new LinearLayout(getActivity());
        rowLayoutContainer.setOrientation(LinearLayout.HORIZONTAL);

        final EditText numOfReps = buildEditText(index, defaultValue);
        final Button button = buildButton();

        button.setOnClickListener(buildOnClickListener(numOfReps, rowLayoutContainer, button, exerciseResult.getPause()));

        numOfReps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    if(String.valueOf(defaultValue).equalsIgnoreCase(numOfReps.getText().toString())) {
                        numOfReps.setText("");
                    }
                    rowLayoutContainer.addView(button);
                }else {
                    if(numOfReps.hasFocus() && numOfReps.isEnabled() && "".equalsIgnoreCase(numOfReps.getText().toString())) {
                        numOfReps.setText(defaultValue);
                    }
                    rowLayoutContainer.removeView(button);
                }
            }
        });
        rowLayoutContainer.addView(numOfReps);
        linearLayoutReps.addView(rowLayoutContainer);
    }

    private View.OnClickListener buildOnClickListener(final EditText editText, final LinearLayout rowLayout, final Button button, final int pause) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                new CountDownTimer(pause*1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        prevExercise.setEnabled(false);
                        nextExercise.setEnabled(false);
                        button.setBackground(getResources().getDrawable(R.drawable.mybutton_red));
                        button.setText("" + millisUntilFinished / 1000);
                        button.setEnabled(false);
                    }

                    public void onFinish() {
                        button.setBackground(getResources().getDrawable(R.drawable.mybutton));
                        rowLayout.removeView(button);
                        editText.setEnabled(false);
                        prevExercise.setEnabled(true);
                        nextExercise.setEnabled(true);
                        exerciseResult.setRepResult(Integer.valueOf(String.valueOf(editText.getText())), editText.getId());
                        exerciseResult.setWeight(Double.parseDouble(String.valueOf(weight.getText())));
                    }
                }.start();
            }
        };
        return listener;
    }

    private Button buildButton() {
        Button button = new Button(getActivity());
        button.setBackground(getResources().getDrawable(R.drawable.mybutton));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, 200);
        params.weight = 1.0f;
        params.setMargins(0, 0, 0, 0);
        button.setLayoutParams(params);
        button.setTextColor(Color.WHITE);
        button.bringToFront();
        button.setText(getResources().getString(R.string.pause));
        return button;
    }

    private EditText buildEditText(int index, int defaultValue) {
        final EditText editText = new EditText(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, 200);
        params.weight = 1.0f;
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        editText.setLayoutParams(params);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setId(index);
        editText.bringToFront();
        editText.setText(String.valueOf(defaultValue));
        editText.clearFocus();

        return editText;
    }

    public interface OnExerciseChangedListener {
        public void onExerciseChanged(ExerciseResult exerciseResult, Direction direction);
    }
}

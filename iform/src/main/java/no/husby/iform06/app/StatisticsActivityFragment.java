package no.husby.iform06.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.inject.Inject;
import no.husby.iform06.model.TrainingLogItem;
import roboguice.fragment.RoboFragment;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.fragment_statistics)
public class StatisticsActivityFragment extends RoboFragment {

    @InjectView(R.id.trainingResults)
    private ListView trainingResults;

    public StatisticsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle a = getArguments();

        if(a!=null) {
            String result = a.getString("trainingResults");

            TrainingLogItem[] resultList = new Gson().fromJson(result, TrainingLogItem[].class);

            ArrayAdapter<TrainingLogItem> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, resultList);
            if(trainingResults == null) {
                trainingResults = (ListView) getActivity().findViewById(R.id.trainingResults);
                trainingResults.setAdapter(adapter);
            }


        }

        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

}

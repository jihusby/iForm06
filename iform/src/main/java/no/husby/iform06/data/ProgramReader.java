package no.husby.iform06.data;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.util.Log;
import com.google.gson.Gson;
import no.husby.iform06.model.*;
import no.husby.iform06.utils.Utils;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;

public class ProgramReader {

    private final AssetManager assetManager;

    public ProgramReader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }


    public Program getProgram(Activity a, User user) {
        if(user!=null) {
            return getProgramFrom(a, user, assetManager);
        }
        return null;
    }

    private Program getProgramFrom(Activity a, User user, AssetManager assetManager) {
        String result = loadJSONFromAsset(a, "_program" + user.getId() + ".json", assetManager);
        return new Gson().fromJson(result, Program.class);
    }

    private String loadJSONFromAsset(Activity a, String filename, AssetManager assetManager) {
        String json = null;

        try {
            FileInputStream inputStream = a.openFileInput(filename);
            json = Utils.readJsonFromInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}

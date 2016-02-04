package no.husby.iform06.utils;

import android.app.Activity;
import android.content.Context;
import com.google.gson.Gson;
import no.husby.iform06.model.Day;
import no.husby.iform06.model.TrainingResult;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils extends Activity {

    public static String readJsonFromInputStream(FileInputStream inputStream) throws IOException {
        int c;
        String resultString=("");
        while( (c = inputStream.read()) != -1){
            resultString = resultString + Character.toString((char)c);
        }
        inputStream.close();
        return URLDecoder.decode(resultString, "UTF-8");
    }

    public static void writeJsonToOutputStream(String json, FileOutputStream outputStream) throws IOException {
        String string = URLEncoder.encode(json, "UTF-8");
        outputStream.write(string.getBytes());
        outputStream.close();
    }

}

package no.husby.iform06.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getDateString() {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date today = new Date();
        try {
            Date todayWithZeroTime = formatter.parse(formatter.format(today));
            return String.valueOf(todayWithZeroTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateString(Date date) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date todayWithZeroTime = formatter.parse(formatter.format(date));
            return String.valueOf(todayWithZeroTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}

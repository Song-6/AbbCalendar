package com.abblcg.test.xcalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Abb on 2018/1/4.
 */

public class TimeUtil {

    public static String printLogCalendar(Calendar calendar) {
        SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
        df.applyPattern("yyyy-MM-dd HH:mm:ss");

        if (calendar == null) calendar = Calendar.getInstance();
        String time = df.format(calendar.getTimeInMillis());

        return time;
    }
}

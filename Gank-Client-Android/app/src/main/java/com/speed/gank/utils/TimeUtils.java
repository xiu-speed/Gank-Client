package com.speed.gank.utils;

import java.util.Calendar;

/**
 * Created by huben on 15/8/11.
 */
public class TimeUtils {

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year;
    }


    public static int getMonth() {
        int month = (Calendar.getInstance().get(Calendar.MONTH)) + 1;
        return month;
    }

    public static int getDay() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return day;
    }
}

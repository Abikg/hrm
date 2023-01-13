package com.makalu.hrm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat dfDay = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean hasSameDay(Date d1, Date d2){
        return dfDay.format(d1).equals(dfDay.format(d2));
    }

    public static double getHours(Date d1, Date d2){
        return ((d1.getTime() - d2.getTime())/ (1000 * 60 * 60)) % 24;
    }
}

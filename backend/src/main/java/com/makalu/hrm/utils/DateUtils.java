package com.makalu.hrm.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Formatter;

public class DateUtils {

    private static SimpleDateFormat dfDay = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean hasSameDay(Date d1, Date d2) {
        return dfDay.format(d1).equals(dfDay.format(d2));
    }

    public static double getHours(Date d1, Date d2){
        Duration duration=Duration.between(d1.toInstant(), d2.toInstant());
        Long seconds=new Long(duration.toSeconds());
        Double hours= (seconds.doubleValue())/3600*(-1);
        DecimalFormat df = new DecimalFormat("#.00000");
        hours = Double.valueOf(df.format(hours));

        return  hours;

    }
}

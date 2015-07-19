package com.example.apurp_000.dementiaapp;

import java.util.Calendar;

/**
 * Created by Ryan on 7/18/2015.
 */
public class GenerateTime {

    //Generate the times for Analytics
    public String generateTimes ()
    {   int zMinute;
        int zHourOfDay;
        String zMin;
        String zHour;
        String zTime;
        final java.util.Calendar cal = java.util.Calendar.getInstance();
        zMinute = cal.get(Calendar.MINUTE);
        zHourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        zMin = Integer.toString(zMinute);
        zHour = Integer.toString(zHourOfDay);
        zTime = (zHour + ":" + zMin);
        return zTime;
    }

}

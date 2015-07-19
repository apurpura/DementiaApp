package com.example.apurp_000.dementiaapp;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ryan on 7/18/2015.
 */
public class GenerateTime {

    //Generate the times for Analytics
    public String generateTimes ()
    {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString ;
    }

}

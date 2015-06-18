package com.example.apurp_000.dementiaapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

/**
 * Created by apurpura on 6/17/2015.
 */
public class CalendarAPIAdapter {

    public String getCalendar() throws IOException {
        String id = getCalendarId(Credentials.credential.getSelectedAccountName());
        if (id == ""){
            com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
            calendar.setSummary("MARA");
            com.google.api.services.calendar.model.Calendar newC = Credentials.signonActivity.calendarService.calendars().insert(calendar).execute();
            addCalendarDBEntry(Credentials.credential.getSelectedAccountName(), newC.getId());
            id = newC.getId();
        }
        return id;
    }

    public void addCalendarDBEntry(String username, String calendarId) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(CalendarDbHelper.Calendar.USERNAME,username);
        values.put(CalendarDbHelper.Calendar.CALENDAR_ID, calendarId);

        SQLiteDatabase db = new CalendarDbHelper(ApplicationContextProvider.getContext()).getWritableDatabase();
        db.insert("Calendar", null, values);
        db.close();
    }

    public String getCalendarId(String username){
        SQLiteDatabase db = new CalendarDbHelper(ApplicationContextProvider.getContext()).getReadableDatabase();
        String id = "";

        String[] projection = {
                CalendarDbHelper.Calendar.CALENDAR_ID
        };

        String selection = CalendarDbHelper.Calendar.USERNAME + " = ?";
        String[] selectionArgs = {username};


        Cursor c = db.query(
                "Calendar",  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if(c != null){
            try {
                c.moveToFirst();
                id = c.getString(0);
            }catch(Exception e){
                id = "";
            }
        }

        return id;
    }

}





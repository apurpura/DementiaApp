package com.example.apurp_000.dementiaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by apurpura on 6/17/2015.
 */
public class CalendarAPIAdapter {
    private static Context mActivity;
    CalendarAPIAdapter(Context context){
        mActivity = context;
    }

    public String getCalendar() throws IOException {
        String id = "";
        RefreshCredentialsService.refreshCredentials();
        HashMap<String, String> calList = getCalendarList();
        if (!calList.containsKey(Credentials.credential.getSelectedAccountName())){
            com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
            calendar.setSummary(Credentials.credential.getSelectedAccountName() + " MARA");
            com.google.api.services.calendar.model.Calendar newC = Credentials.signonActivity.calendarService.calendars().insert(calendar).execute();
            addCalendarDBEntry(Credentials.credential.getSelectedAccountName(), newC.getId());
            id = newC.getId();
        }else{
            id = calList.get(Credentials.credential.getSelectedAccountName());
        }
        return id;
    }

    public static String getAccount(String calendarId){
        RefreshCredentialsService.refreshCredentials();
        String account = "";
        HashMap<String, String> ls = getCalendarList();
        for(String acc : ls.keySet()) {
            if (ls.get(acc).equals(calendarId)) {
                account = acc;
                break;
            }
        }
        return account;
    }

    public static HashMap<String, String> getCalendarList(){
        RefreshCredentialsService.refreshCredentials();
        HashMap<String, String> calList = new HashMap<String, String>();

        // Iterate through entries in calendar list
        String pageToken = null;
        do {
            CalendarList calendarList = null;
            try {
                calendarList = Credentials.signonActivity.calendarService.calendarList().list().setPageToken(pageToken).execute();
            }  catch (UserRecoverableAuthIOException e) {
                mActivity.startActivity(e.getIntent());
            }catch(Exception e){
                RefreshCredentialsService.refreshCredentials();
            }
            if(calendarList == null)
                return calList;
            List<CalendarListEntry> items = calendarList.getItems();

            for (CalendarListEntry calendarListEntry : items) {
                if(calendarListEntry.getSummary().contains(" MARA"))
                    calList.put(calendarListEntry.getSummary().replace(" MARA", ""), calendarListEntry.getId());
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);
        return calList;
    }

    public void addCalendarDBEntry(String username, String calendarId) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(CalendarDbHelper.Calendar.USERNAME,username);
        values.put(CalendarDbHelper.Calendar.CALENDAR_ID, calendarId);

        SQLiteDatabase db = new CalendarDbHelper(mActivity).getWritableDatabase();
        db.insert("Calendar", null, values);
        db.close();
    }

    public String getCalendarId(String username){
        SQLiteDatabase db = new CalendarDbHelper(mActivity).getReadableDatabase();
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
        c.close();
        return id;
    }

}





package com.example.apurp_000.dementiaapp;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.google.api.client.util.DateTime;
//import com.google.api.services.calendar.model.EventDateTime;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.example.apurp_000.dementiaapp.EventDbHelper.Event.*;

/**
 * Created by apurpura on 6/19/2015.
 */
public class EventDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DICTIONARY_TABLE_NAME = "Event";
    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    _ID + " integer Primary Key autoincrement not null, " +
                    Id + " TEXT not null, " +
                    CalendarId + " TEXT, " +
                    Summary + " TEXT, " +
                    Description + " TEXT, " +
                    Location + " TEXT, " +
                    StartTime + " TEXT, " +
                    EndTime + " TEXT, " +
                    Action + " TEXT)";
    private static final java.lang.String SQL_DELETE_ENTRIES = "DROP TABLE Event";

    EventDbHelper(Context context) {
        super(context, DICTIONARY_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int n){
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /* Inner class that defines the table contents */
    public static class Event implements BaseColumns {
        public static final String _ID = "u_id";
        public static final String Id = "Id";
        public static final String CalendarId = "CalendarId";
        public static final String Summary = "Summary";
        public static final String Description = "Description";
        public static final String Location = "Location";
        public static final String StartTime = "StartTime";
        public static final String EndTime = "EndTime";
        public static final String Action = "Action";
    }

    public void insertEventDB(String id, String calendarId, String summary, String description,
                                String location, DateTime startTime, DateTime endTime, String action
                                , Context context) {

        // Create a new map of values, where column names are the keys
        if(GetEvent(id, context).Id == "") {
            ContentValues values = new ContentValues();
            values.put(Id, id);
            values.put(CalendarId, calendarId);
            values.put(Summary, summary);
            values.put(Description, description);
            values.put(Location, location);
            values.put(StartTime, startTime.toString());
            values.put(EndTime, endTime.toString());
            values.put(Action, action);

            SQLiteDatabase db = new EventDbHelper(context).getWritableDatabase();
            db.insert("Event", null, values);
            db.close();

            Integer theId = GetEvent(id, context).u_id;
            EventModel e = new EventModel(id, calendarId, summary, description, location, startTime.toString(), endTime.toString(), action, theId);
            AlarmManagerHelper.setAlarm(context, startTime.getValue(), e);
        }
    }

    public EventModel GetEvent(String id, Context context){
        SQLiteDatabase db = new EventDbHelper(context).getReadableDatabase();
        EventModel m = new EventModel("","","","","","","","",0);

        String[] projection = {
                Id, Action, CalendarId
                , Description, EndTime, Location
                , StartTime, Summary, _ID
        };

        String selection = Id + " = ?";
        String[] selectionArgs = {id};


        Cursor c = db.query(
                "Event",  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        HashMap<String, String> newMap = new HashMap<String, String>();
        if(c != null){
            try {
                c.moveToFirst();
                m = new EventModel(c.getString(0), c.getString(2),
                        c.getString(7), c.getString(3),c.getString(5),
                        c.getString(6),c.getString(4),c.getString(1), c.getInt(8));
            }catch(Exception e){
                id = "";
            }
        }

        return m;
    }

   


}

package com.example.apurp_000.dementiaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import com.google.api.client.util.DateTime;
//import com.google.api.services.calendar.model.EventDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.apurp_000.dementiaapp.EventDbHelper.Event.*;
import static com.example.apurp_000.dementiaapp.EventResultDBHelper.EventResultStrings.Action;
import static com.example.apurp_000.dementiaapp.EventResultDBHelper.EventResultStrings.CalendarId;
import static com.example.apurp_000.dementiaapp.EventResultDBHelper.EventResultStrings.CancelTime;
import static com.example.apurp_000.dementiaapp.EventResultDBHelper.EventResultStrings.EndTime;
import static com.example.apurp_000.dementiaapp.EventResultDBHelper.EventResultStrings.EventId;
import static com.example.apurp_000.dementiaapp.EventResultDBHelper.EventResultStrings.Level;
import static com.example.apurp_000.dementiaapp.EventResultDBHelper.EventResultStrings.Score;
import static com.example.apurp_000.dementiaapp.EventResultDBHelper.EventResultStrings.StartTime;
import static com.example.apurp_000.dementiaapp.EventResultDBHelper.EventResultStrings.Trophy;

/**
 * Created by apurpura on 6/19/2015.
 */
public class EventDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;
    private static final String DICTIONARY_TABLE_NAME = "EventModel";
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
                    Action + " TEXT, " +
                    Notify + " TEXT)";
    private static final java.lang.String SQL_DELETE_ENTRIES = "DROP TABLE EventModel";

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
        public static final String Notify = "Notify";
    }

    public void insertEventDB(String id, String calendarId, String summary, String description,
                                String location, DateTime startTime, DateTime endTime, String action
                                , Context context, String notify) {

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
            values.put(Notify, notify);
            SQLiteDatabase db = new EventDbHelper(context).getWritableDatabase();
            db.insert("EventModel", null, values);
            db.close();

            Integer theId = GetEvent(id, context).u_id;
            EventModel e = new EventModel(id, calendarId, summary, description, location, startTime.toString(), endTime.toString(), action, theId, notify);
            HashMap<String, String> ls = CalendarAPIAdapter.getCalendarList();
            String acctName = Credentials.credential.getSelectedAccountName();
            String primary = ls.get(acctName);
            if(calendarId.equals(primary))
                AlarmManagerHelper.setAlarm(context, startTime.getValue(), e, "AlarmService");
        }
    }

    public EventModel GetEvent(String id, Context context){
        SQLiteDatabase db = new EventDbHelper(context).getReadableDatabase();
        EventModel m = new EventModel("","","","","","","","",0, "");

        String[] projection = {
                Id, Action, CalendarId
                , Description, EndTime, Location
                , StartTime, Summary, _ID, Notify
        };

        String selection = Id + " = ?";
        String[] selectionArgs = {id};


        Cursor c = db.query(
                "EventModel",  // The table to query
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
                        c.getString(6),c.getString(4),c.getString(1), c.getInt(8), c.getString(9));
            }catch(Exception e){
                id = "";
            }
        }

        return m;
    }

    public boolean delete(String id, Context context)
    {
        SQLiteDatabase db = new EventDbHelper(context).getWritableDatabase();
        //return db.delete(, Id + "=" + id, null) > 0;
        return db.delete(DICTIONARY_TABLE_NAME, Id + " = ?", new String[] { id }) > 0;
    }

    public void updateNotify(Context context, String id){
        SQLiteDatabase db = new EventDbHelper(context).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Notify","False");
        db.update(DICTIONARY_TABLE_NAME, cv, "Id " + "=\"" + id + "\"", null);
    }

    public HashMap<String,List<EventModel>> GetNotifyEventsWithoutEventResult(Context context){
        EventModel m = new EventModel("","","","","","","","",0, "");

        HashMap<String, List<EventModel>> results = new HashMap<String, List<EventModel>>();
        SQLiteDatabase db = new EventDbHelper(context).getReadableDatabase();

        String select =
                "select e.Id, e.Action, e.CalendarId" +
                ", e.Description, e.EndTime, e.Location" +
                ", e.StartTime, e.Summary, e.Notify" +
                        " From EventModel e where e.Notify = ?";


        //String selection = "EventModel." + Notify + " = ?";
        String[] selectionArgs = {"True"};
        //Specify books table and add join to categories table (use full_id for joining categories table)
        //SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        //_QB.setTables("EventModel" +
            //    " JOIN " + "EventResult ON " +
             //   "EventModel.Id" + " = " + "EventResult.EventId");



        Cursor c = db.rawQuery(
                select,                                // The columns for the WHERE clause
                selectionArgs                               // The sort order
        );

        if (c != null) {
            // move cursor to first row
            if (c.moveToFirst()) {
                do {
                    String startTime = c.getString(c.getColumnIndex("StartTime"));
                    String endTime = c.getString(c.getColumnIndex("EndTime"));
                    if(!startTime.equals(endTime)) {
                        boolean insertList = false;
                        List<EventModel> ls;
                        String action = c.getString(c.getColumnIndex("Action"));
                        String calendarId = c.getString(c.getColumnIndex("CalendarId"));
                        if (action != "" & action != "n/a" & calendarId != "") {
                            if (!results.containsKey(action)) {
                                ls = new ArrayList<EventModel>();
                                insertList = true;
                            } else
                                ls = results.get(action);

                            String eventId = c.getString(c.getColumnIndex("Id"));
                            String notify = c.getString(c.getColumnIndex("Notify"));
                            String summary = c.getString(c.getColumnIndex("Summary"));
                            String description = c.getString(c.getColumnIndex("Description"));

                            EventModel er = new EventModel(eventId, calendarId, summary, description, "", startTime, endTime, action, 0, notify);

                            // add the bookName into the bookTitles ArrayList
                            ls.add(er);
                            // move to next row
                            if (insertList)
                                results.put(action, ls);
                        }
                    }
                } while (c.moveToNext());
            }
        }


        return results;
    }




}

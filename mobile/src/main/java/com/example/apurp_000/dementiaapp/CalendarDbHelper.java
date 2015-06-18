package com.example.apurp_000.dementiaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by apurpura on 6/17/2015.
 */
public class CalendarDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DICTIONARY_TABLE_NAME = "Calendar";
    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    Calendar.USERNAME + " TEXT Primary Key, " +
                    Calendar.CALENDAR_ID + " TEXT);";

    CalendarDbHelper(Context context) {
        super(context, DICTIONARY_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int n){
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
       // db.execSQL(SQL_DELETE_ENTRIES);
        //onCreate(db);
    }

    /* Inner class that defines the table contents */
    public static class Calendar implements BaseColumns {
        public static final String USERNAME = "Username";
        public static final String CALENDAR_ID = "Calendar_Id";
    }
}




package com.example.apurp_000.dementiaapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.EditText;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by apurpura on 7/27/2015.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class DeleteEventHelperAsyc extends AsyncTask<Void, Void, Void> {

    private static CalendarActivity mActivity;
    private static HttpTransport httpTransport;
    private String eventId;

    /**
     * Constructor.
     * @param activity SigningOnActivity that spawned this task.
     */
    DeleteEventHelperAsyc(CalendarActivity activity, String id) {
        this.mActivity = activity;
        eventId = id;
        try {
            httpTransport = AndroidHttp.newCompatibleTransport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Background task to call Google Calendar API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected Void doInBackground(Void... params) {
        try {
            Credentials.signonActivity.refreshCalendarService();
            initializeEvent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initializeEvent() throws IOException {
        String calendarId = CalendarAPIAdapter.getCalendarList().get(Account.account);
        //delete the event from calendar
        Credentials.signonActivity.calendarService.events().delete(calendarId, eventId).execute();

        //delete from db and cancel alarm
        new EventDbHelper(mActivity).delete(eventId, mActivity);
        AlarmManagerHelper.cancelAlarm(mActivity, eventId);
    }
}


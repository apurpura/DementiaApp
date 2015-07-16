package com.example.apurp_000.dementiaapp;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.util.Random;

/**
 * Created by apurpura on 7/12/2015.
 */
public class StartEventSync extends AsyncTask<Void, Void, Void> {
    private static SigningOnActivity mActivity;
    private static HttpTransport httpTransport;

    /**
     * Constructor.
     *
     * @param activity SigningOnActivity that spawned this task.
     */
    StartEventSync(SigningOnActivity activity) {
        this.mActivity = activity;
    }

    /**
     * Background task to call Google Calendar API.
     *
     * @param params no parameters needed for this task.
     */
    @Override
    protected Void doInBackground(Void... params) {
        startAlarm();
        return null;
    }

    public static void startAlarm(){
        String id = Integer.toString(new Random().nextInt());
        String calendarId = null;
        try {
            calendarId = new CalendarAPIAdapter(Credentials.signonActivity).getCalendar();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            CalendarApiHelperAsync.UpdateEvents(new CalendarAPIAdapter(Credentials.signonActivity).getCalendar());
        } catch (IOException e) {
            e.printStackTrace();
        }
        EventModel e = new EventModel(id, calendarId, "", "", "", "", "", "", 0);
        AlarmManagerHelper.setAlarm(Credentials.signonActivity, getUpdateAlarmInterval(), e, "UpdateEventsService");
    }

    private static long getUpdateAlarmInterval(){
        DateTime startDateTime = new DateTime(System.currentTimeMillis() + 30000);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        return start.getDateTime().getValue();
    }
}

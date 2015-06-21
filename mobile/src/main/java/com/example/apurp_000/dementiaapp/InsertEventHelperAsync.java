package com.example.apurp_000.dementiaapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class InsertEventHelperAsync extends AsyncTask<Void, Void, Void> {
    private static InsertEvent mActivity;
    private static HttpTransport httpTransport;

    /**
     * Constructor.
     * @param activity SigningOnActivity that spawned this task.
     */
    InsertEventHelperAsync(InsertEvent activity) {
        this.mActivity = activity;
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
            initializeEvent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initializeEvent() throws IOException {
        CalendarAPIAdapter zCalendarAPIAdapter = new CalendarAPIAdapter(mActivity);
        String calendarId = zCalendarAPIAdapter.getCalendar();
        //final EditText mEventTitle = (EditText) findViewById(R.id.eventTitle);
        //final EditText mEventDescription = (EditText) findViewById(R.id.eventDescription);
        //final EditText mEventDate = (EditText) findViewById(R.id.eventStartDate);
        String summary = "Google I/O 2015";
        String location = "800 Howard St., San Francisco, CA 94103";
        String description = "A chance to hear more about Google's developer products.";

        Event event = new Event()
                .setSummary(summary)
                .setLocation(location)
                .setDescription(description);

        DateTime startDateTime = new DateTime(System.currentTimeMillis() + 60000);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setStart(start);

        DateTime endDateTime = new DateTime(System.currentTimeMillis() + 720000);
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setEnd(end);

        Event ev = Credentials.signonActivity.calendarService.events().insert(calendarId,event).execute();
        String id = ev.getId();

        //insert in DB
        new EventDbHelper(mActivity).insertEventDB(id, calendarId, summary, description,
                location, start.getDateTime(), end.getDateTime(), "TEXT"
                , mActivity);

        Intent intent = new Intent(mActivity, CalendarActivity.class);
        mActivity.startActivity(intent);

    }
}


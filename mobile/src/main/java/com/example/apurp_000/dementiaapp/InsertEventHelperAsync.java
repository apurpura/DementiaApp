package com.example.apurp_000.dementiaapp;

import android.content.Intent;
import android.os.AsyncTask;
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
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class InsertEventHelperAsync extends AsyncTask<Void, Void, Void> {
    private static InsertEventActivity mActivity;
    private static HttpTransport httpTransport;

    /**
     * Constructor.
     * @param activity SigningOnActivity that spawned this task.
     */
    InsertEventHelperAsync(InsertEventActivity activity) {
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
            CalendarApiHelperAsync.refreshCredentials();
            initializeEvent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initializeEvent() throws IOException {
        String calendarId = CalendarAPIAdapter.getCalendarList().get(Account.account);
        EditText summary = (EditText) mActivity.findViewById(R.id.eventTitle);
        //don't need location ////EditText location = (EditText) mActivity.findViewById(R.id.eventLocation);
        EditText description = (EditText) mActivity.findViewById(R.id.eventDescription);
        Date zStartDateTime = mActivity.zGetStartDate();
        Date zEndDateTime = mActivity.zGetEndDate();

        Event event = new Event()
                .setSummary(summary.getText().toString())
                .setLocation("MARA")
                .setDescription(description.getText().toString());

        //DateTime startDateTime = new DateTime(System.currentTimeMillis() + 60000);
        DateTime startDateTime = new DateTime(zStartDateTime);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setStart(start);

        //DateTime endDateTime = new DateTime(System.currentTimeMillis() + 720000);
        DateTime endDateTime = null;
        String notify = "False";
        if(mActivity.checked) {
            notify = "True";
            if( zEndDateTime != null)
                endDateTime = new DateTime(zEndDateTime);
            else
                endDateTime = startDateTime;
        }
        else
            endDateTime = startDateTime;
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setEnd(end);

        //add the action to extendedProperties
        Event.ExtendedProperties EP = new Event.ExtendedProperties();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Action", mActivity.action);
        if(mActivity.checked)
            map.put("Notify", "True");
        EP.setShared(map);
        event.setExtendedProperties(EP);

        Event ev = Credentials.signonActivity.calendarService.events().insert(calendarId,event).execute();
        String id = ev.getId();

        //insert in DB
        new EventDbHelper(mActivity).insertEventDB(id, calendarId, summary.getText().toString(), description.getText().toString(),
                "MARA", start.getDateTime(), end.getDateTime(), mActivity.action
                , mActivity, notify);

        Intent intent = new Intent(mActivity, CalendarActivity.class);
        mActivity.startActivity(intent);

    }
}


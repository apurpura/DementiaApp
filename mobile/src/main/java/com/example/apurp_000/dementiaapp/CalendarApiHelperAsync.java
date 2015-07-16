package com.example.apurp_000.dementiaapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.*;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


/**
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class CalendarApiHelperAsync extends AsyncTask<Void, Void, Void> {
    private static CalendarActivity mActivity;
    private static HttpTransport httpTransport;
    private static final String PREFS_NAME = "CalendarPref";
    private static final String PREFS_KEY = "SyncToken";

    /**
     * Constructor.
     * @param activity SigningOnActivity that spawned this task.
     */
    CalendarApiHelperAsync(CalendarActivity activity) {
        this.mActivity = activity;
        try {
            httpTransport = AndroidHttp.newCompatibleTransport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor overload
     */
    CalendarApiHelperAsync() {
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
            String calendarId = CalendarAPIAdapter.getCalendarList().get(Account.account);
            try {

                getDataFromApi(calendarId);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            updateResultsText();


        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
           Credentials.showGooglePlayServicesAvailabilityErrorDialog(
                   availabilityException.getConnectionStatusCode(), mActivity);

        } catch (UserRecoverableAuthIOException userRecoverableException) {
            mActivity.startActivityForResult(
                    userRecoverableException.getIntent(),
                    SigningOnActivity.REQUEST_AUTHORIZATION);

        } catch (IOException e) {
            updateStatus("The following error occurred: " +
                    e.getMessage());
        }
        return null;
    }

    public void updateResultsText() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mActivity.listDataHeader == null) {
                    AlertDialogPopup.ShowDialogPopup("Error", "An error was accountered retrieving data!", mActivity);
                } else if (mActivity.listDataHeader.size() == 0) {
                    AlertDialogPopup.ShowDialogPopup("Alert", "No events found", mActivity);
                }
                mActivity.listAdapter.refresh();
            }
        });
    }

    /**
     * Show a status message in the list header TextView; called from background
     * threads and async tasks that need to update the UI (in the UI thread).
     * @param message a String to display in the UI header TextView.
     */
    public void updateStatus(final String message) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogPopup.ShowDialogPopup("Notification", message, mActivity);
            }
        });
    }

  /*  *//**
     * Clear any existing Google Calendar API data from the TextView and update
     * the header message; called from background threads and async tasks
     * that need to update the UI (in the UI thread).
     *//*
    public void clearResultsText() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // mActivity.mStatusText.setText("Retrieving data…");
               // mActivity.mResultsText.setText("");
            }
        });
    }*/

    /**
     * Fetch a list of the next 10 events from the primary calendar.
     * @return List of Strings describing returned events.
     * @throws IOException
     */
    private void getDataFromApi(String calendarId) throws IOException, ParseException {
        DateTime now = new DateTime(System.currentTimeMillis());
        List<String> eventStrings = new ArrayList<String>();
        Events events = Credentials.signonActivity.calendarService.events().list(calendarId)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        for (Event event : items) {
            String start = event.getStart().getDateTime().toString();
            String fulltime = start;
            String entireDate = start;
            int index = start.indexOf("T");
            String time = start.substring(index + 1, start.length() - 1);
            start = start.substring(0, index);
            DateFormat formatter1;
            formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            start = DateFormat.getDateInstance().format(formatter1.parse(start));
            String summary =  event.getSummary();

            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = df.parse(time);
            df.setTimeZone(TimeZone.getDefault());
            String formattedDate = DateFormat.getTimeInstance().format(date);
            String childLine = formattedDate + "     " + summary;
            if(!mActivity.listDataHeader.contains(start)){
                mActivity.listDataHeader.add(start);
                ArrayList newList = new ArrayList<String>();
                newList.add(childLine);
                mActivity.listDataChild.put(start, newList);
            }
            else{
                List<String> theList = mActivity.listDataChild.get(start);
                if(!theList.contains(childLine))
                    theList.add(childLine);
            }
        }

    }

    public static void UpdateEvents(String calendarId) throws IOException {
        // Construct the {@link Calendar.Events.List} request, but don't execute it yet.
        Calendar.Events.List request = Credentials.signonActivity.calendarService.events().list(calendarId);

        // Load the sync token stored from the last execution, if any.
        String syncToken = getValue(Credentials.signonActivity);
        if (syncToken == null) {
            System.out.println("Performing full sync.");
        } else {
            System.out.println("Performing incremental sync.");
            request.setSyncToken(syncToken);
        }

        // Retrieve the events, one page at a time.
        String pageToken = null;
        Events events = null;
        do {
            request.setPageToken(pageToken);
            try {
                events = request.execute();
            } catch (GoogleJsonResponseException e) {
                if (e.getStatusCode() == 410) {
                    // A 410 status code, "Gone", indicates that the sync token is invalid.
                    System.out.println("Invalid sync token, clearing event store and re-syncing.");
                    save(Credentials.signonActivity, null);
                    UpdateEvents(calendarId);
                } else {
                    throw e;
                }
            }

            List<Event> items = events.getItems();
            if (items.size() == 0) {
                System.out.println("No new events to sync.");
            } else {
                for (Event event : items) {
                    String action = "";
                    if(event.getExtendedProperties() != null)
                        action = event.getExtendedProperties().getShared().get("Action");
                    if(event.getId() != null & event.getSummary() != null & event.getStart() != null & event.getEnd() != null) {
                        new EventDbHelper(Credentials.signonActivity).insertEventDB(event.getId(), calendarId, event.getSummary(), event.getDescription(),
                                event.getLocation(), event.getStart().getDateTime(), event.getEnd().getDateTime(), action
                                , Credentials.signonActivity);
                    }
                }
            }

            pageToken = events.getNextPageToken();
        } while (pageToken != null);

        save(Credentials.signonActivity, events.getNextSyncToken());

        System.out.println("Sync complete.");
    }


    public static void save(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(PREFS_KEY, text); //3
        editor.commit(); //4
    }

    public static String getValue(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        text = settings.getString(PREFS_KEY, null); //2
        return text;
    }





}

package com.example.apurp_000.dementiaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;


/**
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class CalendarApiHelperAsync extends AsyncTask<Void, Void, Void> {
    private static CalendarActivity mActivity;
    private static HttpTransport httpTransport;
    private static final String PREFS_NAME = "CalendarPref";
    private static String PREFS_KEY = "SyncToken";
    private static final String ER_NAME = "CalendarPref";
    private static final String ER_KEY = "SyncToken";
    public static ArrayList<EventModel> eList;

    /**
     * Constructor.
     * @param activity SigningOnActivity that spawned this task.
     */
    CalendarApiHelperAsync(CalendarActivity activity) {
        this.mActivity = activity;
        eList = new ArrayList<EventModel>();
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
            RefreshCredentialsService.refreshCredentials();
            String calendarId = CalendarAPIAdapter.getCalendarList().get(Account.account);
            try {

                getDataFromApi(calendarId);
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
                if (eList.size() == 0) {
                    AlertDialogPopup.ShowDialogPopup("Alert", "No events found. Please insert events or choose another day.", mActivity);
                }else {
                    Toast.makeText(mActivity, "Swipe card left to delete event.", Toast.LENGTH_LONG).show();
                }
                RecyclerAdapter rAdapter = new RecyclerAdapter(eList, mActivity);
                mActivity.rView.setAdapter(rAdapter);
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
        String pageToken = null;
       // mActivity.listDataHeader.clear();
        DateTime begin = new DateTime(mActivity.eventDate.getMillis());
        DateTime end = new DateTime(mActivity.eventDateMax.getMillis());
        String tz = mActivity.eventDate.getZone().toString();

        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone(tz));
        String eventStart = DateFormat.getDateInstance().format(formatter.parse(begin.toString()));
        eList.clear();
        do {
            Events events = Credentials.signonActivity.calendarService.events().list(calendarId)
                    .setTimeMin(begin)
                    //.setTimeMax(end)
                    .setTimeZone(tz)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .setPageToken(pageToken)
                    .setMaxResults(50)
                    .execute();
            List<com.google.api.services.calendar.model.Event> items = events.getItems();
            for (Event event : items) {
                String start = event.getStart().getDateTime().toString();
                String fulltime = start;
                String entireDate = start;
                int index = start.indexOf("T");
                String time = start.substring(index + 1, start.length() - 1);
                start = start.substring(0, index);
                start = DateFormat.getDateInstance().format(formatter.parse(start));
                String summary = event.getSummary();
                String description = event.getDescription();
                String id = event.getId();

                String action = "";
                String notify = "False";
                if(event.getExtendedProperties() != null)
                    action = event.getExtendedProperties().getShared().get("Action");
                    notify = event.getExtendedProperties().getShared().get("Notify");


                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                df.setTimeZone(TimeZone.getDefault());
                Date date = df.parse(time);

                String formattedDate = DateFormat.getTimeInstance().format(date);
                if(start.equals(eventStart)) {
                    EventModel m = new EventModel(id, "", summary, description,
                            "", formattedDate, "", action, 1, notify);
                    eList.add(m);
                    //mActivity.listDataHeader.add(start + "       " + childLine);
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
        updateResultsText();
    }

    public static void UpdateEvents() throws IOException {
        // Construct the {@link Calendar.Events.List} request, but don't execute it yet.
        RefreshCredentialsService.refreshCredentials();
        HashMap<String, String> calList = CalendarAPIAdapter.getCalendarList();
        org.joda.time.DateTime eventDate = new org.joda.time.DateTime().withTimeAtStartOfDay();
        String tz=eventDate.getZone().toString();
        for(String email : calList.keySet()) {
                String calendarId=calList.get(email);
                PREFS_KEY = calendarId;
                Calendar.Events.List request = Credentials.signonActivity.calendarService.events().list(calendarId).setShowDeleted(true).setTimeZone(tz);

                // Load the sync token stored from the last execution, if any.
                String syncToken = getValue(Credentials.signonActivity);
                if (syncToken == null) {
                    System.out.println("Performing full sync eventResults.");
                } else {
                    System.out.println("Performing incremental sync eventResults.");
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
                            UpdateEvents();
                        } else {
                            throw e;
                        }
                    }

                    List<Event> items = events.getItems();
                    if (items.size() == 0) {
                        System.out.println("No new events to sync.");
                    } else {
                        System.out.println(items.size() + " items to sync");
                        for (Event event : items) {
                            if (event.getStatus().equals("cancelled")) {
                                //delete db entry
                                try {
                                    new EventDbHelper(Credentials.signonActivity).delete(event.getId(), Credentials.signonActivity);
                                } catch (Exception ee) {
                                    //there wasn't anything in the db
                                }
                                //delete the alarm if it exists
                                try {
                                    AlarmManagerHelper.cancelAlarm(Credentials.signonActivity, event.getId());
                                } catch (Exception e) {
                                    //tere wasn't an alarm
                                }

                            } else {
                                String action = "";
                                String notify = "False";
                                DateTime startD = event.getStart().getDateTime();
                                DateTime endD = event.getEnd().getDateTime();
                                if (event.getExtendedProperties() != null)
                                    try {
                                        action = event.getExtendedProperties().getShared().get("Action");
                                        notify = event.getExtendedProperties().getShared().get("Notify");
                                    }catch(Exception e){
                                        //keep going
                                    }
                                if (event.getId() != null & event.getSummary() != null & event.getStart() != null) {
                                    new EventDbHelper(Credentials.signonActivity).insertEventDB(event.getId(), calendarId, event.getSummary(), event.getDescription(),
                                            event.getLocation(), startD, endD, action
                                            , Credentials.signonActivity, notify);
                                }
                                String evR = "";
                                if (event.getExtendedProperties() != null)
                                    evR = event.getExtendedProperties().getShared().get("EventResult");
                                if(evR != null) {
                                    if (!evR.equals("")) {
                                        try {
                                            JSONObject json = new JSONObject(evR);
                                            action = json.get("action").toString().trim();
                                            String eventId = json.get("eventId").toString().trim();
                                            String startTime = json.get("startTime").toString().trim();
                                            String endTime = json.get("endTime").toString().trim();
                                            String cancelTime = json.get("cancelTime").toString().trim();
                                            String level = json.get("level").toString().trim();
                                            String score = json.get("score").toString().trim();
                                            String trophy = json.get("trophy").toString().trim();
                                            Log.d("insertingEventResult db", "EventId: " + eventId + ",Action: " + action);
                                            EventResultDBHelper db = new EventResultDBHelper(Credentials.signonActivity);
                                            db.insertEventResult(startTime, endTime, cancelTime, level, score, action, eventId, calendarId, Credentials.signonActivity, trophy);

                                            if(notify != null) {
                                                if (!Credentials.credential.getSelectedAccountName().contains(CalendarAPIAdapter.getAccount(calendarId)) & notify.equals("True")) {
                                                    StringBuilder desc = new StringBuilder();
                                                    desc.append(CalendarAPIAdapter.getAccount(calendarId) + "\n");
                                                    if (cancelTime != null && !cancelTime.equals("")) {
                                                        desc.append("Cancelled");
                                                    } else {
                                                        desc.append("Completed");
                                                    }
                                                    if (!score.equals("") && !score.equals("n/a") && !score.equals("N/A")) {
                                                        desc.append("Score: " + score + "\n");
                                                    }

                                                    Notification.sendNotification(Credentials.signonActivity, action, desc.toString());
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                        }

                    pageToken = events.getNextPageToken();
                } while (pageToken != null);

                save(Credentials.signonActivity, events.getNextSyncToken());
                System.out.println("Sync complete.");
            }
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

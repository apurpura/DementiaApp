package com.example.apurp_000.dementiaapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class CalendarApiHelperAsync extends AsyncTask<Void, Void, Void> {
    private static CalendarActivity mActivity;
    private static HttpTransport httpTransport;

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
     * Background task to call Google Calendar API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected Void doInBackground(Void... params) {
        try {
            clearResultsText();
            String calendarId = new CalendarAPIAdapter().getCalendar();
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

    /**
     * Fill the data TextView with the given List of Strings; called from
     * background threads and async tasks that need to update the UI (in the
     * UI thread).
     */
    public void updateResultsText() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mActivity.listDataHeader == null) {
                    AlertDialogPopup.ShowDialogPopup("Error", "An error was accountered retrieving data!", mActivity);
                   // mActivity.mResultsText.setText("Error retrieving data!");
                } else if (mActivity.listDataHeader.size() == 0) {
                    AlertDialogPopup.ShowDialogPopup("Alert", "No events found", mActivity);
                    //mActivity.mResultsText.setText("No events found.");
                } else {
                  // mActivity.mStatusText.setText("Data retrieved using" + " the Google Calendar API:");
//todo
                   // mActivity.mResultsText.setText(TextUtils.join("\n\n", dataStrings));
                }

                mActivity.listAdapter.refresh();
                /*if(dataStrings != null){
                    int notificationId = 001;
                    String eventText = dataStrings.get(0);
                    *//*NotificationCompat.Builder notificationBuilder =
                            new NotificationCompat.Builder(SigningOnActivity.this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle(eventText)
                                    .setContentText(eventText);

                    NotificationManagerCompat notificationManager =
                            NotificationManagerCompat.from(SigningOnActivity.this);

                    notificationManager.notify(notificationId, notificationBuilder.build());*//*

                }*/
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
                               // mActivity.mStatusText.setText(message);
            }
        });
    }

    /**
     * Clear any existing Google Calendar API data from the TextView and update
     * the header message; called from background threads and async tasks
     * that need to update the UI (in the UI thread).
     */
    public void clearResultsText() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // mActivity.mStatusText.setText("Retrieving data…");
               // mActivity.mResultsText.setText("");
            }
        });
    }

    /*private String checkForProviderCalendar(){
        String ourCalendar = "";
        String[] projection =
                new String[]{
                        Calendars._ID,
                        Calendars.NAME,
                        Calendars.ACCOUNT_NAME,
                        Calendars.ACCOUNT_TYPE};
        Cursor calCursor =
                ApplicationContextProvider.getContext().getContentResolver().query(
                        Calendars.CONTENT_URI,
                        projection,
                        Calendars.VISIBLE + " = 1",
                        null,
                                Calendars._ID + " ASC");
        if (calCursor.moveToFirst()) {
            do {
                long id = calCursor.getLong(0);
                String displayName = calCursor.getString(1);
                System.out.println("Id: " + id + " Display Name: " + displayName);
                if(displayName.equals("MARA")){
                   ourCalendar = String.valueOf(id);
                }
                ourCalendar = ourCalendar + displayName;
            } while (calCursor.moveToNext());

        }
        calCursor.close();
        return ourCalendar;
    }
*/

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
            int index = start.indexOf("T");
            start = start.substring(0, index);
            String summary =  event.getSummary();
            if(!mActivity.listDataHeader.contains(start)){
                mActivity.listDataHeader.add(start);
                ArrayList newList = new ArrayList<String>();
                newList.add(summary);
                mActivity.listDataChild.put(start, newList);
            }
            else{
                List<String> theList = mActivity.listDataChild.get(start);
                theList.add(summary);
            }
        }

    }



}

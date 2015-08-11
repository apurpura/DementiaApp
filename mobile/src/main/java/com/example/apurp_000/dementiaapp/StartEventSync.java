package com.example.apurp_000.dementiaapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

/**
 * Created by apurpura on 7/12/2015.
 */
public class StartEventSync extends AsyncTask<Void, Void, Void>{
    private static SigningOnActivity mActivity;
    private static HttpTransport httpTransport;
    private static GoogleApiClient googleClient;

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
        CalendarApiHelperAsync.refreshCredentials();
        String id = Integer.toString(new Random().nextInt());
        String calendarId = null;
        try {
            calendarId = new CalendarAPIAdapter(Credentials.signonActivity).getCalendar();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            CalendarApiHelperAsync.UpdateEvents();
            SearchForOverDueEvents();
            //Intent intent = new Intent(mActivity,CheckWearableConnection.class);
            //mActivity.startService(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        EventModel e = new EventModel(id, calendarId, "", "", "", "", "", "", 0, "x");
        AlarmManagerHelper.setAlarm(Credentials.signonActivity, getUpdateAlarmInterval(), e, "UpdateEventsService");
    }

    private static long getUpdateAlarmInterval(){
        DateTime startDateTime = new DateTime(System.currentTimeMillis() + 15000);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone(TimeZone.getDefault().toString());
        return start.getDateTime().getValue();
    }

    private static void SearchForOverDueEvents(){
        CalendarApiHelperAsync.refreshCredentials();
        EventDbHelper dbHelper = new EventDbHelper(Credentials.signonActivity);
        HashMap<String, List<EventModel>> ls = dbHelper.GetNotifyEventsWithoutEventResult(Credentials.signonActivity);
        for(List<EventModel> els :  ls.values()){
            for(EventModel em : els ){
                EventResult er = new EventResultDBHelper(Credentials.signonActivity).GetEventResult(em.Id, Credentials.signonActivity);
                if(er.eventId == null) {
                    Date endDate = null;
                    Date rightNow = null;
                    if (!em.EndTime.equals("") && !em.EndTime.toLowerCase().equals("n/a"))
                        try {
                            try {
                                endDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ").parse(em.EndTime);
                            }catch (Exception e){
                                endDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(em.EndTime);
                            }
                            //DateTime t = new DateTime(new Date(), TimeZone.getDefault());
                            rightNow = new Date();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    String dayEnd = (String) android.text.format.DateFormat.format("dd", endDate);
                    String dayNow = (String) android.text.format.DateFormat.format("dd", rightNow);
                    if (rightNow.after(endDate) && dayEnd.equals(dayNow)) {
                        Notification.sendNotification(Credentials.signonActivity, em.Action,CalendarAPIAdapter.getAccount(em.CalendarId) + " Time Exceeded");
                        dbHelper.updateNotify(Credentials.signonActivity, em.Id);
                    }
                }
            }

        }

    }


}

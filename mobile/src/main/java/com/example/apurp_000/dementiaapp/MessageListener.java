package com.example.apurp_000.dementiaapp;

import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by apurpura on 7/16/2015.
 */
public class MessageListener extends WearableListenerService {

    private JSONObject json;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("onMessageReceive", "made it");
        String path = messageEvent.getPath();
        if( path.equalsIgnoreCase("activityresult") ) {
            String message = new String(messageEvent.getData());
             try {
                 json = new JSONObject(message);
                 String action = json.get("action").toString();
                 String eventId = json.get("eventId").toString();
                 String startTime = json.get("startTime").toString();
                 String endTime = json.get("endTime").toString();
                 String cancelTime = json.get("cancelTime").toString();
                 String level = json.get("level").toString();
                 String score = json.get("score").toString();
                 Log.d("insertingEventResult db", "EventId: " + eventId + ",Action: " + action);
                 EventResultDBHelper db = new EventResultDBHelper(Credentials.signonActivity);
                 String calendarId = new CalendarAPIAdapter(Credentials.signonActivity).getCalendarId(Credentials.credential.getSelectedAccount().toString());
                 if(calendarId == ""){
                     try {
                         calendarId = new CalendarAPIAdapter(Credentials.signonActivity).getCalendar().toString();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
                 db.insertEventResult(startTime,endTime,cancelTime,level,score,action,eventId, calendarId, Credentials.signonActivity);

             } catch (JSONException e) {
                      e.printStackTrace();
                  }
        }
    }
}

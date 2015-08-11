package com.example.apurp_000.dementiaapp;

import android.util.ArrayMap;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.api.services.calendar.model.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apurpura on 7/16/2015.
 */
public class MessageListener extends WearableListenerService {

    private JSONObject json;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("onMessageReceive", "made it");
        String path = messageEvent.getPath();
        RefreshCredentialsService.refreshCredentials();
        if( path.equalsIgnoreCase("activityresult") ) {
            String message = new String(messageEvent.getData());
             try {
                 json = new JSONObject(message);
                 String action = json.get("action").toString().trim();
                 String eventId = json.get("eventId").toString().trim();
                 String startTime = json.get("startTime").toString().trim();
                 String endTime = json.get("endTime").toString().trim();
                 String cancelTime = json.get("cancelTime").toString().trim();
                 String level = json.get("level").toString().trim();
                 String score = json.get("score").toString().trim();
                 String trophy = json.get("trophy").toString().trim();
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
                 db.insertEventResult(startTime,endTime,cancelTime,level,score,action,eventId, calendarId, Credentials.signonActivity, trophy);
                 updateEventInCalendar(message, eventId,cancelTime,endTime,score,action);

             } catch (JSONException e) {
                      e.printStackTrace();
                  }
        }
    }

    private void updateEventInCalendar(String json, String eventId, String cancelTime, String endTime, String score, String action) {
        // Retrieve the event from the API
        if (Credentials.signonActivity == null)
            Credentials.signonActivity = new SigningOnActivity();
        RefreshCredentialsService.refreshCredentials();
        Event ev = null;
        for (String calId : CalendarAPIAdapter.getCalendarList().values()) {
            try {
                ev = Credentials.signonActivity.calendarService.events().get(calId, eventId).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (ev != null) {
                //add the action to extendedProperties
                Event.ExtendedProperties EP = ev.getExtendedProperties();
                Map<String, String> map = null;
                try {
                    map = EP.getShared();
                }catch(Exception e){

                }
                if(map == null)
                    map = new ArrayMap<String, String>() ;
                map.put("EventResult", json);
                EP.setShared(map);
                ev.setExtendedProperties(EP);
                // Update the event
                try {
                    Event updatedEvent = Credentials.signonActivity.calendarService.events().update(calId, eventId, ev).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.example.apurp_000.dementiaapp;

import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apurpura on 7/16/2015.
 */
public class MessageListener extends WearableListenerService {

    private JSONObject json;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("onMessageReceive", "made it");
        //if( messageEvent.getPath().equalsIgnoreCase(START_ACTIVITY) ) {
            String message = new String(messageEvent.getData());
        String action = "";
        String path = messageEvent.getPath();
       // try {
            //json = new JSONObject(message);
            //action = json.get("Action").toString();

            Log.d("Mobile Received Message", "Path: " + path + ", Message: {" + message + "}");
       // } catch (JSONException e) {
       //     e.printStackTrace();
       // }


        //} else {
           //super.onMessageReceived( messageEvent );
        //}
    }
}

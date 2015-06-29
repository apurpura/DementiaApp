package com.example.apurp_000.dementiaapp;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apurpura on 6/18/2015.
 */
public class MessageListener extends WearableListenerService {

    private static final String START_ACTIVITY = "/start_activity";
    private JSONObject json;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("onMessageReceive", "made it");
        if( messageEvent.getPath().equalsIgnoreCase(START_ACTIVITY) ) {
            final String message = new String(messageEvent.getData());

            activityFactory(message);

        } else {
            super.onMessageReceived( messageEvent );
        }
    }

    private void activityFactory(String message){
        try {
            json = new JSONObject(message);
            String action = json.get("Action").toString();
            switch (action) {
                case "Text Message":
                    startTextMessageActivity(message);
                    break;
                case "Image Carousel":
                    startCarousel();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void startTextMessageActivity(String message){
        Log.d("activatingIntent", "MainActivityIntent");
        Intent messageIntent = new Intent(this, TextMessageActivity.class);
        messageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        messageIntent.putExtra("text", message);
        this.startActivity(messageIntent);
    }

    private void startCarousel(){
        Log.d("activatingIntent", "MainActivityIntent");
        Intent messageIntent = new Intent(this, ImageCarousel.class);
        messageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(messageIntent);
    }


}

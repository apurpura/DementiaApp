package com.example.apurp_000.dementiaapp;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by apurpura on 6/18/2015.
 */
public class MessageListener extends WearableListenerService {

    private static final String START_ACTIVITY = "/start_activity";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("onMessageReceive", "made it");
        if( messageEvent.getPath().equalsIgnoreCase(START_ACTIVITY) ) {
            final String message = new String(messageEvent.getData());

            /*Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("message", message);

            Log.d("onMessageReceive", "broadcasting");
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);


            Log.d("onMessageReceive", "doneBraodcasting");*/
            Log.d("activatingIntent", "MainActivityIntent");
            Intent messageIntent = new Intent(this, MainActivity.class);
            messageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            messageIntent.putExtra("text", message);
            this.startActivity(messageIntent);
        } else {
            super.onMessageReceived( messageEvent );
        }
    }


}

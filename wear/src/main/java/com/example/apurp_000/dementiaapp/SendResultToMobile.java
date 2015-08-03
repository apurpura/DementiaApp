package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apurpura on 7/16/2015.
 */
public class SendResultToMobile implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleClient;
    private JSONObject json;
    private Activity act;

    public SendResultToMobile(ActivityResult result, Activity act){
        this.act = act;
        Log.d("SendResultToMobile", "created");
        json = new JSONObject();
        try {
            json.put("eventId", result.eventId);
            json.put("action", result.action);
            json.put("startTime", result.startTime);
            json.put("endTime", result.endTime);
            json.put("cancelTime", result.cancelTime);
            json.put("level", result.level);
            json.put("score", result.score);
            json.put("trophy", result.trophy);
            //json.put("YourValue", result.YourValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (NullPointerException n){
            n.printStackTrace();
        }
    }

    public void start(){
        Log.d("SendResultToMobile", "started");
        googleClient = new GoogleApiClient.Builder(act)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener( this)
                .build();

        googleClient.connect();
    }


    @Override
    public void onConnected(Bundle connectionHint){
        Log.d("onConnected", "connected about to send datalayer");
        new SendToDataLayerThread("ActivityResult", json.toString(), googleClient).start();
        Log.d("onConnected", "sent to data layer");
    }

    @Override
    public void onConnectionSuspended(int cause){}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){
        int notificationId = 001;
        android.support.v4.app.NotificationCompat.Builder notificationBuilder =
                null;
        try {
            notificationBuilder = new NotificationCompat.Builder(act)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(json.get("Summary").toString())
                    .setContentText(json.get("Description").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(act);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}

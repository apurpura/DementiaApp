package com.example.apurp_000.dementiaapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONException;
import org.json.JSONObject;

public class AlarmService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    public static String TAG = AlarmService.class.getSimpleName();
    private JSONObject json;
    private static final String START_ACTIVITY = "/start_activity";
    private GoogleApiClient googleClient;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("AlarmService", "ServiceStarted");
        json = new JSONObject();
        try {
            json.put("Id", intent.getExtras().get("Id"));
            json.put("CalendarId", intent.getExtras().get("CalendarId"));
            json.put("Action", intent.getExtras().get("Action"));
            json.put("Summary", intent.getExtras().get("Summary"));
            json.put("Description", intent.getExtras().get("Description"));
            json.put("StartTime", intent.getExtras().get("StartTime"));
            json.put("EndTime", intent.getExtras().get("EndTime"));
            json.put("Location", intent.getExtras().get("Location"));
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (NullPointerException n){
            n.printStackTrace();
        }
        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener( this)
                .build();

        googleClient.connect();
       // new AlertWearableAsync(intent, this).execute();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onConnected(Bundle connectionHint){
        Log.d("onConnected", "connected about to send datalayer");
        new SendToDataLayerThread(START_ACTIVITY, json.toString(), googleClient).start();
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
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(json.get("Summary").toString())
                    .setContentText(json.get("Description").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}

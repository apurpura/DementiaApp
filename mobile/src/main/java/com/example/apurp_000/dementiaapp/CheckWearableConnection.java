package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * Created by apurpura on 8/4/2015.
 */
public class CheckWearableConnection  extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private GoogleApiClient googleClient;
    static boolean sendConnectionFailedMessage = true;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener( this)
                .build();

        googleClient.connect();
        return super.onStartCommand(intent, flags, startId);
    }

    public class VerifyConnection extends Thread {

        public void run() {
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleClient).await();
        if (nodes.getNodes().size() == 0) {
            Set<String> ls = CalendarAPIAdapter.getCalendarList().keySet();
            String[] recips =  ls.toArray(new String[ls.size()]);
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, recips);
            i.putExtra(Intent.EXTRA_SUBJECT, "MARA WEARABLE DISCONNECTED");
            i.putExtra(Intent.EXTRA_TEXT, Credentials.credential.getSelectedAccountName() + " has diconnected wearable from cell phone.");
            try {
                Credentials.signonActivity.startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(Credentials.signonActivity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
            sendConnectionFailedMessage = false;
        } else
            sendConnectionFailedMessage = true;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        new VerifyConnection().start();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        int notificationId = 001;
        android.support.v4.app.NotificationCompat.Builder notificationBuilder =
                null;
        try {
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("No Internet")
                    .setContentText("Google connection Failed");
        } catch (Exception e) {
            e.printStackTrace();
        }

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

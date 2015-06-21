package com.example.apurp_000.dementiaapp;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class HelloWorldActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String START_ACTIVITY = "/start_activity";
    private GoogleApiClient googleClient;
    boolean connected;
    String nodeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
        connected = false;
        ApplicationContextProvider.setContext(this);

        Button wearButton = (Button)findViewById(R.id.wearButton);
        wearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int notificationId = 001;

                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(HelloWorldActivity.this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("HelloWorld")
                                .setContentText("OMG REALLY!");

                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(HelloWorldActivity.this);

                notificationManager.notify(notificationId, notificationBuilder.build());*/
                if(connected){
                    String message = "hello wearable\n via the data layer";
                    new SendToDataLayerThread(START_ACTIVITY, message, googleClient).start();
                }


            }
        });

        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .build();


    }

    @Override
    protected void onStart(){
        super.onStart();
        googleClient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint){
        connected = true;
    }

    @Override
    protected void onStop(){
        if(null != googleClient && googleClient.isConnected()){
            googleClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int cause){}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){}



}

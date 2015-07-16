package com.example.apurp_000.dementiaapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

public class UpdateEventsService extends Service {
    public UpdateEventsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            new StartEventSync(Credentials.signonActivity).execute();
        return super.onStartCommand(intent, flags, startId);
    }




}

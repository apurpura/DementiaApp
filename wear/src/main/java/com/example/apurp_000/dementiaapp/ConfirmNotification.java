package com.example.apurp_000.dementiaapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Ryan on 6/25/2015.
 */
public class ConfirmNotification extends Service {

    private NotificationManager zNM;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}

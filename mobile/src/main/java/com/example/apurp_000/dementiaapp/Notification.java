package com.example.apurp_000.dementiaapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import java.util.UUID;

/**
 * Created by apurpura on 8/3/2015.
 */
public class Notification {

    public static void sendNotification(Context ac, String title, String summary) {
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(ac)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(summary);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(ac, MemoryRecallAnalytics.class);

        // The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ac);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MemoryRecallAnalytics.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) ac.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        int mId = 001;
        mNotificationManager.notify(mId, mBuilder.build());
    }
}

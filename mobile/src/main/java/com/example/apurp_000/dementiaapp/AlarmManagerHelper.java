package com.example.apurp_000.dementiaapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.api.services.calendar.model.Event;

import java.util.Calendar;
import java.util.List;

public class AlarmManagerHelper extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //implement here to create alarms on phone start.
    }

    public static void setAlarm(Context context, long timeMiliseconds, EventModel m) {
        PendingIntent p = createPendingIntent(context, m);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeMiliseconds, p);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeMiliseconds, p);
        }
    }

    public static void cancelAlarm(Context context, String id) {
        EventDbHelper dbHelper = new EventDbHelper(context);

        EventModel alarm =  dbHelper.GetEvent(id, context);

        if (alarm.Id != "") {
                    PendingIntent pIntent = createPendingIntent(context, alarm);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);
        }
    }

    private static PendingIntent createPendingIntent(Context context, EventModel m) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra("Id", m.Id);
        intent.putExtra("CalandarId", m.CalendarId);
        intent.putExtra("Action", m.Action);
        intent.putExtra("Summary", m.Summary);
        intent.putExtra("Description", m.Description);
        intent.putExtra("StartTime", m.StartTime);
        intent.putExtra("EndTime", m.EndTime);
        intent.putExtra("Location", m.Location);

        return PendingIntent.getService(context, m.u_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}

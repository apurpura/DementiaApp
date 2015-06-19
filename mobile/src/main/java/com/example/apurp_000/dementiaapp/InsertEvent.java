package com.example.apurp_000.dementiaapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;

import java.io.IOException;
import java.lang.*;

/**
 * Created by Ryan on 6/18/2015.
 */
public class InsertEvent extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
        //Button publishEvent = (Button) findViewById(R.id.publishEvent);
        //publishEvent.setOnClickListener(new View.OnClickListener() {
            //public void onClick(View v) {
              //  try {
              //  } catch (Exception e) {
                //    e.printStackTrace();
              //  }
           // }
        //});
    }
    /**
     * Called whenever this activity is pushed to the foreground, such as after
     * a call to onCreate().
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (Credentials.isGooglePlayServicesAvailable(this)) {
            refreshResults();
        } else {
            String message = "\"Google Play Services required: \" +\n" +
                    "                    \"after installing, close and relaunch this app.\"";
            AlertDialogPopup.ShowDialogPopup("Alert", message, this);
        }
    }

    /**
     * Attempt to get a set of data from the Google Calendar API to display. If the
     * email address isn't known yet, then call chooseAccount() method so the
     * user can pick an account.
     */
    private void refreshResults() {
        if (Credentials.isOnline()) {
            new InsertEventHelperAsync(this).execute();
        } else {
            AlertDialogPopup.ShowDialogPopup("Alert", "No Network Connection Available.", this);
        }

    }
}


package com.example.apurp_000.dementiaapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.Calendar;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.*;
import java.util.*;

/**
 * Created by Ryan on 6/18/2015.
 */
public class InsertEvent extends Activity {
    Context context;
    int zYear, zMonth, zDay;
    int zTextField;
    static final int zDIALOG = 0;
    String action;
    Spinner actionSpinner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        //Set dialog to use current Year, Month and Day
        final java.util.Calendar cal = java.util.Calendar.getInstance();
            zYear = cal.get(java.util.Calendar.YEAR);
            zMonth = cal.get(java.util.Calendar.MONTH);
            zDay = cal.get(java.util.Calendar.DAY_OF_MONTH);
        //Execute Calendar Dialog Popup
        showDialogOnButtonClick();

        //Proceed to Publishing the Event to Calendar and Event DB
        Button publishEvent = (Button) findViewById(R.id.publishEvent);
        context = this;
        publishEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (Credentials.isOnline()) {
                        new InsertEventHelperAsync(InsertEvent.this).execute();
                    } else {
                        AlertDialogPopup.ShowDialogPopup("Alert", "No Network Connection Available.", InsertEvent.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //listen for the action selected
        action = "";
        actionSpinner = (Spinner) findViewById(R.id.actionSpinner);
        actionSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

    }

    //Create a Popup Dialog for Start Date button and End Date Buttons
    public void showDialogOnButtonClick() {
        Button eventStartDate = (Button)findViewById(R.id.eventStartDate);
        eventStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(zDIALOG);
                zTextField = 0;
            }
        });

        final Button eventEndDate = (Button)findViewById(R.id.eventEndDate);
        eventEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(zDIALOG);
                zTextField = 1;
            }
        });
    }
    //Shows the Date Picker Dialog Calendar
    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == zDIALOG)
            return new DatePickerDialog(this,dpickerListner,zYear,zMonth,zDay);
        else return null;
    }

    //Create the Listener for the dates selected, then evalutes what text field to
    //display the date.
    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            zYear = year;
            zMonth = monthOfYear + 1;
            zDay = dayOfMonth;
            TextView startView =(TextView)findViewById(R.id.startView) ;
            TextView endView = (TextView)findViewById(R.id.endView);
            if(zTextField == 0)
            {
            startView.setText(zYear + "-" + zMonth + "-" + zDay);
            }else{endView.setText(zYear + "-" + zMonth + "-" + zDay);}

            //Toast.makeText(InsertEvent.this,zYear + "-" + zMonth + "-" + zDay, Toast.LENGTH_LONG).show();
        }
    };

    /**
     * Called whenever this activity is pushed to the foreground, such as after
     * a call to onCreate().
     */
    @Override
    protected void onResume(){
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


    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

                    action = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
}


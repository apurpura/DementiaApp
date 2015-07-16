package com.example.apurp_000.dementiaapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ryan on 6/18/2015.
 */
public class InsertEvent extends Activity {
    Context context;
    int zYear, zMonth, zDay, zMinute, zHourOfDay;
    static final int zDialog = 0;
    boolean setFalse = false;

    String action;
    Spinner actionSpinner;
    String account;
    Spinner accountSpinner;
    ArrayAdapter<String> dataAdapter;
    List<String> lables;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        //Set date dialog to use current Year, Month and Day
        //Set time dialog to use current Hour of Day and Minute
        final java.util.Calendar cal = java.util.Calendar.getInstance();
            zYear = cal.get(Calendar.YEAR);
            zMonth = cal.get(Calendar.MONTH);
            zDay = cal.get(Calendar.DAY_OF_MONTH);
            zMinute = cal.get(Calendar.MINUTE);
            zHourOfDay = cal.get(Calendar.HOUR_OF_DAY);

        //Execute Calendar Dialog Popup
        showDialog();

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

        if(Credentials.credential != null)
            account = Credentials.credential.getSelectedAccountName();
        else {
            Intent intent = new Intent(this,SigningOnActivity.class);
            startActivity(intent);
        }
        // Spinner Drop down elements
        lables = new ArrayList<>();

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner = (Spinner) findViewById(R.id.account_spinner);
        // attaching data adapter to spinner
        accountSpinner.setAdapter(dataAdapter);
        accountSpinner.setOnItemSelectedListener(new AccountOnItemSelectedListener() );


        //listen for the action selected
        action = "";
        actionSpinner = (Spinner) findViewById(R.id.actionSpinner);
        actionSpinner.setOnItemSelectedListener(new ActionOnItemSelectedListener());

    }

    //Create a Popup Dialog for Start Date and End Date text fields
    public void showDialog() {
        EditText eventStartDate = (EditText)findViewById(R.id.startView);
        eventStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(zDialog);
                setFalse = true;
            }
        });

        EditText eventEndDate = (EditText)findViewById(R.id.endView);
        eventEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(zDialog);
            }
        });
    }
    //Shows the Date Picker Dialog Calendar
    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == zDialog)
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
            EditText eventStartDate = (EditText)findViewById(R.id.startView);
            EditText eventEndDate = (EditText)findViewById(R.id.endView);
            if(setFalse)
            {
                eventStartDate.setText(zYear + "-" + zMonth + "-" + zDay);
                setFalse = false;
            }else{eventEndDate.setText(zYear + "-" + zMonth + "-" + zDay);}

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
            new CalendarListAsync(InsertEvent.this).execute();
    }

    public class ActionOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

                    action = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    public class AccountOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

            account = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
}


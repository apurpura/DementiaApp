package com.example.apurp_000.dementiaapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.api.client.util.DateTime;

import java.lang.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ryan on 6/18/2015.
 */
public class InsertEventActivity extends IActivity {
    Context context;
    int zYear, zMonth, zDay, zMinute, zHourOfDay;
    String zStartDate, zStartTime, zEndDate, zEndTime;
    static final int zDateDialog = 0;
    static final int zTimeDialog = 1;
    boolean setFalse = false;
    boolean checked = false;

    String action;
    Spinner actionSpinner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
        Credentials.signonActivity.refreshCalendarService();
        final EditText summary = (EditText)findViewById(R.id.eventTitle);
        final EditText description = (EditText)findViewById(R.id.eventDescription);

        //Set date dialog to use current Year, Month and Day
        //Set time dialog to use current Hour of Day and Minute
        final java.util.Calendar cal = java.util.Calendar.getInstance();
            zYear = cal.get(Calendar.YEAR);
            zMonth = cal.get(Calendar.MONTH);
            zDay = cal.get(Calendar.DAY_OF_MONTH);
            zMinute = cal.get(Calendar.MINUTE);
            zHourOfDay = cal.get(Calendar.HOUR_OF_DAY);

        //set up hamburger menu btn
        setHamBtnlistners();


        //Execute Calendar Dialog Popup
        showDialog();

        //Proceed to Publishing the EventModel to Calendar and EventModel DB
        Button publishEvent = (Button) findViewById(R.id.publishEvent);
        context = this;
        publishEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Check Summary and Description fields for Text or do not publish
                if(summary.getText().toString().equals("") && description.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), "Fill All Required Fields", Toast.LENGTH_SHORT).show();
                }else if (summary.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), "Fill All Required Fields", Toast.LENGTH_SHORT).show();
                }else if (description.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), "Fill All Required Fields", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        if (Credentials.isOnline()) {
                            new InsertEventHelperAsync(InsertEventActivity.this).execute();
                        } else {
                            AlertDialogPopup.ShowDialogPopup("Alert", "No Network Connection Available.", InsertEventActivity.this);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        }
                    }
            }
        });

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

    public void setHamBtnlistners(){

        Button hamButton = (Button) findViewById(R.id.HamburgerButtonInsertEvent);
        hamButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Pop UP Menu
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);

                //inflater open menu
                popupMenu.inflate(R.menu.hamburger_menu_insertitempage);

                //show menu
                popupMenu.show();

                //set up listners for the hamburger menu tiles
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item1:
                                Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item2:
                                Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item3:
                                Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });

            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        for (int i=0; i<menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            String title = mi.getTitle().toString();
            Spannable newTitle = new SpannableString(title);
            newTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, newTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mi.setTitle(newTitle);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hamburger_menu_insertitempage, menu);
        return true;
    }

    //Create a Popup Dialog for Start Date and End Date text fields
    public void showDialog() {
        EditText eventStartDate = (EditText)findViewById(R.id.startView);
        eventStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(zDateDialog);
                setFalse = true;
            }
        });

        EditText eventEndDate = (EditText)findViewById(R.id.endView);
        eventEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(zDateDialog);
            }
        });

        EditText eventStartTime = (EditText)findViewById(R.id.startTimeView);
        eventStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(zTimeDialog);
                setFalse = true;
            }
        });

        EditText eventEndTime = (EditText)findViewById(R.id.endTimeView);
        eventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(zTimeDialog);
            }
        });
    }
    //Shows the Date Picker Dialog Calendar
    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == zDateDialog)
            return new DatePickerDialog(this,dpickerListner,zYear,zMonth,zDay);
        else {
            if (id == zTimeDialog)
            return new TimePickerDialog(this, tPickerListen, zHourOfDay, zMinute, false);
        }
        return null;
    }

    //Create the Listener for the dates selected, then evalutes what text field to
    //Display the date.
    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            zYear = year;
            zMonth = monthOfYear + 1;
            zDay = dayOfMonth;
            EditText eventStartDate = (EditText)findViewById(R.id.startView);
            EditText eventEndDate = (EditText)findViewById(R.id.endView);
            if(setFalse)
            {   if (zMonth < 10) {
                eventStartDate.setText(zYear + "-0" + zMonth + "-" + zDay);
                zStartDate = eventStartDate.getText().toString();
                setFalse = false;
                } if (zDay < 10) {eventStartDate.setText(zYear + "-" + zMonth + "-0" + zDay);
                zStartDate = eventStartDate.getText().toString();
                setFalse = false;} if (zDay < 10 && zMonth < 10)
                {eventStartDate.setText(zYear + "-0" + zMonth + "-0" + zDay);
                zStartDate = eventStartDate.getText().toString();
                setFalse = false;
                }
                else
                eventStartDate.setText(zYear + "-" + zMonth + "-" + zDay);
                zStartDate = eventStartDate.getText().toString();
                setFalse = false;
            }else{
                if (zMonth < 10) {
                    eventEndDate.setText(zYear + "-0" + zMonth + "-" + zDay);
                    zEndDate = eventEndDate.getText().toString();
                } if (zDay < 10) {eventEndDate.setText(zYear + "-" + zMonth + "-0" + zDay);
                    zEndDate = eventEndDate.getText().toString();
                    } if (zDay < 10 && zMonth < 10)
                {eventEndDate.setText(zYear + "-0" + zMonth + "-0" + zDay);
                    zEndDate = eventEndDate.getText().toString();
                }
                else
                    eventEndDate.setText(zYear + "-" + zMonth + "-" + zDay);
                    zEndDate = eventEndDate.getText().toString();

            }
        }
    };

    //Create the Listener for the times selected, then evalutes what text field to
    //Display the time.
    private TimePickerDialog.OnTimeSetListener tPickerListen = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            zMinute = minute;
            zHourOfDay = hourOfDay;
            EditText eventStartTime = (EditText)findViewById(R.id.startTimeView);
            EditText eventEndTime = (EditText)findViewById(R.id.endTimeView);
            if(setFalse)
            {if(zMinute < 10){eventStartTime.setText(zHourOfDay + ":0" + zMinute + ":" + "00");
                zStartTime = eventStartTime.getText().toString();
                setFalse = false;}if(zHourOfDay < 10){eventStartTime.setText("0" + zHourOfDay + ":" + zMinute + ":" + "00");
                zStartTime = eventStartTime.getText().toString();
                setFalse = false;}if(zMinute < 10 && zHourOfDay < 10){eventStartTime.setText("0" + zHourOfDay + ":0" + zMinute + ":" + "00");
                zStartTime = eventStartTime.getText().toString();
                setFalse = false;
            }else{
                eventStartTime.setText(zHourOfDay + ":" + zMinute + ":" + "00");
                zStartTime = eventStartTime.getText().toString();
                setFalse = false;}
            }else{
                if(zMinute < 10){eventEndTime.setText(zHourOfDay + ":0" + zMinute + ":" + "00");
                    zEndTime = eventEndTime.getText().toString();
                    }if(zHourOfDay < 10){eventEndTime.setText("0" + zHourOfDay + ":" + zMinute + ":" + "00");
                    zEndTime = eventEndTime.getText().toString();
                        }if(zMinute < 10 && zHourOfDay < 10){eventEndTime.setText("0" + zHourOfDay + ":0" + zMinute + ":" + "00");
                        zEndTime = eventEndTime.getText().toString();
                }else{
                eventEndTime.setText(zHourOfDay + ":" + zMinute + ":" + "00");
                zEndTime = eventEndTime.getText().toString();}
            }
        }
    };

    //Format Start Date and Time to be used in StartDateTime Event Method
    public Date zGetStartDate (){
        SimpleDateFormat zSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date zEventStartDateTime = null;
        try {
            zEventStartDateTime = zSDF.parse(zStartDate + "T" + zStartTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return zEventStartDateTime;
        };
        //Format End Date and Time to be used in EndDateTime of Event Method
    public Date zGetEndDate (){
        SimpleDateFormat zEDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date zEventEndDateTime = null;
        try {
            zEventEndDateTime = zEDF.parse(zEndDate + "T" + zEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return zEventEndDateTime;
    };

    /**
     * Called whenever this activity is pushed to the foreground, such as after
     * a call to onCreate().
     */
    @Override
    protected void onResume(){
        super.onResume();
        Credentials.signonActivity.refreshCalendarService();
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
            new CalendarListAsync(InsertEventActivity.this).execute();
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

            Account.account = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        checked = ((CheckBox) view).isChecked();
        EditText endDate = (EditText) findViewById(R.id.endView);
        EditText endTime = (EditText) findViewById(R.id.endTimeView);
        if(checked){
            endDate.setVisibility(View.VISIBLE);
            endTime.setVisibility(View.VISIBLE);
        }
        else{
            endDate.setVisibility(View.INVISIBLE);
            endTime.setVisibility(View.INVISIBLE);
        }



    }
}


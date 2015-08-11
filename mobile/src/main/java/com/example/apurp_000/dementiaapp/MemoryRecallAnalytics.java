package com.example.apurp_000.dementiaapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MemoryRecallAnalytics extends IActivity {
    public LinearLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalendarApiHelperAsync.refreshCredentials();
        setContentView(R.layout.activity_memory_recall_analytics);
        rl = (LinearLayout) findViewById(R.id.rellay);

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


        //pop up chart when user click on title
        //popUpTitleChartText();
    }



    /**
     * Called whenever this activity is pushed to the foreground, such as after
     * a call to onCreate().
     */
    @Override
    protected void onResume(){
        super.onResume();
        CalendarApiHelperAsync.refreshCredentials();
        if (Credentials.isGooglePlayServicesAvailable(this)) {
            refreshResults();
        } else {
            String message = "\"Google Play Services required: \" +\n" +
                    "                    \"after installing, close and relaunch this app.\"";
            AlertDialogPopup.ShowDialogPopup("Alert", message, this);
        }
        //new MemoryRecallAnalyticsHelperAsync(MemoryRecallAnalytics.this).execute();
    }

    /**
     * Attempt to get a set of data from the Google Calendar API to display. If the
     * email address isn't known yet, then call chooseAccount() method so the
     * user can pick an account.
     */
    private void refreshResults() {
        new CalendarListAsync(MemoryRecallAnalytics.this).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memory_recall_analytics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class AccountOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            boolean shouldRefresh = true;
            if(Account.account == parent.getItemAtPosition(pos).toString())
                shouldRefresh = false;
            Account.account = parent.getItemAtPosition(pos).toString();
            if(shouldRefresh)
                new MemoryRecallAnalyticsHelperAsync(MemoryRecallAnalytics.this).execute();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }






}

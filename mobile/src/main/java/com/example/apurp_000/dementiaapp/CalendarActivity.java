package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CalendarActivity extends IActivity {
    CalendarView calendar;
    //TextView mResultsText;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_list);
        ApplicationContextProvider.setContext(this);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        if(Credentials.credential != null ) {
            if(Account.account == null)
                Account.account = Credentials.credential.getSelectedAccountName();
        }
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
    }

    /**
     * Called whenever this activity is pushed to the foreground, such as after
     * a call to onCreate().
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (Credentials.isGooglePlayServicesAvailable(this)) {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();
            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);
            refreshResults();
            ExpandableListView listView = (ExpandableListView) findViewById(R.id.lvExp);
            int count = listDataHeader.size();
            for (int position = 1; position <= count; position++)
                listView.expandGroup(position - 1);
            this.listAdapter.refresh();
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
            new CalendarListAsync(CalendarActivity.this).execute();
        } else {
            AlertDialogPopup.ShowDialogPopup("Alert", "No Network Connection Available.", this);
        }

    }

    private void callAsyncTask(){
        if (Credentials.isOnline()) {
            AsyncTask t = new CalendarApiHelperAsync(this).execute();
        } else {
            AlertDialogPopup.ShowDialogPopup("Alert", "No Network Connection Available.", this);
        }
    }

    public class AccountOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            boolean shouldRefresh = true;
            if(Account.account == parent.getItemAtPosition(pos).toString())
                shouldRefresh = false;
            Account.account = parent.getItemAtPosition(pos).toString();
            if(shouldRefresh)
                callAsyncTask();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }


}

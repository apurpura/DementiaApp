package com.example.apurp_000.dementiaapp;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.api.client.http.HttpTransport;
import com.google.api.services.calendar.Calendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apurpura on 7/12/2015.
 */
public class CalendarListAsync extends AsyncTask<Void, Void, Void> {
    private IActivity mActivity;
    private List<String> theList;
    public CalendarListAsync(IActivity activity){
        mActivity = activity;
    }

    /**
     * Background task to call Google Calendar API.
     *
     * @param params no parameters needed for this task.
     */
    @Override
    protected Void doInBackground(Void... params) {
        theList = new ArrayList(CalendarAPIAdapter.getCalendarList().keySet());
        if(Account.account != null){
            int index = theList.indexOf(Account.account);
            String item = theList.get(index);
            theList.remove(index);
            theList.add(0, item);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, theList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mActivity.accountSpinner.setAdapter(dataAdapter);
    }
}

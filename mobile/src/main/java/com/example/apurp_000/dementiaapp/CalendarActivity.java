package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


//import com.google.api.client.util.DateTime;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;


public class CalendarActivity extends IActivity {
    CalendarView calendar;
    //TextView mResultsText;
    //ExpandableListAdapter listAdapter;
   // ArrayAdapter<String> itemsAdapter;
    // ListView expListView;
    // List<String> listDataHeader;
    public DateTime eventDate;
    public DateTime eventDateMax;
    //HashMap<String, List<String>> listDataChild;
    public RecyclerAdapter rAdapter;
    public RecyclerView rView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_list);
        ApplicationContextProvider.setContext(this);
        //expListView = (ListView) findViewById(R.id.lvExp);

        //set up hamburger menu btn
        setHamBtnlistners();

        eventDate = new DateTime().withTimeAtStartOfDay();
        eventDateMax = eventDate.plusDays(1).withTimeAtStartOfDay();

        rView = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rView.setLayoutManager(llm);
        rAdapter = new RecyclerAdapter(new ArrayList<EventModel>(), CalendarActivity.this);
        rView.setAdapter(rAdapter);

        SwipeDismissRecyclerViewTouchListener swipeTouchListener =
                new SwipeDismissRecyclerViewTouchListener(rView,
                        new SwipeDismissRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    //mItems.remove(position);
                                    ArrayList<EventModel> events=  CalendarApiHelperAsync.eList;
                                    String eventId = events.get(position).Id;
                                    new DeleteEventHelperAsyc(CalendarActivity.this,eventId ).execute();
                                    events.remove(position);
                                    RecyclerAdapter rAdapter = new RecyclerAdapter(events, CalendarActivity.this);
                                    rView.setAdapter(rAdapter);
                                }
                                rAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                Toast.makeText(CalendarActivity.this, "Swipe card left to delete event.", Toast.LENGTH_LONG).show();
                            }
                        });

        rView.addOnItemTouchListener(swipeTouchListener);



        RefreshCredentialsService.refreshCredentials();

        // Spinner Drop down elements
        ArrayList<String> lables = new ArrayList<String>();
        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner = (Spinner) findViewById(R.id.account_spinner);
        // attaching data adapter to spinner
        accountSpinner.setAdapter(dataAdapter);
        accountSpinner.setOnItemSelectedListener(new AccountOnItemSelectedListener());
        initializeCalendar();
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
        getMenuInflater().inflate(R.menu.hamburger_menu_calenderpage, menu);
        return true;
    }

    public void setHamBtnlistners(){

        Button hamButton = (Button) findViewById(R.id.HamburgerButtoncalendarPAge);
        hamButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Pop UP Menu
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);

                //inflater open menu
                popupMenu.inflate(R.menu.hamburger_menu);


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

    /**
     * Called whenever this activity is pushed to the foreground, such as after
     * a call to onCreate().
     */
    @Override
    protected void onResume() {
        super.onResume();
        RefreshCredentialsService.refreshCredentials();
        if (Credentials.isGooglePlayServicesAvailable(this)) {
            //listDataHeader = new ArrayList<String>();
            //itemsAdapter =
                   // new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listDataHeader);
            //listDataChild = new HashMap<String, List<String>>();
            //listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
            //expListView.setAdapter(itemsAdapter);
            refreshResults();
            //ExpandableListView listView = (ExpandableListView) findViewById(R.id.lvExp);
//            int count = listDataHeader.size();
//            for (int position = 1; position <= count; position++)
//                listView.expandGroup(position - 1);
        } else {
            String message = "\"Google Play Services required: \" +\n" +
                    "                    \"after installing, close and relaunch this app.\"";
            AlertDialogPopup.ShowDialogPopup("Alert", message, this);
        }
    }

    private void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendar);

        // sets whether to show the week number.
        calendar.setShowWeekNumber(false);

        // sets the first day of week according to Calendar.
        // here we set Monday as the first day of the Calendar
        calendar.setFirstDayOfWeek(2);

        //The background color for the selected week.
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));

        //sets the color for the dates of an unfocused month.
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));

        //sets the color for the separator line between weeks.
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));

        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
        calendar.setSelectedDateVerticalBar(R.color.darkgreen);

        //sets the listener to be notified upon selected date change.
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                GregorianCalendar gc = new GregorianCalendar(TimeZone.getDefault());
                gc.clear();
                gc.set(year, month, day);
                GregorianCalendar start = new GregorianCalendar (gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH), 0, 0);
                GregorianCalendar end = new GregorianCalendar (gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH), 11, 59);
                end.setTimeZone(TimeZone.getDefault());
                start.setTimeZone(TimeZone.getDefault());
                long left = start.getTimeInMillis();
                eventDate = new DateTime(left);
                long right = end.getTimeInMillis();
                eventDateMax = new DateTime(right);
                callAsyncTask();
            }
        });
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

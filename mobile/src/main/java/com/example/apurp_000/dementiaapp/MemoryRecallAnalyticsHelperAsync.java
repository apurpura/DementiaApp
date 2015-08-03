package com.example.apurp_000.dementiaapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apurpura on 7/29/2015.
 */
public class MemoryRecallAnalyticsHelperAsync extends AsyncTask<Void, Void, Void> {
    private static MemoryRecallAnalytics mActivity;
    private static HttpTransport httpTransport;
    private Map<String, List<EventResult>> er;

    /**
     * Constructor.
     * @param activity SigningOnActivity that spawned this task.
     */
    MemoryRecallAnalyticsHelperAsync(MemoryRecallAnalytics activity) {
        this.mActivity = activity;
        try {
            httpTransport = AndroidHttp.newCompatibleTransport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Background task to call Google Calendar API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected Void doInBackground(Void... params) {
        try {
            Credentials.signonActivity.refreshCalendarService();
            updateEventResults();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void updateEventResults(){
        EventResultDBHelper db = new EventResultDBHelper(Credentials.signonActivity);
        final String calId = CalendarAPIAdapter.getCalendarList().get(Account.account);
        er = db.GetEventResults(Credentials.signonActivity,calId);
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                //GridLayout gv = mActivity.gv;

                mActivity.gv.removeAllViews();
                for (String key : er.keySet()) {

                    mActivity.gv.setColumnCount(2);
                    mActivity.gv.setRowCount(100);

                    //action
                    TextView action = new TextView(mActivity);
                    action.setText(key);
                    action.setTextSize(18);
                    action.setClickable(true);
                    action.setPaintFlags(action.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                    action.setTextColor(Color.BLUE);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    action.setTypeface(null, Typeface.BOLD);
                    action.setLayoutParams(params);
                    mActivity.gv.addView(action, params);
                    final String theA = key;
                    action.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mActivity, AnalyticChartPage.class);
                            Intent text = intent.putExtra("text", theA);
                            intent.putExtra("calendar", calId);
                            //start Memory
                            mActivity.startActivity(intent);
                        }
                    });
                    //blank view
                    TextView blank = new TextView(mActivity);
                    mActivity.gv.addView(blank);
                    TextView blank2 = new TextView(mActivity);
                    mActivity.gv.addView(blank2);
                    TextView blank3 = new TextView(mActivity);
                    mActivity.gv.addView(blank3);

                    List<EventResult> ls = er.get(key);
                    EventResult evR = getEventWithScore(ls);
                    if(evR.score > 0){
                        //high score text
                        GridLayout.LayoutParams params11 = new GridLayout.LayoutParams();
                        params11.setMargins(90,0,40,0);
                        TextView hs = new TextView(mActivity);
                        hs.setTypeface(null, Typeface.BOLD);
                        hs.setText("High Score: ");
                        hs.setTextSize(14);
                        hs.setLayoutParams(params11);
                        mActivity.gv.addView(hs, params11);

                        //high score value
                        String hsv = getHighestScore(ls);
                        TextView hcv = new TextView(mActivity);
                        hcv.setText(hsv);
                        hcv.setTypeface(null, Typeface.BOLD);
                        hcv.setTextSize(13);
                        GridLayout.LayoutParams params12 = new GridLayout.LayoutParams();
                        hcv.setLayoutParams(params12);
                        mActivity.gv.addView(hcv, params12);

                        //last scoretext
                        GridLayout.LayoutParams params9 = new GridLayout.LayoutParams();
                        params9.setMargins(90, 0, 40, 0);
                        TextView st = new TextView(mActivity);
                        st.setText("Last Score: ");
                        st.setTextSize(14);
                        st.setTypeface(null, Typeface.BOLD);
                        st.setLayoutParams(params9);
                        mActivity.gv.addView(st, params9);

                        //last score value
                        TextView lcv = new TextView(mActivity);
                        Integer lastScore = ls.get(ls.size()-1).score;
                        lcv.setText(lastScore.toString());
                        lcv.setTextSize(13);
                        lcv.setTypeface(null, Typeface.BOLD);
                        GridLayout.LayoutParams params10 = new GridLayout.LayoutParams();
                        lcv.setLayoutParams(params10);
                        mActivity.gv.addView(lcv, params10);

                    }

                    //Last start time text
                    GridLayout.LayoutParams params5 = new GridLayout.LayoutParams();
                    params5.setMargins(90,0,40,0);
                    TextView startTime = new TextView(mActivity);
                    startTime.setTypeface(null, Typeface.BOLD);
                    startTime.setText("Last Start Time: ");
                    startTime.setTextSize(14);
                    startTime.setLayoutParams(params5);
                    mActivity.gv.addView(startTime, params5);
                    //startDateTime
                    TextView startDateTime = new TextView(mActivity);
                    startDateTime.setTypeface(null, Typeface.BOLD);
                    if (ls.get(ls.size() - 1).startTime != null)
                        startDateTime.setText(DateFormat.getDateTimeInstance().

                                format(ls.get(ls.size()

                                        - 1).startTime));
                    startDateTime.setTextSize(13);
                    GridLayout.LayoutParams params3 = new GridLayout.LayoutParams();
                    startDateTime.setLayoutParams(params3);
                    mActivity.gv.addView(startDateTime, params3);

                    //last end time text
                    GridLayout.LayoutParams params2 = new GridLayout.LayoutParams();
                    params2.setMargins(90,0,40,0);
                    TextView endTime = new TextView(mActivity);
                    endTime.setTypeface(null, Typeface.BOLD);
                    endTime.setText("Last End Time: ");
                    endTime.setTextSize(14);
                    endTime.setLayoutParams(params2);
                    mActivity.gv.addView(endTime, params2);

                    //endDateTime
                    TextView endDateTime = new TextView(mActivity);
                    endDateTime.setTypeface(null, Typeface.BOLD);
                    if (ls.get(ls.size() - 1).endTime != null)
                        endDateTime.setText(DateFormat.getDateTimeInstance().

                                format(ls.get(ls.size()

                                        - 1).endTime));
                    endDateTime.setTextSize(13);
                    GridLayout.LayoutParams params6 = new GridLayout.LayoutParams();
                    endDateTime.setLayoutParams(params6);
                    mActivity.gv.addView(endDateTime, params6);

                    //was last cancelled text
                    GridLayout.LayoutParams params7 = new GridLayout.LayoutParams();
                    params7.setMargins(90,0,40,0);
                    TextView wc = new TextView(mActivity);
                    wc.setTypeface(null, Typeface.BOLD);
                    wc.setText("Was Last Canceled: ");
                    wc.setTextSize(14);
                    wc.setLayoutParams(params7);
                    mActivity.gv.addView(wc, params7);

                    //was cancelled yes/no
                    TextView wcv = new TextView(mActivity);
                    wcv.setTypeface(null, Typeface.BOLD);
                    String wasCanceled = "NO";
                    if (ls.get(ls.size() - 1).cancelTime != null)
                        wasCanceled = "YES";
                    wcv.setText(wasCanceled);
                    wcv.setTextSize(13);
                    GridLayout.LayoutParams params8 = new GridLayout.LayoutParams();
                    wcv.setLayoutParams(params8);
                    mActivity.gv.addView(wcv, params8);

                    //blank view
                    TextView blank4 = new TextView(mActivity);
                    mActivity.gv.addView(blank4);
                    TextView blank5 = new TextView(mActivity);
                    mActivity.gv.addView(blank5);
                }
            }});
    }

    protected String getHighestScore(List<EventResult> ls){
        Integer highest = 0;
        for(EventResult e : ls){
            if(e.score > highest){
                highest = e.score;
            }
        }
        return highest.toString();
    }

    protected EventResult getEventWithScore(List<EventResult> ls){
        EventResult er = ls.get(ls.size()-1);
        if(er.score > 0)
                return er;
        for(int i = ls.size()-1; i < 0; i--){
            if(ls.get(i).score > 0){
                er = ls.get(i);
                break;
            }
        }
        return er;
    }


}


package com.example.apurp_000.dementiaapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
        er = db.GetEventResults(Credentials.signonActivity,CalendarAPIAdapter.getCalendarList().get(Account.account));
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                //GridLayout gv = mActivity.gv;
                Integer actionRow = 3;
                for (String key : er.keySet()) {

                    mActivity.gv.setColumnCount(4);
                    mActivity.gv.setRowCount(100);

                    //action
                    TextView action = new TextView(mActivity);
                    action.setText(key);
                    action.setTextSize(14);
                    action.setTextColor(Color.BLUE);
                    GridLayout.Spec row4 = GridLayout.spec(actionRow);
                    GridLayout.Spec col4 = GridLayout.spec(0);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams(row4, col4);
                    action.setLayoutParams(params);
                    mActivity.gv.addView(action, params);

                    List<EventResult> ls = er.get(key);
                    EventResult evR = getEventWithScore(ls);
                    if(evR.score > 0){
                        //high score text
                        actionRow++;
                        GridLayout.Spec row11 = GridLayout.spec(actionRow);
                        GridLayout.Spec col11 = GridLayout.spec(1);
                        GridLayout.LayoutParams params11 = new GridLayout.LayoutParams(row11, col11);
                        TextView hs = new TextView(mActivity);
                        hs.setText("High Score: ");
                        hs.setTextSize(12);
                        hs.setLayoutParams(params11);
                        mActivity.gv.addView(hs, params11);

                        //high score value
                        String hsv = getHighestScore(ls);
                        TextView hcv = new TextView(mActivity);
                        hcv.setText(hsv);
                        hcv.setTextSize(12);
                        GridLayout.Spec row12 = GridLayout.spec(actionRow);
                        GridLayout.Spec col12 = GridLayout.spec(2);
                        GridLayout.LayoutParams params12 = new GridLayout.LayoutParams(row12, col12);
                        hcv.setLayoutParams(params12);
                        mActivity.gv.addView(hcv, params12);

                        //last scoretext
                        actionRow++;
                        GridLayout.Spec row9 = GridLayout.spec(actionRow);
                        GridLayout.Spec col9 = GridLayout.spec(1);
                        GridLayout.LayoutParams params9 = new GridLayout.LayoutParams(row9, col9);
                        TextView st = new TextView(mActivity);
                        st.setText("Last Score: ");
                        st.setTextSize(12);
                        st.setLayoutParams(params9);
                        mActivity.gv.addView(st, params9);

                        //last score value
                        TextView lcv = new TextView(mActivity);
                        Integer lastScore = ls.get(ls.size()-1).score;
                        lcv.setText(lastScore.toString());
                        lcv.setTextSize(12);
                        GridLayout.Spec row10 = GridLayout.spec(actionRow);
                        GridLayout.Spec col10 = GridLayout.spec(2);
                        GridLayout.LayoutParams params10 = new GridLayout.LayoutParams(row10, col10);
                        lcv.setLayoutParams(params10);
                        mActivity.gv.addView(lcv, params10);

                    }

                    //Last start time text
                    actionRow++;
                    GridLayout.Spec row5 = GridLayout.spec(actionRow);
                    GridLayout.Spec col5 = GridLayout.spec(1);
                    GridLayout.LayoutParams params5 = new GridLayout.LayoutParams(row5, col5);
                    TextView startTime = new TextView(mActivity);
                    startTime.setText("Last Start Time: ");
                    startTime.setTextSize(12);
                    startTime.setLayoutParams(params5);
                    mActivity.gv.addView(startTime, params5);
                    //startDateTime
                    TextView startDateTime = new TextView(mActivity);
                    if (ls.get(ls.size() - 1).startTime != null)
                        startDateTime.setText(DateFormat.getDateTimeInstance().

                                format(ls.get(ls.size()

                                        - 1).startTime));
                    startDateTime.setTextSize(12);
                    GridLayout.Spec row1 = GridLayout.spec(actionRow);
                    GridLayout.Spec col1 = GridLayout.spec(2);
                    GridLayout.LayoutParams params3 = new GridLayout.LayoutParams(row1, col1);
                    startDateTime.setLayoutParams(params3);
                    mActivity.gv.addView(startDateTime, params3);

                    //last end time text
                    actionRow++;
                    GridLayout.Spec row = GridLayout.spec(actionRow);
                    GridLayout.Spec col = GridLayout.spec(1);
                    GridLayout.LayoutParams params2 = new GridLayout.LayoutParams(row, col);
                    TextView endTime = new TextView(mActivity);
                    endTime.setText("Last End Time: ");
                    endTime.setTextSize(12);
                    endTime.setLayoutParams(params2);
                    mActivity.gv.addView(endTime, params2);

                    //endDateTime
                    TextView endDateTime = new TextView(mActivity);
                    if (ls.get(ls.size() - 1).endTime != null)
                        endDateTime.setText(DateFormat.getDateTimeInstance().

                                format(ls.get(ls.size()

                                        - 1).endTime));
                    endDateTime.setTextSize(12);
                    GridLayout.Spec row2 = GridLayout.spec(actionRow);
                    GridLayout.Spec col2 = GridLayout.spec(2);
                    GridLayout.LayoutParams params6 = new GridLayout.LayoutParams(row2, col2);
                    endDateTime.setLayoutParams(params6);
                    mActivity.gv.addView(endDateTime, params6);

                    //was last cancelled text
                    actionRow++;
                    GridLayout.Spec row7 = GridLayout.spec(actionRow);
                    GridLayout.Spec col7 = GridLayout.spec(1);
                    GridLayout.LayoutParams params7 = new GridLayout.LayoutParams(row7, col7);
                    TextView wc = new TextView(mActivity);
                    wc.setText("Was Last Canceled: ");
                    wc.setTextSize(12);
                    wc.setLayoutParams(params7);
                    mActivity.gv.addView(wc, params7);

                    //was cancelled yes/no
                    TextView wcv = new TextView(mActivity);
                    String wasCanceled = "NO";
                    if (ls.get(ls.size() - 1).cancelTime != null)
                        wasCanceled = "YES";
                    wcv.setText(wasCanceled);
                    wcv.setTextSize(12);
                    GridLayout.Spec row8 = GridLayout.spec(actionRow);
                    GridLayout.Spec col8 = GridLayout.spec(2);
                    GridLayout.LayoutParams params8 = new GridLayout.LayoutParams(row8, col8);
                    wcv.setLayoutParams(params8);
                    mActivity.gv.addView(wcv, params8);

                    actionRow++;
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


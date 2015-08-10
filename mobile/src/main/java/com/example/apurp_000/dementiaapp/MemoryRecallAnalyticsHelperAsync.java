package com.example.apurp_000.dementiaapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    private Integer bScore = 0;
    private Integer sScore = 0;
    private Integer gScore = 0;

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
        final String calId = CalendarAPIAdapter.getCalendarList().get(Account.account);
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                //GridLayout gv = mActivity.gv;
                EventResultDBHelper db = new EventResultDBHelper(Credentials.signonActivity);
                er = db.GetEventResults(Credentials.signonActivity,calId);
                mActivity.rl.removeAllViews();
                Integer actionId = View.generateViewId();
                for (String key : er.keySet()) {
                    List<EventResult> ls = er.get(key);
                    String hsv = getHighestScore(ls);
                    GridLayout gv = new GridLayout(mActivity);
                    gv.setColumnCount(2);
                    gv.setRowCount(6);
                    LinearLayout.LayoutParams o = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
                    Display display = wm.getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int width = size.x;
                    o.width = width;
                    gv.setLayoutParams(o);


                    //gv.setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.FILL_PARENT, GridLayout.LayoutParams.FILL_PARENT));

                    //action
                    TextView action = new TextView(mActivity);
                    action.setText(key);
                    action.setId(actionId);
                    action.setTextSize(18);
                    LinearLayout.LayoutParams rp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    action.setTypeface(null, Typeface.BOLD);
                    action.setLayoutParams(rp);
                    mActivity.rl.addView(action, rp);

                    //blank view
                    TextView blank = new TextView(mActivity);
                    mActivity.rl.addView(blank);

                   /* TextView blank2 = new TextView(mActivity);
                    gv.addView(blank2);
                    TextView blank3 = new TextView(mActivity);
                    gv.addView(blank3);*/


                    EventResult evR = getEventWithScore(ls);
                    if(evR.score > 0){
                        action.setClickable(true);
                        action.setPaintFlags(action.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        action.setTextColor(Color.BLUE);
                        //rp.addRule(RelativeLayout.ALIGN_LEFT);
                        //rp.addRule(RelativeLayout.BELOW, actionId);

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

                        GridLayout gvT = new GridLayout(mActivity);
                        gvT.setColumnCount(3);
                        gvT.setRowCount(2);
                        LinearLayout.LayoutParams o2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        o2.width = width;
                        gv.setLayoutParams(o2);
                        mActivity.rl.addView(gvT);
                        //blank view
                        TextView blank2 = new TextView(mActivity);
                        mActivity.rl.addView(blank2);



                        //GridLayout.LayoutParams paramsgvt = new GridLayout.LayoutParams();
                        //WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
                        //Display display = wm.getDefaultDisplay();
                        //Point size = new Point();
                        //display.getSize(size);
                        //int width = size.x;
                        //paramsgvt.width = width;
                        //gvT.setLayoutParams(paramsgvt);

                        //goldTrophy
                        ImageView g = new ImageView(mActivity);
                        g.setImageResource(R.drawable.goldtrophy);
                        GridLayout.LayoutParams gp = new GridLayout.LayoutParams();
                        gp.setMargins(90, 0, 0, 0);
                        gp.width = 200;
                        gp.height = 200;
                        g.setLayoutParams(gp);
                        gvT.addView(g);


                        //silverTrophy
                        ImageView s = new ImageView(mActivity);
                        s.setImageResource(R.drawable.silvertrophy);
                        GridLayout.LayoutParams sp = new GridLayout.LayoutParams();
                        sp.setMargins(157,0,0,0);
                        sp.width = 200;
                        sp.height = 200;
                        s.setLayoutParams(sp);
                        gvT.addView(s);



                        //bronzeTrophy
                        ImageView b = new ImageView(mActivity);
                        b.setImageResource(R.drawable.bronzetrophy);
                        GridLayout.LayoutParams bp = new GridLayout.LayoutParams();
                        bp.setMargins(155,0,90,0);
                        bp.width = 200;
                        bp.height = 200;
                        b.setLayoutParams(bp);
                        gvT.addView(b);

                        TextView gNum = new TextView(mActivity);
                        GridLayout.LayoutParams gNump = new GridLayout.LayoutParams();
                        gNump.setMargins(177, 0, 0, 0);
                        gNum.setLayoutParams(gNump);
                        gNum.setText(gScore.toString());
                        gvT.addView(gNum);

                        TextView sNum = new TextView(mActivity);
                        GridLayout.LayoutParams sNump = new GridLayout.LayoutParams();
                        sNump.setMargins(240, 0, 0, 0);
                        sNum.setLayoutParams(sNump);
                        sNum.setText(sScore.toString());
                        gvT.addView(sNum);

                        TextView bNum = new TextView(mActivity);
                        GridLayout.LayoutParams bNump = new GridLayout.LayoutParams();
                        bNump.setMargins(240, 0, 177, 0);
                        bNum.setLayoutParams(bNump);
                        bNum.setText(bScore.toString());
                        gvT.addView(bNum);

                        //high score text
                        GridLayout.LayoutParams params11 = new GridLayout.LayoutParams();
                        params11.setMargins(90,0,40,0);
                        TextView hs = new TextView(mActivity);
                        hs.setTypeface(null, Typeface.BOLD);
                        hs.setText("High Score: ");
                        hs.setTextSize(14);
                        hs.setLayoutParams(params11);
                        gv.addView(hs, params11);

                        //high score value
                        TextView hcv = new TextView(mActivity);
                        hcv.setText(hsv);
                        hcv.setTypeface(null, Typeface.BOLD);
                        hcv.setTextSize(13);
                        GridLayout.LayoutParams params12 = new GridLayout.LayoutParams();
                        hcv.setLayoutParams(params12);
                        gv.addView(hcv, params12);

                        //last scoretext
                        GridLayout.LayoutParams params9 = new GridLayout.LayoutParams();
                        params9.setMargins(90, 0, 40, 0);
                        TextView st = new TextView(mActivity);
                        st.setText("Last Score: ");
                        st.setTextSize(14);
                        st.setTypeface(null, Typeface.BOLD);
                        st.setLayoutParams(params9);
                        gv.addView(st, params9);

                        //last score value
                        TextView lcv = new TextView(mActivity);
                        Integer lastScore = ls.get(ls.size()-1).score;
                        lcv.setText(lastScore.toString());
                        lcv.setTextSize(13);
                        lcv.setTypeface(null, Typeface.BOLD);
                        GridLayout.LayoutParams params10 = new GridLayout.LayoutParams();
                        lcv.setLayoutParams(params10);
                        gv.addView(lcv, params10);

                    }
                    mActivity.rl.addView(gv);

                    //Last start time text
                    GridLayout.LayoutParams params5 = new GridLayout.LayoutParams();
                    params5.setMargins(90,0,40,0);
                    TextView startTime = new TextView(mActivity);
                    startTime.setTypeface(null, Typeface.BOLD);
                    startTime.setText("Last Start Time: ");
                    startTime.setTextSize(14);
                    startTime.setLayoutParams(params5);
                    gv.addView(startTime, params5);
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
                    gv.addView(startDateTime, params3);

                    //last end time text
                    GridLayout.LayoutParams params2 = new GridLayout.LayoutParams();
                    params2.setMargins(90,0,40,0);
                    TextView endTime = new TextView(mActivity);
                    endTime.setTypeface(null, Typeface.BOLD);
                    endTime.setText("Last End Time: ");
                    endTime.setTextSize(14);
                    endTime.setLayoutParams(params2);
                    gv.addView(endTime, params2);

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
                    gv.addView(endDateTime, params6);

                    //was last cancelled text
                    GridLayout.LayoutParams params7 = new GridLayout.LayoutParams();
                    params7.setMargins(90,0,40,0);
                    TextView wc = new TextView(mActivity);
                    wc.setTypeface(null, Typeface.BOLD);
                    wc.setText("Was Last Canceled: ");
                    wc.setTextSize(14);
                    wc.setLayoutParams(params7);
                    gv.addView(wc, params7);

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
                    gv.addView(wcv, params8);

                    //blank view
                    TextView blank4 = new TextView(mActivity);
                    gv.addView(blank4);
                    TextView blank5 = new TextView(mActivity);
                    gv.addView(blank5);
                }
            }});
    }

    protected String getHighestScore(List<EventResult> ls){
        gScore = 0;
        sScore = 0;
        bScore = 0;
        Integer highest = 0;
        for(EventResult e : ls){
            if(e.score > highest){
                highest = e.score;
            }
            try {
                if (e.trophy.equals("1"))
                    gScore++;
                if (e.trophy.equals("2"))
                    sScore++;
                if (e.trophy.equals("3"))
                    bScore++;
            }catch (Exception k){
                //just keep it going
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


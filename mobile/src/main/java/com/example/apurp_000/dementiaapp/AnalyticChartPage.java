package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.TextView;

import com.androidplot.Plot;
import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnalyticChartPage extends Activity {

    XYPlot plot;
    String activityName;
    String calendarId;
    Map<String, List<EventResult>> er;
    List<Number> dates = new ArrayList<>();
    Map<String, Integer> scores = new HashMap<String, Integer>() ;


    public void onCreate(Bundle savedInstanceState) {

        //get passed intent - name of activity
        Intent intent = getIntent();
        activityName = intent.getStringExtra("text");
        calendarId = intent.getStringExtra("calendar");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytic_chart_page);

        //Change chart name depending on what activity it is in
        plot= (XYPlot) findViewById(R.id.mySimpleXYPlot);
        getDomainAndRange();
        //give value for debug incase dev launches analyticchartpage directly without going through memory recall analytics where a intent is passed
        if(activityName!=null){
            plot.setTitle(activityName);
        }else{
            plot.setTitle("Debug chart");
        }

        //change label names the series lines
        plot.setDomainLabel("Date");
        plot.setRangeLabel("Score");



        // fun little snippet that prevents users from taking screenshots
        // on ICS+ devices :-)
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                //WindowManager.LayoutParams.FLAG_SECURE);



        // create our series from our array of nums:
        XYSeries series2 = new SimpleXYSeries(
                dates,
                new ArrayList<Integer>(scores.values()),
                "Scores over Time")
                ;

        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(this,
                R.xml.line_point_formatter_with_plf1);

        // add a new series' to the xyplot:
        plot.addSeries(series2, series1Format);

        // reduce the number of range labels
        if(dates.size() <=10)
            plot.setDomainStep(XYStepMode.SUBDIVIDE, dates.size());
        else
            plot.setDomainStep(XYStepMode.SUBDIVIDE, 10);
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1);
        plot.getGraphWidget().setDomainLabelOrientation(-45);
        plot.setDomainValueFormat(new GraphXValueFormat());
        plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        //This gets rid of the black border (up to the graph) there is no black border around the labels
        plot.getBackgroundPaint().setColor(Color.TRANSPARENT);

//This gets rid of the black behind the graph
        plot.getGraphWidget().getBackgroundPaint().setColor(Color.TRANSPARENT);

//With a new release of AndroidPlot you have to also set the border paint
        plot.getBorderPaint().setColor(Color.TRANSPARENT);
        plot.setBorderStyle(Plot.BorderStyle.NONE, null, null);
        plot.getDomainLabelWidget().getLabelPaint().setColor(Color.BLACK);
        plot.getRangeLabelWidget().getLabelPaint().setColor(Color.BLACK);
        plot.getTitleWidget().getLabelPaint().setColor(Color.BLACK);
        plot.getGraphWidget().getRangeLabelPaint().setColor(Color.BLACK);
        plot.getGraphWidget().getDomainLabelPaint().setColor(Color.BLACK);
        plot.getLegendWidget().getTextPaint().setColor(Color.BLACK);
    }

    protected void getDomainAndRange() {

        RefreshCredentialsService.refreshCredentials();
        EventResultDBHelper db = new EventResultDBHelper(Credentials.signonActivity);
        er = db.GetEventResults(Credentials.signonActivity, calendarId);
        scores.clear();
        dates.clear();
                for (String key : er.keySet()) {
                    if (key.equals(activityName)) {
                        List<EventResult> ers = er.get(key);
                        for (EventResult e : ers) {
                            if(!scores.containsKey(stringDateValue(e.startTime.getTime()))) {
                                scores.put(stringDateValue(e.startTime.getTime()),e.score);
                                dates.add(e.startTime.getTime());
                            }else{
                                Integer hs = scores.get(stringDateValue(e.startTime.getTime()));
                                if(e.score > hs){
                                    scores.put(stringDateValue(e.startTime.getTime()), e.score);
                                }
                            }
                        }
                    }
                }
        Collections.sort(dates, new Comparator<Number>() {
            @Override
            public int compare(Number o1, Number o2) {
                Long d1 = o1.longValue();
                Long d2 = o2.longValue();
                return d1.compareTo(d2);

            }
        });
    }

    protected String stringDateValue(Long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(timestamp);
        return dateFormat.format(date);
    }
}







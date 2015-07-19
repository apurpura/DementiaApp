package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.TextView;

import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;
import java.util.Arrays;

public class AnalyticChartPage extends Activity {

    private XYPlot plot;
     String activityName;

    public void onCreate(Bundle savedInstanceState) {
        //get passed intent - name of activity
        Intent intent = getIntent();
        activityName = intent.getStringExtra("text");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.analytic_xy_plot_chart);

        //Change chart name depending on what activity it is in
        XYPlot plot = (XYPlot) findViewById(R.id.graphPlot);
        //give value for debug incase dev launches analyticchartpage directly without going through memory recall analytics where a intent is passed
        if(activityName!=null){
            plot.setTitle(activityName +" chart");
        }else{
            plot.setTitle("Debug chart");
        }

        //change label names the series lines
        //plot.setDomainLabel("TestDomain");
        //plot.setRangeLabel("TestRange");



        //return to menu on click
        closeChartListener();

        // fun little snippet that prevents users from taking screenshots
        // on ICS+ devices :-)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
               WindowManager.LayoutParams.FLAG_SECURE);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.graphPlot);

        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};

        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series

        // same as above
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_plf1);

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);

        // same as above:
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_plf2);
        plot.addSeries(series2, series2Format);

        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);


    }
    public void closeChartListener(){
        final Button exitchart = (Button)findViewById(R.id.closechartbutton);
        exitchart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //return to menu
                Intent intent = new Intent(AnalyticChartPage.this, MemoryRecallAnalytics.class);
                startActivity(intent);


            }
        });
    }


}

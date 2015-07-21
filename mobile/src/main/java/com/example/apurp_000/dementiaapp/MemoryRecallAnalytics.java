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
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MemoryRecallAnalytics extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_recall_analytics);
        EventResultDBHelper db = new EventResultDBHelper(Credentials.signonActivity);
        Map<String, List<EventResult>> er = db.GetEventResults(Credentials.signonActivity);
        GridLayout gv = (GridLayout) findViewById(R.id.grid);
        Integer actionRow = 3;
        for (String key : er.keySet()) {
            TextView action = new TextView(this);
            action.setText(key);
            action.setTextSize(14);
            action.setTextColor(Color.BLUE);
            GridLayout.Spec row4 = GridLayout.spec(actionRow);
            GridLayout.Spec col4 = GridLayout.spec(0);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(row4, col4);

            action.setLayoutParams(params);
            gv.addView(action, params);


           // <TextView
            //android:layout_width="wrap_content"
            //android:layout_height="wrap_content"
            //android:text="Simon Says"
            //android:id="@+id/simonSaysTitle"
            //android:layout_row="3"
            //android:layout_column="1"
            //android:textSize="10dp"
            //android:textColor="@drawable/analytictitleselector"
            //android:onClick="onClick"
            //android:clickable="true"
            //android:linksClickable="true" />

            List<EventResult> ls = er.get(key);
            gv.setColumnCount(4);
            gv.setRowCount(100);
            actionRow++;
                GridLayout.Spec row5 = GridLayout.spec(actionRow);
                GridLayout.Spec col5 = GridLayout.spec(1);
                GridLayout.LayoutParams params5 = new GridLayout.LayoutParams(row5, col5);
                TextView startTime = new TextView(this);
                startTime.setText("Last Start Time: ");
                //endTime.setText(ls.get(ls.size() - 1).endTime.toString());
                startTime.setTextSize(12);

                startTime.setLayoutParams(params5);
                gv.addView(startTime, params5);

                TextView startDateTime = new TextView(this);
                if(ls.get(ls.size() - 1).startTime != null)
                    startDateTime.setText(DateFormat.getDateTimeInstance().format(ls.get(ls.size() - 1).startTime));
                startDateTime.setTextSize(12);
                GridLayout.Spec row1 = GridLayout.spec(actionRow);
                GridLayout.Spec col1 = GridLayout.spec(2);
                GridLayout.LayoutParams params3 = new GridLayout.LayoutParams(row1, col1);

                startDateTime.setLayoutParams(params3);
                gv.addView(startDateTime, params3);
            actionRow++;
                GridLayout.Spec row = GridLayout.spec(actionRow);
                GridLayout.Spec col = GridLayout.spec(1);
                GridLayout.LayoutParams params2 = new GridLayout.LayoutParams(row, col);
                TextView endTime = new TextView(this);
                endTime.setText("Last End Time: ");
                //endTime.setText(ls.get(ls.size() - 1).endTime.toString());
                endTime.setTextSize(12);

                endTime.setLayoutParams(params2);
                gv.addView(endTime, params2);

                TextView endDateTime = new TextView(this);
                if(ls.get(ls.size() - 1).endTime != null)
                    endDateTime.setText(DateFormat.getDateTimeInstance().format(ls.get(ls.size() - 1).endTime));
                endDateTime.setTextSize(12);
                GridLayout.Spec row2 = GridLayout.spec(actionRow);
                GridLayout.Spec col2 = GridLayout.spec(2);
                GridLayout.LayoutParams params6 = new GridLayout.LayoutParams(row2, col2);

                endDateTime.setLayoutParams(params6);
                gv.addView(endDateTime, params6);
            actionRow++;
                GridLayout.Spec row7 = GridLayout.spec(actionRow);
                GridLayout.Spec col7 = GridLayout.spec(1);
                GridLayout.LayoutParams params7 = new GridLayout.LayoutParams(row7, col7);
                TextView wc = new TextView(this);
                wc.setText("Was Lsst Canceled: ");
                //endTime.setText(ls.get(ls.size() - 1).endTime.toString());
                wc.setTextSize(12);

                wc.setLayoutParams(params7);
                gv.addView(wc, params7);

                TextView wcv = new TextView(this);
                String wasCanceled = "NO";
                if(ls.get(ls.size() - 1).cancelTime != null)
                        wasCanceled = "YES";
                wcv.setText(wasCanceled);
                wcv.setTextSize(12);
                GridLayout.Spec row8 = GridLayout.spec(actionRow);
                GridLayout.Spec col8 = GridLayout.spec(2);
                GridLayout.LayoutParams params8 = new GridLayout.LayoutParams(row8, col8);

                wcv.setLayoutParams(params8);
                gv.addView(wcv, params8);
            actionRow++;
        }



        //pop up chart when user click on title
        //popUpTitleChartText();
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


    /*public void popUpTitleChartText() {
        //simon title
         TextView simonTitle = (TextView) findViewById(R.id.simonSaysTitle);
         simonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent simonIntent = new Intent(getApplicationContext(), AnalyticChartPage.class);
                simonIntent.putExtra("text", "Simon Game");
                //start Memory
                startActivity(simonIntent);
            }
        });
        //simon title
        TextView memoryTitle = (TextView) findViewById(R.id.memoryGameTitle);
        memoryTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent memoryIntent = new Intent(getApplicationContext(), AnalyticChartPage.class);
                memoryIntent.putExtra("text", "Memory Game");
                //start Memory
                startActivity(memoryIntent);
            }
        });
        //simon title
        TextView pillsTitle = (TextView) findViewById(R.id.takePillsTitle);
        pillsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pillsIntent = new Intent(getApplicationContext(), AnalyticChartPage.class);
                pillsIntent.putExtra("text", "Pill Time");
                //start Memory
                startActivity(pillsIntent);
            }
        });
        //simon title
        TextView getDressedTitle = (TextView) findViewById(R.id.getDressedTitle);
        getDressedTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dressedIntent = new Intent(getApplicationContext(), AnalyticChartPage.class);
                dressedIntent.putExtra("text", "Get Dressed");
                //start dressed
                startActivity(dressedIntent);
            }
        });
        //simon title
        TextView imageCarouselTitle = (TextView) findViewById(R.id.imageCarouselTitle);
        imageCarouselTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent carouselIntent = new Intent(getApplicationContext(), AnalyticChartPage.class);
                carouselIntent.putExtra("text", "Image Carousel");
                //start Carousel
                startActivity(carouselIntent);

            }
        });
    }*/


}

package com.example.apurp_000.dementiaapp;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MemoryRecallAnalytics extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_recall_analytics);

        //pop up chart when user click on title
        popUpTitleChartText();
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


    public void popUpTitleChartText() {
        //simon title
         TextView simonTitle = (TextView) findViewById(R.id.simonSaysTitle);
         simonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent simonIntent = new Intent(getApplicationContext(), AnalyticChartPage.class);
                simonIntent.putExtra("text", "simon");
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
                memoryIntent.putExtra("text", "memory");
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
                pillsIntent.putExtra("text", "pills");
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
                dressedIntent.putExtra("text", "dressed");
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
                carouselIntent.putExtra("text", "carousel");
                //start Carousel
                startActivity(carouselIntent);

            }
        });
    }


}

package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class AnalyticChartPage extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytic_chart_page);

        final ImageButton startGame = (ImageButton)findViewById(R.id.chartPage);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //close
                finish();


            }
        });
    }
}

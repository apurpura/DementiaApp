package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ryan on 6/21/2015.
 */
public class MainMenu extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button viewCalendar = (Button) findViewById(R.id.viewCalendar);
        viewCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,CalendarActivity.class);
                startActivity(intent);
            }
        });
        Button insertEvent = (Button) findViewById(R.id.insertEvent);
        insertEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,InsertEvent.class);
                startActivity(intent);
            }
        });
        Button triggerNotification = (Button) findViewById(R.id.triggerNotification);
        triggerNotification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //new Intent(this,triggerNotification.class);
            }
        });
        Button triggerGame = (Button) findViewById(R.id.triggerGame);
        triggerGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new Intent(this,triggerGame.class);
            }
        });
    }

}

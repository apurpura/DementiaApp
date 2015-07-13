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

        /*Button call for viewing the calender of the logged in use.
        * Need to address how to change the calendar being viewed*/
        Button viewCalendar = (Button) findViewById(R.id.viewCalendar);
        viewCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,CalendarActivity.class);
                startActivity(intent);
            }
        });
        /*Button call for creating an event in the calender of the logged in use.*/
        Button insertEvent = (Button) findViewById(R.id.insertEvent);
        insertEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,InsertEvent.class);
                startActivity(intent);
            }
        });

         /*Button call for triggering notifications.
        * Need to address how the flow of this*/
        Button triggerNotification = (Button) findViewById(R.id.triggerNotification);
        triggerNotification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //new Intent(this,triggerNotification.class);
            }
        });
        /*Button call for triggering games to start on wearable.
        * Need to push a game on the wearable and figure the flow
        * on notifying and opening the app how the flow of this*/
        Button triggerGame = (Button) findViewById(R.id.memoryAnalytics);
        triggerGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new Intent(this,triggerGame.class);
            }
        });
    }

}

package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.android.gms.maps.MapsInitializer;

/**
 * Created by Ryan on 6/21/2015.
 */
public class MainMenu extends Activity {



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);



        //listners for menu buttons
        setUpBtnlistners();
    }

    public void setUpBtnlistners(){


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
                Intent intent = new Intent(MainMenu.this,InsertEventActivity.class);
                startActivity(intent);
            }
        });

        /*Button call for triggering games to start on wearable.
        * Need to push a game on the wearable and figure the flow
        * on notifying and opening the app how the flow of this*/
        Button triggerGame = (Button) findViewById(R.id.memoryAnalytics);
        triggerGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,MemoryRecallAnalytics.class);
                startActivity(intent);
                //new Intent(this,triggerGame.class);
            }
        });
    }
}

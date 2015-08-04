package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.maps.MapsInitializer;

/**
 * Created by Ryan on 6/21/2015.
 */
public class MainMenu extends Activity {



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //set up hamburger menu btn
       setHamBtnlistners();

        //listners for main menu buttons
        setUpBtnlistners();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        for (int i=0; i<menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            String title = mi.getTitle().toString();
            Spannable newTitle = new SpannableString(title);
            newTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, newTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mi.setTitle(newTitle);
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hamburger_menu, menu);
        return true;
    }

    public void setHamBtnlistners(){

        Button hamButton = (Button) findViewById(R.id.HamburgerButton);
        hamButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                 //Pop UP Menu
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);

                //inflater open menu
                popupMenu.inflate(R.menu.hamburger_menu);


                //show menu
                 popupMenu.show();

                //set up listners for the hamburger menu tiles
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                       switch (item.getItemId()) {
                            case R.id.item1:
                                Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item2:
                                Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item3:
                                Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
                                return true;
                       }



                        return false;
                    }
                });

            }
        });
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

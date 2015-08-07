package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.wearable.view.WatchViewStub;
import android.text.format.Time;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PillSequence extends Activity {

    private TextView mTextView;
    int currentPill = 0;
    public String zStartTime;
    public String zEndTime;
    public String zCancelTime = "n/a";
    public String zTResults = "";
    Date zStart;
    Date zEnd;
    boolean notFinished = true;
    GenerateTime zGetTimes = new GenerateTime();
    TextMessageActivity zTMA = new TextMessageActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_sequence);
        zStartTime = zGetTimes.generateTimes();
        zStart = new Date();
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                //Button to transvers list of pills
                tapToConitinue();
            }
        });
    }

    //Should the patient just cancel the activity
    protected void onDestroy(){
        if(notFinished){
            zCancelTime = zGetTimes.generateTimes();
            zEndTime = "n/a";
            generateAnalytics();
        }
        super.onDestroy();
    }

    public void tapToConitinue(){

        ImageButton continueButton = (ImageButton)findViewById(R.id.imageButton7);

        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                switch (currentPill){

                    case 0:
                        currentPill++;
                        ImageButton continueButton = (ImageButton)findViewById(R.id.imageButton7);
                        continueButton.setImageResource(R.drawable.pillone);

                        break;
                    case 1:
                        currentPill++;;
                        continueButton = (ImageButton)findViewById(R.id.imageButton7);
                        continueButton.setImageResource(R.drawable.pilltwo);
                        break;
                    case 2:
                        currentPill++;
                        continueButton = (ImageButton)findViewById(R.id.imageButton7);
                        continueButton.setImageResource(R.drawable.pillthree);
                        break;
                    case 3:
                        currentPill++;
                        continueButton = (ImageButton)findViewById(R.id.imageButton7);
                        continueButton.setImageResource(R.drawable.pillendpage);
                        break;
                    case 4:

                        //Ryan fake variable
                        //Integer fakeEndTime = 4;
                        zEnd = new Date();
                        zEndTime = zGetTimes.generateTimes();

                        if(zTrophieTimes() <= 3){

                            String goldText = "gold";
                            // start end page
                            Intent trophyIntent = new Intent(getApplicationContext(), TrophyPage.class);
                            trophyIntent.putExtra("text", goldText);
                            zTResults = "1";
                            generateAnalytics();
                            //start memory end page
                            startActivity(trophyIntent);
                            //finish
                            finish();

                        }else if (zTrophieTimes() <= 4){


                            String silverText = "silver";
                            // start end page
                            Intent trophyIntent = new Intent(getApplicationContext(), TrophyPage.class);
                            trophyIntent.putExtra("text", silverText);
                            zTResults = "2";
                            generateAnalytics();
                            //start memory end page
                            startActivity(trophyIntent);

                            //finish
                            finish();

                        }else if (zTrophieTimes() <= 5){
                            String bronzeText = "bronze";
                            // start end page
                            Intent trophyIntent = new Intent(getApplicationContext(), TrophyPage.class);
                            trophyIntent.putExtra("text", bronzeText);
                            zTResults = "3";
                            generateAnalytics();
                            //start memory end page
                            startActivity(trophyIntent);

                            //finish
                            finish();

                        }
                        else {
                            generateAnalytics();
                            //no trophy
                            finish();
                        }
                        notFinished = false;
                        break;


                }

            }
        });
    }

    public void generateAnalytics() {

        String StartTime = zStartTime ;
        String EndTime = zEndTime;
        String CancelTime = zCancelTime;
        String Level = "n/a";
        String Score = "n/a";
        String Action = "Pill Sequence";
        String EventId = zTMA.id;
        String Trophy = zTResults;

        ActivityResult zResults = new ActivityResult(StartTime, EndTime, CancelTime, Level, Score, Action, EventId, Trophy);
        new SendResultToMobile(zResults,this).start();
    }

    public long zTrophieTimes(){
        TimeUnit timeUnit = TimeUnit.MINUTES;
        long diffInMillies = zEnd.getTime() - zStart.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

}

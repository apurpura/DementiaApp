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

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PillSequence extends Activity {

    private TextView mTextView;
    int currentPill = 0;
    public String zStartTime;
    public String zEndTime;
    public String zCancelTime = "";
    public String zTResults = "";
    Date zStart;
    Date zEnd;
    boolean notFinished = true;
    GenerateTime zGetTimes = new GenerateTime();
    private String id = "";


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
        Intent intent = getIntent();
        String m = intent.getStringExtra("text");
        if(m != null) {
            try {
                JSONObject json = new JSONObject(m);
                id = json.get("Id").toString();
            } catch (Exception e) {
                ///keep going
            }
        }
    }

    //Should the patient just cancel the activity
    protected void onDestroy(){
        if(notFinished){
            zCancelTime = zGetTimes.generateTimes();
            zEndTime = "";
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
                            zTResults = "1";
                            notFinished = false;
                            generateAnalytics();
                            Intent trophyIntent = new Intent(getApplicationContext(), TrophyPage.class);
                            trophyIntent.putExtra("text", goldText);
                            //start memory end page
                            startActivity(trophyIntent);
                            //finish
                            finish();

                        }else if (zTrophieTimes() <= 4){

                            String silverText = "silver";
                            // start end page
                            zTResults = "2";
                            notFinished = false;
                            generateAnalytics();
                            Intent trophyIntent = new Intent(getApplicationContext(), TrophyPage.class);
                            trophyIntent.putExtra("text", silverText);
                            //start memory end page
                            startActivity(trophyIntent);

                            //finish
                            finish();

                        }else if (zTrophieTimes() <= 5){
                            String bronzeText = "bronze";
                            // start end page
                            zTResults = "3";
                            notFinished = false;
                            generateAnalytics();
                            Intent trophyIntent = new Intent(getApplicationContext(), TrophyPage.class);
                            trophyIntent.putExtra("text", bronzeText);
                            //start memory end page
                            startActivity(trophyIntent);
                            //finish
                            finish();
                        }
                        else {
                            notFinished = false;
                            generateAnalytics();
                            //no trophy
                            finish();
                        }
                        break;
                }

            }
        });
    }

    public void generateAnalytics() {

        String StartTime = zStartTime ;
        String EndTime = zEndTime;
        String CancelTime = zCancelTime;
        String Level = "";
        String Score = "";
        String Action = "Pill Sequence";
        String EventId = id;
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

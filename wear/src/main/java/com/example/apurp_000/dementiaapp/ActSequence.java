package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ActSequence extends Activity {

    private TextView mTextView;
    int currentPic = 0;
    public String zStartTime;
    public String zEndTime;
    Date zStart;
    Date zEnd;
    public String zCancelTime = "";
    public String zTResults = "";
    boolean notFinished = true;
    GenerateTime zGetTimes = new GenerateTime();
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sequence);
        zStartTime = zGetTimes.generateTimes();
        zStart = new Date();
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                //Button to transvers list
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

        ImageButton continueButton = (ImageButton)findViewById(R.id.imageButton6);

        continueButton.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {

                   switch (currentPic){

                       case 0:
                           currentPic++;
                           ImageButton continueButton = (ImageButton)findViewById(R.id.imageButton6);
                           continueButton.setImageResource(R.drawable.underwear);

                           break;
                       case 1:
                           currentPic++;;
                           continueButton = (ImageButton)findViewById(R.id.imageButton6);
                           continueButton.setImageResource(R.drawable.brah);
                           break;
                       case 2:
                           currentPic++;
                           continueButton = (ImageButton)findViewById(R.id.imageButton6);
                           continueButton.setImageResource(R.drawable.socks);
                           break;
                       case 3:
                           currentPic++;
                           continueButton = (ImageButton)findViewById(R.id.imageButton6);
                           continueButton.setImageResource(R.drawable.pants);
                           break;
                       case 4:
                           currentPic++;
                           continueButton = (ImageButton)findViewById(R.id.imageButton6);
                           continueButton.setImageResource(R.drawable.sweater);
                           break;
                       case 5:
                           currentPic++;
                           continueButton = (ImageButton)findViewById(R.id.imageButton6);
                           continueButton.setImageResource(R.drawable.slippers);
                           break;
                       case 6:
                           currentPic++;
                           continueButton = (ImageButton)findViewById(R.id.imageButton6);
                           continueButton.setImageResource(R.drawable.finishedsequence);
                           break;
                       case 7:
                           //Ryan fake variable
                           //Integer fakeEndTime = 3;
                           zEnd = new Date();
                           zEndTime = zGetTimes.generateTimes();

                           if(zTrophieTimes() <= 3){

                               String goldText = "gold";
                               // start end page
                               Intent trophyIntent = new Intent(getApplicationContext(), TrophyPage.class);
                               trophyIntent.putExtra("text", goldText);
                               zTResults = "1";
                               notFinished = false;
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
                               notFinished = false;
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
                               notFinished = false;
                               generateAnalytics();
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
    //Export the Analytics to the ActivityResults to be sent
    public void generateAnalytics() {

        String StartTime = zStartTime ;
        String EndTime = "";
        String CancelTime = zCancelTime;
        String Level = "";
        String Score = "";
        String Action = "Dress Sequence";
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

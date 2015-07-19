package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class PillSequence extends Activity {

    private TextView mTextView;
    int currentPill = 0;
    public String zStartTime;
    public String zEndTime;
    public String zCancelTime = "n/a";
    boolean notFinished = true;
    GenerateTime zGetTimes = new GenerateTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_sequence);
        zStartTime = zGetTimes.generateTimes();
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
                        zEndTime = zGetTimes.generateTimes();
                        generateAnalytics();
                        notFinished = false;
                        finish();
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
        String EventId = "FigureOutEvenID02";

        ActivityResult zResults = new ActivityResult(StartTime, EndTime, CancelTime, Level, Score, Action, EventId);
        new SendResultToMobile(zResults,this).start();
    }

}

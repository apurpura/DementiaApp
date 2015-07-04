package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class simonSaysActivity extends Activity {

    private TextView mTextView;

    MediaPlayer yellowMediaPlayer;
    MediaPlayer blueMediaPlayer;
    MediaPlayer greenMediaPlayer;
    MediaPlayer redMediaPlayer;
    MediaPlayer win;
    MediaPlayer loose;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_says);

        yellowMediaPlayer = MediaPlayer.create(this, R.raw.asharp);
        blueMediaPlayer = MediaPlayer.create(this, R.raw.dsharp);
        redMediaPlayer = MediaPlayer.create(this, R.raw.csharp);
        greenMediaPlayer = MediaPlayer.create(this, R.raw.fsharp);
        win = MediaPlayer.create(this, R.raw.applause);
        loose = MediaPlayer.create(this, R.raw.aww);


        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                //startPage
                startActivity(new Intent(getApplicationContext(),startPage.class));

                //UI
                mTextView = (TextView) stub.findViewById(R.id.text);
                onClickGreen();
                onClickRed();
                onClickBlue();
                onClickYellow();

                //GameLoop
                gameFlow();

            }
        });


    }

    protected void onClickGreen(){
        final ImageButton greenButton3 = (ImageButton)findViewById(R.id.imageButton);
        //Proceed to Publishing the Event to Calendar and Event DB
        greenButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //sound
                greenMediaPlayer.start();

            }
        });
    }

    protected void onClickRed(){
        final ImageButton redButton = (ImageButton)findViewById(R.id.imageButton3);
        //Proceed to Publishing the Event to Calendar and Event DB
        redButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //sound
               redMediaPlayer.start();
            }
        });
    }

    protected void onClickYellow(){
        final ImageButton yellowButton2 = (ImageButton)findViewById(R.id.imageButton2);

        //Proceed to Publishing the Event to Calendar and Event DB
        yellowButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //debug close game
                finish();
                //sound
                yellowMediaPlayer.start();

            }
        });
    }
    public void onClickBlue(){
        final ImageButton blueButton4 = (ImageButton)findViewById(R.id.imageButton4);
        //Proceed to Publishing the Event to Calendar and Event DB
        blueButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //sound
                blueMediaPlayer.start();
            }
        });
    }
    protected void gameFlow(){
        //global level, highest level and streak
        simonSaysGlobalVars global = simonSaysGlobalVars.getInstance();
        global.setLevelData(1);

        int gameLevel = simonSaysGlobalVars.getInstance().getLevelData();
        int gameStreak = simonSaysGlobalVars.getInstance().getStreakData();
        int totalWins = simonSaysGlobalVars.getInstance().getTotalWinsData();
        int totalLost = simonSaysGlobalVars.getInstance().getTotalLostData();
        int highestLevel = simonSaysGlobalVars.getInstance().getlevelHighestData();


        //level flow and streak level
        int flowLevel = gameLevel;
        int levelStreak = gameStreak;



        //game loop

        //Won continuing playing var for main loop
        boolean nextLevel = true;
        boolean playerWon = true;

        do {
//debug purpose - to keep from infinte loop
nextLevel = false;

            switch (flowLevel){
                case 1:
                case 2:
                case 3:
                    if (!playerWon) {
                        //level streak reset
                        levelStreak = 0;
                        //level loose
                        totalLost++;
                        //store data
                        global.setToalLostData(totalLost);

                        nextLevel = false;
                    }else {
                        //level win ++
                        flowLevel++;
                        levelStreak++;
                        totalWins++;
                        //store data
                        global.setLevelData(flowLevel);
                        global.setToalWinsData(totalWins);

                        if (gameStreak < levelStreak) {
                            //send streak data
                            global.setStreakData(levelStreak);
                        }

                        if (highestLevel < flowLevel){
                            global.setlevelHighestData(flowLevel);
                        }


                    }
                    //add a user reply continue yes or no to break loop
                    break;

                case 4:
                case 5:
                case 6:

                    break;


            }
        }while (nextLevel);


    }
}

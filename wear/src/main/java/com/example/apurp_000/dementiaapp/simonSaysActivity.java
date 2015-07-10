package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//import java.util.logging.Handler;


public class simonSaysActivity extends Activity {

    private TextView mTextView;
    public  int i1;

    //sound - implement when speakers are avail
    //MediaPlayer yellowMediaPlayer;
    //MediaPlayer blueMediaPlayer;
    //MediaPlayer greenMediaPlayer;
    //MediaPlayer redMediaPlayer;
    //MediaPlayer win;
    //MediaPlayer loose;

    //vibrate
    //private Vibrator greenVibrator;
    //private Vibrator redVibrator;
    //private Vibrator blueVibrator;
    //private Vibrator yellowVibrator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_says);
        /*
        sound - implement when speakers are avail
        yellowMediaPlayer = MediaPlayer.create(this, R.raw.asharp);
        blueMediaPlayer = MediaPlayer.create(this, R.raw.dsharp);
        redMediaPlayer = MediaPlayer.create(this, R.raw.csharp);
        greenMediaPlayer = MediaPlayer.create(this, R.raw.fsharp);
        win = MediaPlayer.create(this, R.raw.applause);
        loose = MediaPlayer.create(this, R.raw.aww);

        //Vibrate
        yellowVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        redVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        blueVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        greenVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        */

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                //UI
                mTextView = (TextView) stub.findViewById(R.id.text);
                onClickGreen();
                onClickRed();
                onClickBlue();
                onClickYellow();

                //GameLoop
                //gameFlow();
            }
        });
    }

    protected void onResume(){
        super.onResume();
        new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                gameLoop();
            }
        }.start();
    };

    protected void onClickGreen(){

        final ImageButton greenButton3 = (ImageButton)findViewById(R.id.imageButton);

        greenButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //vibrate 1 sec
               //greenVibrator.vibrate(10000);
                //sound - implement when speakers are avail
               // greenMediaPlayer.start();


            }
        });
    }

    protected void onClickRed(){

        final ImageButton redButton = (ImageButton)findViewById(R.id.imageButton3);

        //Proceed to Publishing the Event to Calendar and Event DB
        redButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //vibrate 1.5 sec
               //redVibrator.vibrate(1500);

                //sound - implement when speakers are avail
              // redMediaPlayer.start();
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


                //vibrate 2 sec
                // yellowVibrator.vibrate(2000);
                //sound - implement when speakers are avail
                // yellowMediaPlayer.start();

            }
        });
    }
    public void onClickBlue(){
        final ImageButton blueButton4 = (ImageButton)findViewById(R.id.imageButton4);

        //Proceed to Publishing the Event to Calendar and Event DB
        blueButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //vibrate 2.5 sec
                //blueVibrator.vibrate(2500);

                //sound - implement when speakers are avail
                //blueMediaPlayer.start();
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

    public void gameLoop() {
        //int tileArray[] = {1, 2, 3, 4};
        final int gameArray[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        final int min = 1;
        final int max = 4;
        final int length = gameArray.length;
        int count = 0;

                for (int loop = 0; loop < length; loop++) {
                    final int delayTimer = 5000;
                    final int finalCount = count;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Random r = new Random();
                            int i1 = r.nextInt(max - min + 1) + min;
                            gameArray[finalCount] = i1;
                            switch (i1) {
                                case 1:
                                    final ImageButton greenButton = (ImageButton) findViewById(R.id.imageButton);
                                    greenButton.setImageResource(R.drawable.ssgreendown);
                                    handler.postDelayed(r1, delayTimer);
                                    //greenButton.setImageResource(R.drawable.ssgreenup);
                                    break;
                                case 2:
                                    final ImageButton yellowButton = (ImageButton) findViewById(R.id.imageButton2);
                                    yellowButton.setImageResource(R.drawable.ssyellowdown);
                                    handler.postDelayed(r2, delayTimer);
                                    //yellowButton.setImageResource(R.drawable.ssyellowup);
                                    break;
                                case 3:
                                    final ImageButton redButton = (ImageButton) findViewById(R.id.imageButton3);
                                    redButton.setImageResource(R.drawable.ssreddown);
                                    handler.postDelayed(r3, delayTimer);
                                    //redButton.setImageResource(R.drawable.ssredup);
                                    break;
                                case 4:
                                    final ImageButton blueButton = (ImageButton) findViewById(R.id.imageButton4);
                                    blueButton.setImageResource(R.drawable.ssbluedown);
                                    handler.postDelayed(r4, delayTimer);
                                    /*handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            blueButton.setImageResource(R.drawable.ssblueup);
                                        }
                                    }, 5000 * (count + 1));*/
                                    //blueButton.setImageResource(R.drawable.ssblueup);
                                    break;
                            }
                        }
                    },5000 * (count + 1));
                    count++;
                }
            }

    final Runnable r1 = new Runnable() {
        @Override
        public void run() {
            final ImageButton greenButton = (ImageButton) findViewById(R.id.imageButton);
            greenButton.setImageResource(R.drawable.ssgreenup);
            //handler.postDelayed(this,4000);
        }};

        final Runnable r2 = new Runnable() {
            @Override
            public void run() {
                final ImageButton yellowButton = (ImageButton) findViewById(R.id.imageButton2);
                yellowButton.setImageResource(R.drawable.ssyellowup);
                //handler.postDelayed(this,4000);
            }};

            final Runnable r3 = new Runnable() {
                @Override
                public void run() {
                    final ImageButton redButton = (ImageButton) findViewById(R.id.imageButton3);
                    redButton.setImageResource(R.drawable.ssredup);
                    //handler.postDelayed(this,4000);
                }};

                final Runnable r4 = new Runnable() {
                    @Override
                    public void run() {
                        final ImageButton blueButton = (ImageButton) findViewById(R.id.imageButton4);
                        blueButton.setImageResource(R.drawable.ssblueup);
                        //handler.postDelayed(this,4000);
                    }};

    Handler handler = new Handler();
}


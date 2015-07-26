package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//import java.util.logging.Handler;


public class simonSaysActivity extends Activity {

    private TextView mTextView;
    Handler handler = new Handler();
    final ArrayList<Integer> testArray = new ArrayList<>(10);
    final ArrayList<Integer> playerArray = new ArrayList<>(10);
    public int zSpotInArray = 0, zLevelCount = 0;

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
                onClickBlue(0);
                onClickGreen(0);
                onClickRed(0);
                onClickYellow(0);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(getBaseContext(), "Repeat After Simon", Toast.LENGTH_SHORT).show();
                SimonAI(zLevelCount);
            }
        }.start();
    }

    protected void onClickGreen(int zSpotInArray) {

        final ImageButton greenButton3 = (ImageButton) findViewById(R.id.imageButton);
        final int zFinSpotInArray = zSpotInArray;
        greenButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playerArray.add(zFinSpotInArray, 1);
                playerLogic();
                onUserInteraction();{
                    if (playerArray.size() == testArray.size()){
                        if(playerArray.containsAll(testArray)){
                            zLevelCount++;
                            Toast.makeText(getBaseContext(), "Nice Job!", Toast.LENGTH_SHORT).show();
                            onResume();}
                }
                };
                //vibrate 1 sec
                //greenVibrator.vibrate(10000);
                //sound - implement when speakers are avail
                //greenMediaPlayer.start();
            }
        });
    }

    protected void onClickRed(int zSpotInArray) {

        final ImageButton redButton = (ImageButton) findViewById(R.id.imageButton3);
        final int zFinSpotInArray = zSpotInArray;
        //Proceed to Publishing the Event to Calendar and Event DB
        redButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playerArray.add(zFinSpotInArray, 3);
                playerLogic();
                onUserInteraction();{
                    if (playerArray.size() == testArray.size()){
                        if(playerArray.containsAll(testArray)){
                            zLevelCount++;
                            Toast.makeText(getBaseContext(), "Nice Job!", Toast.LENGTH_SHORT).show();
                            onResume();}
                    }
                };
                //vibrate 1.5 sec
                //redVibrator.vibrate(1500);
                // sound - implement when speakers are avail
                // redMediaPlayer.start();
            }
        });
    }

    protected void onClickYellow(int zSpotInArray) {

        final ImageButton yellowButton2 = (ImageButton) findViewById(R.id.imageButton2);
        final int zFinSpotInArray = zSpotInArray;

        //Proceed to Publishing the Event to Calendar and Event DB
        yellowButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playerArray.add(zFinSpotInArray, 2);
                playerLogic();
                onUserInteraction();{
                    if (playerArray.size() == testArray.size()){
                        if(playerArray.containsAll(testArray)){
                            zLevelCount++;
                            Toast.makeText(getBaseContext(), "Nice Job!", Toast.LENGTH_SHORT).show();
                            onResume();}
                    }
                };
                //vibrate 2 sec
                // yellowVibrator.vibrate(2000);
                //sound - implement when speakers are avail
                // yellowMediaPlayer.start();

            }
        });
    }

    public void onClickBlue(int zSpotInArray) {
        final ImageButton blueButton4 = (ImageButton) findViewById(R.id.imageButton4);
        final int zFinSpotInArray = zSpotInArray;

        //Proceed to Publishing the Event to Calendar and Event DB
        blueButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playerArray.add(zFinSpotInArray, 4);
                playerLogic();
                onUserInteraction();{
                    if (playerArray.size() == testArray.size()){
                        if(playerArray.containsAll(testArray)){
                            zLevelCount++;
                            Toast.makeText(getBaseContext(), "Nice Job!", Toast.LENGTH_SHORT).show();
                            onResume();}
                    }
                };
                //vibrate 2.5 sec
                //blueVibrator.vibrate(2500);

                //sound - implement when speakers are avail
                //blueMediaPlayer.start();
            }
        });
    }

    public void playerLogic() {
    zSpotInArray++;
    }

    public void SimonAI(int LevelCount){

        final int min = 1;
        final int max = 4;
        int length = testArray.size();
        int count = 0;

        for (int loop = 0; loop <= length; loop++) {
            final int delayTimer = 5000;
            final int zfinalCount = loop;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Random r = new Random();
                    int i1 = r.nextInt(max - min + 1) + min;
                    testArray.add(zfinalCount, i1);
                    switch (i1) {
                        case 1:
                            final ImageButton greenButton = (ImageButton) findViewById(R.id.imageButton);
                            greenButton.setImageState(new int[]{android.R.attr.state_pressed}, true);
                            handler.postDelayed(r1, delayTimer);
                            //greenButton.setImageResource(R.drawable.ssgreenup);
                            break;
                        case 2:
                            final ImageButton yellowButton = (ImageButton) findViewById(R.id.imageButton2);
                            yellowButton.setImageState(new int[]{android.R.attr.state_pressed}, true);
                            handler.postDelayed(r2, delayTimer);
                            //yellowButton.setImageResource(R.drawable.ssyellowup);
                            break;
                        case 3:
                            final ImageButton redButton = (ImageButton) findViewById(R.id.imageButton3);
                            redButton.setImageState(new int[]{android.R.attr.state_pressed}, true);
                            handler.postDelayed(r3, delayTimer);
                            //redButton.setImageResource(R.drawable.ssredup);
                            break;
                        case 4:
                            final ImageButton blueButton = (ImageButton) findViewById(R.id.imageButton4);
                            blueButton.setImageState(new int[]{android.R.attr.state_pressed}, true);
                            handler.postDelayed(r4, delayTimer);
                            break;
                    }
                }
            }, 5000 * (count + 1));
            count++;
            zSpotInArray = 0;
        }
    }

    final Runnable r1 = new Runnable() {
        @Override
        public void run() {
            final ImageButton greenButton = (ImageButton) findViewById(R.id.imageButton);
            greenButton.setImageState(new int[]{android.R.attr.drawable}, true);
            //handler.postDelayed(this,4000);
        }
    };

    final Runnable r2 = new Runnable() {
        @Override
        public void run() {
            final ImageButton yellowButton = (ImageButton) findViewById(R.id.imageButton2);
            yellowButton.setImageState(new int[]{android.R.attr.drawable}, true);
            //handler.postDelayed(this,4000);
        }
    };

    final Runnable r3 = new Runnable() {
        @Override
        public void run() {
            final ImageButton redButton = (ImageButton) findViewById(R.id.imageButton3);
            redButton.setImageState(new int[]{android.R.attr.drawable}, true);
            //handler.postDelayed(this,4000);
        }
    };

    final Runnable r4 = new Runnable() {
        @Override
        public void run() {
            final ImageButton blueButton = (ImageButton) findViewById(R.id.imageButton4);
            blueButton.setImageState(new int[]{android.R.attr.drawable}, true);
            //handler.postDelayed(this,4000);
        }
    };
}


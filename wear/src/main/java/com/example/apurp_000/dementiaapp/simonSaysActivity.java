package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//import java.util.logging.Handler;


public class simonSaysActivity extends Activity {

    private TextView mTextView;
    Handler zHandler = new Handler();
    final ArrayList<Integer> zAIArray = new ArrayList<>(10);
    final ArrayList<Integer> zPlayerArray = new ArrayList<>(10);
    List<Integer> zComparingList = new ArrayList<Integer>();
    List<Integer> zTrueList = new ArrayList<Integer>();
    public int zSpotInArray = 0, zLevelCount = 0;
    public String zTResults;

    public String zStartTime;
    public String zEndTime;
    public String zCancelTime = "n/a";
    boolean notFinished = true;
    GenerateTime zGetTimes = new GenerateTime();
    private String id = "";

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
        zStartTime = zGetTimes.generateTimes();
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
                onClickBlue();
                onClickGreen();
                onClickRed();
                onClickYellow();
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

    protected void onResume() {
        super.onResume();
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(getBaseContext(), "Repeat After Simon", Toast.LENGTH_SHORT).show();
                SimonAI();
            }
        }.start();
    }

    //Should the patient just cancel the activity
    protected void onDestroy(){
        //get level for score to pass to win page
        String endPageLevelCount = String.valueOf(zLevelCount);

        // start end page and pass score
        Intent simonGameIntent = new Intent(getApplicationContext(), SimonSaysWinPage.class);
        simonGameIntent.putExtra("text", endPageLevelCount);
        //launch simon end page
        startActivity(simonGameIntent);

        if(notFinished){
            zCancelTime = zGetTimes.generateTimes();
            zEndTime = "n/a";
            zTResults = "";
            generateAnalytics();
        }
        super.onDestroy();
    }

    protected void onClickGreen() {

        final ImageButton greenButton3 = (ImageButton) findViewById(R.id.imageButton);
        greenButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final int zFinSpotInArray = zSpotInArray;
                zPlayerArray.add(zFinSpotInArray, 1);
                zPlayerLogic();
                onUserInteraction();{
                    if (zPlayerArray.size() == zAIArray.size()){
                        for (int zLoop = 0; zLoop < zAIArray.size(); zLoop++) {
                            zComparingList.add(0);
                            zTrueList.add(1);
                        }
                        for(int zLoop = 0; zLoop < zAIArray.size(); zLoop++) {
                            if(zPlayerArray.get(zLoop).equals(zAIArray.get(zLoop))) {
                                zComparingList.set(zLoop, 1);
                            }
                        }
                        if(zComparingList.equals(zTrueList) && zLevelCount != 10){
                            zLevelCount++;
                            Toast.makeText(getBaseContext(), "Nice Job!", Toast.LENGTH_SHORT).show();
                            zClearArrays();
                            onResume();}else{
                                zEndTime = zGetTimes.generateTimes();
                                if (zLevelCount >= 6) {
                                    zTResults = "1";
                                }else if(zLevelCount >= 4){
                                    zTResults = "2";
                                }else if(zLevelCount >= 2){
                                    zTResults = "3";
                                }
                                generateAnalytics();
                                notFinished = false;
                                finish(); }
                    }
                };
                //vibrate 1 sec
                //greenVibrator.vibrate(10000);
                //sound - implement when speakers are avail
                //greenMediaPlayer.start();
            }
        });
    }

    protected void onClickRed() {

        final ImageButton redButton = (ImageButton) findViewById(R.id.imageButton3);

        //Proceed to Publishing the Event to Calendar and Event DB
        redButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final int zFinSpotInArray = zSpotInArray;
                zPlayerArray.add(zFinSpotInArray, 3);
                zPlayerLogic();
                onUserInteraction();{
                    if (zPlayerArray.size() == zAIArray.size()){
                        for (int zLoop = 0; zLoop < zAIArray.size(); zLoop++) {
                            zComparingList.add(0);
                            zTrueList.add(1);
                        }
                        for(int zLoop = 0; zLoop < zAIArray.size(); zLoop++) {
                            if(zPlayerArray.get(zLoop).equals(zAIArray.get(zLoop))) {
                                zComparingList.set(zLoop, 1);
                            }
                        }
                        if(zComparingList.equals(zTrueList) && zLevelCount != 10){
                            zLevelCount++;
                            Toast.makeText(getBaseContext(), "Nice Job!", Toast.LENGTH_SHORT).show();
                            zClearArrays();
                            onResume();}else{
                            zEndTime = zGetTimes.generateTimes();
                            if (zLevelCount >= 6) {
                                zTResults = "1";
                            }else if(zLevelCount >= 4){
                                zTResults = "2";
                            }else if(zLevelCount >= 2){
                                zTResults = "3";
                            }
                            generateAnalytics();
                            notFinished = false;
                            finish(); }
                    }
                };
                //vibrate 1.5 sec
                //redVibrator.vibrate(1500);
                // sound - implement when speakers are avail
                // redMediaPlayer.start();
            }
        });
    }

    protected void onClickYellow() {

        final ImageButton yellowButton2 = (ImageButton) findViewById(R.id.imageButton2);

        //Proceed to Publishing the Event to Calendar and Event DB
        yellowButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final int zFinSpotInArray = zSpotInArray;
                zPlayerArray.add(zFinSpotInArray, 2);
                zPlayerLogic();
                onUserInteraction();{
                    if (zPlayerArray.size() == zAIArray.size()){
                        for (int zLoop = 0; zLoop < zAIArray.size(); zLoop++) {
                            zComparingList.add(0);
                            zTrueList.add(1);
                        }
                        for(int zLoop = 0; zLoop < zAIArray.size(); zLoop++) {
                            if(zPlayerArray.get(zLoop).equals(zAIArray.get(zLoop))) {
                                zComparingList.set(zLoop, 1);
                            }
                        }
                        if(zComparingList.equals(zTrueList) && zLevelCount != 10){
                            zLevelCount++;
                            Toast.makeText(getBaseContext(), "Nice Job!", Toast.LENGTH_SHORT).show();
                            zClearArrays();
                            onResume();}else{
                            zEndTime = zGetTimes.generateTimes();
                            if (zLevelCount >= 6) {
                                zTResults = "1";
                            }else if(zLevelCount >= 4){
                                zTResults = "2";
                            }else if(zLevelCount >= 2){
                                zTResults = "3";
                            }
                            generateAnalytics();
                            notFinished = false;
                            finish(); }
                    }
                };
                //vibrate 2 sec
                // yellowVibrator.vibrate(2000);
                //sound - implement when speakers are avail
                // yellowMediaPlayer.start();

            }
        });
    }

    public void onClickBlue() {
        final ImageButton blueButton4 = (ImageButton) findViewById(R.id.imageButton4);

        //Proceed to Publishing the Event to Calendar and Event DB
        blueButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final int zFinSpotInArray = zSpotInArray;
                zPlayerArray.add(zFinSpotInArray, 4);
                zPlayerLogic();
                onUserInteraction();{
                    if (zPlayerArray.size() == zAIArray.size()){
                        for (int zLoop = 0; zLoop < zAIArray.size(); zLoop++) {
                            zComparingList.add(0);
                            zTrueList.add(1);
                        }
                        for(int zLoop = 0; zLoop < zAIArray.size(); zLoop++) {
                            if(zPlayerArray.get(zLoop).equals(zAIArray.get(zLoop))) {
                                zComparingList.set(zLoop, 1);
                            }
                        }
                        if(zComparingList.equals(zTrueList) && zLevelCount != 10){
                            zLevelCount++;
                            Toast.makeText(getBaseContext(), "Nice Job!", Toast.LENGTH_SHORT).show();
                            zClearArrays();
                            onResume();}else{
                            zEndTime = zGetTimes.generateTimes();
                            if (zLevelCount >= 6) {
                                zTResults = "1";
                            }else if(zLevelCount >= 4){
                                zTResults = "2";
                            }else if(zLevelCount >= 2){
                                zTResults = "3";
                            }
                            generateAnalytics();
                            notFinished = false;
                            finish(); }
                    }
                };
                //vibrate 2.5 sec
                //blueVibrator.vibrate(2500);

                //sound - implement when speakers are avail
                //blueMediaPlayer.start();
            }
        });
    }

    public void zPlayerLogic() {
    zSpotInArray++;
    }

    public void SimonAI(){

        final int min = 1;
        final int max = 4;
        int length = zAIArray.size() + zLevelCount;
        int count = 0;

        for (int loop = 0; loop <= length; loop++) {
            final int zDelayTimer = 4000;
            final int zFinalCount = count;
            zHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Random zRandom = new Random();
                    int zButtonSelect = zRandom.nextInt(max - min + 1) + min;
                    zAIArray.add(zFinalCount, zButtonSelect);
                    switch (zButtonSelect) {
                        case 1:
                            final ImageButton greenButton = (ImageButton) findViewById(R.id.imageButton);
                            greenButton.setImageState(new int[]{android.R.attr.state_pressed}, true);
                            zHandler.postDelayed(r1, zDelayTimer);
                            break;
                        case 2:
                            final ImageButton yellowButton = (ImageButton) findViewById(R.id.imageButton2);
                            yellowButton.setImageState(new int[]{android.R.attr.state_pressed}, true);
                            zHandler.postDelayed(r2, zDelayTimer);
                            break;
                        case 3:
                            final ImageButton redButton = (ImageButton) findViewById(R.id.imageButton3);
                            redButton.setImageState(new int[]{android.R.attr.state_pressed}, true);
                            zHandler.postDelayed(r3, zDelayTimer);
                            break;
                        case 4:
                            final ImageButton blueButton = (ImageButton) findViewById(R.id.imageButton4);
                            blueButton.setImageState(new int[]{android.R.attr.state_pressed}, true);
                            zHandler.postDelayed(r4, zDelayTimer);
                            break;
                    }
                }
            }, 5000 * (count + 1));
            count++;
        };
    }

    final Runnable r1 = new Runnable() {
        @Override
        public void run() {
            final ImageButton greenButton = (ImageButton) findViewById(R.id.imageButton);
            greenButton.setImageState(new int[]{android.R.attr.drawable}, true);
        }
    };

    final Runnable r2 = new Runnable() {
        @Override
        public void run() {
            final ImageButton yellowButton = (ImageButton) findViewById(R.id.imageButton2);
            yellowButton.setImageState(new int[]{android.R.attr.drawable}, true);
        }
    };

    final Runnable r3 = new Runnable() {
        @Override
        public void run() {
            final ImageButton redButton = (ImageButton) findViewById(R.id.imageButton3);
            redButton.setImageState(new int[]{android.R.attr.drawable}, true);
        }
    };

    final Runnable r4 = new Runnable() {
        @Override
        public void run() {
            final ImageButton blueButton = (ImageButton) findViewById(R.id.imageButton4);
            blueButton.setImageState(new int[]{android.R.attr.drawable}, true);
        }
    };

    public void zClearArrays (){
        zComparingList.clear();
        zTrueList.clear();
        zPlayerArray.clear();
        zAIArray.clear();
        zSpotInArray = 0;
    }

    public void generateAnalytics() {

        String StartTime = zStartTime ;
        String EndTime = zEndTime;
        String CancelTime = zCancelTime;
        String Level = Integer.toString(zLevelCount);
        String Score = "n/a";
        String Action = "Simon Says";
        String EventId = id;
        String Trophy = zTResults;

        ActivityResult zResults = new ActivityResult(StartTime, EndTime, CancelTime, Level, Score, Action, EventId, Trophy);
        new SendResultToMobile(zResults,this).start();
    }

}


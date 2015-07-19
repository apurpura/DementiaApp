package com.example.apurp_000.dementiaapp;

        import android.app.Activity;
        import android.os.Bundle;
        import android.os.CountDownTimer;
        import android.os.Handler;
        import android.widget.ImageSwitcher;
        import android.widget.ImageView;

/**
 * Created by Ryan on 6/24/2015.
 */

public class ImageCarousel extends Activity {
    //Configure Array to Store Pictures.
    Integer[] zImageIDs = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4
    };

    int zImageCounter = 0;
    public String zStartTime;
    public String zEndTime;
    public String zCancelTime = "n/a";
    GenerateTime zGetTimes = new GenerateTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_carousel);
        zStartTime = zGetTimes.generateTimes();
        //Pull the Image View variable from the XML Layout
        final ImageView zImageView = (ImageView) findViewById(R.id.image1);

        //Create a Countdown at the start for 5 minutes
        //End with sending Analytics and Closing Activity
        new CountDownTimer(300000,1000){
            public void onTick(long millisUntilFinished){}
            public void onFinish(){
                zEndTime = zGetTimes.generateTimes();
                generateAnalytics();
                finish();
            }
        }.start();

        //Create a thread that scrolls through the imageID array with a set delay
        //before moving onto the next image.
        Runnable r = new Runnable() {
            public void run() {
                zImageView.setImageResource(zImageIDs[zImageCounter]);
                zImageCounter++;
                if (zImageCounter >= zImageIDs.length) {
                    zImageCounter = 0;
                }
                zImageView.postDelayed(this, 5000); //set to go off again in 5 seconds.
            }
        };
        zImageView.postDelayed(r, 3000);//set inital delay of 3 seconds before starting
    }

    protected void onDestroy(){
        super.onDestroy();
        zCancelTime = zGetTimes.generateTimes();
    }
    
    //Export the Analytics to the ActivityResults to be sent
    public void generateAnalytics() {

        String StartTime = zStartTime ;
        String EndTime = zEndTime;
        String CancelTime = zCancelTime;
        String Level = "n/a";
        String Score = "n/a";
        String Action = "Image Carousel";
        String EventId = "FigureOutEvenID03";

        ActivityResult zResults = new ActivityResult(StartTime, EndTime, CancelTime, Level, Score, Action, EventId);
        new SendResultToMobile(zResults,this).start();
    }

    //Insert Code Here for Basic Meta Data for Quiz to Use.
}



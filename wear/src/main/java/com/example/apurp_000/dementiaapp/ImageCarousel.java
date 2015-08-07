package com.example.apurp_000.dementiaapp;

        import android.app.Activity;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.os.CountDownTimer;
        import android.os.Handler;
        import android.util.Log;
        import android.widget.ImageSwitcher;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.wearable.Asset;
        import com.google.android.gms.wearable.DataEvent;
        import com.google.android.gms.wearable.DataEventBuffer;
        import com.google.android.gms.wearable.DataMapItem;
        import com.google.android.gms.wearable.Wearable;
        import com.google.android.gms.wearable.WearableListenerService;

        import java.io.InputStream;
        import java.util.concurrent.TimeUnit;

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
    String[] zTextIDs ={
      "Abstract Art",
      "Shark",
      "Wolf",
      "Parrot"
    };

    int zImageCounter = 0;
    public String zStartTime;
    public String zEndTime;
    public String zCancelTime = "n/a";
    boolean notFinished = true;
    GenerateTime zGetTimes = new GenerateTime();
    TextMessageActivity zTMA = new TextMessageActivity();

    int TIMEOUT_MS = 10000;
    public  Bitmap zTestBimp;
    private  GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_carousel);
        setupGoogleApiClient();
        zStartTime = zGetTimes.generateTimes();

        //Pull the Image View and Text View variable from the XML Layout
        final ImageView zImageView = (ImageView) findViewById(R.id.image1);
        final TextView zTextView = (TextView) findViewById(R.id.textView2);

        //Create a Countdown at the start for 5 minutes
        //End with sending Analytics and Closing Activity
        new CountDownTimer(300000,1000){
            public void onTick(long millisUntilFinished){}
            public void onFinish(){
                zEndTime = zGetTimes.generateTimes();
                notFinished = false;
                generateAnalytics();
                finish();
            }
        }.start();

        //Create a thread that scrolls through the imageID array with a set delay
        //before moving onto the next image.
        Runnable r = new Runnable() {
            public void run() {
                //zImageView.setImageResource(zImageIDs[zImageCounter]);
                zImageView.setImageBitmap(zTestBimp);
                zTextView.setText(zTextIDs[zImageCounter]);
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
        if(notFinished){
            zCancelTime = zGetTimes.generateTimes();
            zEndTime = "n/a";
            generateAnalytics();
        }
        super.onDestroy();
    }
    
    //Export the Analytics to the ActivityResults to be sent
    public void generateAnalytics() {

        String StartTime = zStartTime ;
        String EndTime = zEndTime;
        String CancelTime = zCancelTime;
        String Level = "n/a";
        String Score = "n/a";
        String Action = "Image Carousel";
        String EventId = zTMA.id;

        ActivityResult zResults = new ActivityResult(StartTime, EndTime, CancelTime, Level, Score, Action, EventId);
        new SendResultToMobile(zResults,this).start();
    }

    //Insert Code Here for Basic Meta Data for Quiz to Use.

    public void setupGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();

    }

    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED &&
                    event.getDataItem().getUri().getPath().equals("/image")) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                Asset profileAsset = dataMapItem.getDataMap().getAsset("profileImage");
                Bitmap bitmap = loadBitmapFromAsset(profileAsset);
                // Do something with the bitmap
                zTestBimp = bitmap;
            }
        }
    }

    public Bitmap loadBitmapFromAsset(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset must be non-null");
        }
        ConnectionResult result =
                mGoogleApiClient.blockingConnect(TIMEOUT_MS, TimeUnit.MILLISECONDS);
        if (!result.isSuccess()) {
            return null;
        }
        // convert asset into a file descriptor and block until it's ready
        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                mGoogleApiClient, asset).await().getInputStream();
        mGoogleApiClient.disconnect();

        if (assetInputStream == null) {
            //Log.w(TAG, "Requested an unknown Asset.");
            return null;
        }
        // decode the stream into a bitmap
        return BitmapFactory.decodeStream(assetInputStream);
    }
}



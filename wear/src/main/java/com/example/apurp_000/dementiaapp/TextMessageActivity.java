package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

public class TextMessageActivity extends Activity  {

    private TextView mTextViewSummary;
    private TextView mTextViewDescription;
    private GoogleApiClient mApiClient;
    private static  String WEAR_MESSAGE_PATH = "//message";
    private SimpleCursorAdapter mAdapter;;
    private JSONObject json;
    private String summary;
    private String description;
    protected String id;
    private String action;
    private String location;
    private String closeTime;
    private String startTime;
    private String cancelTime;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startTime = DateFormat.getDateTimeInstance().format(new Date());
        Intent intent = getIntent();
        summary = "";
        description = "";
        id = "";
        action = "";
        location = "";
        cancelTime = "";
        if(intent != null) {
            String m = intent.getStringExtra("text");
            if(m != null) {
                try {
                    json = new JSONObject(m);
                    id = json.get("Id").toString() + "\n";
                    action = json.get("Action").toString() + "\n";
                    summary =  json.get("Summary").toString() + "\n";
                    description =json.get("Description").toString() + "\n";
                    //text = text + "EndTime: " + json.get("EndTime").toString() + "\n";
                    location = json.get("Location").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextViewSummary = (TextView) stub.findViewById(R.id.summary);
                mTextViewDescription = (TextView) stub.findViewById(R.id.description);
                if (summary != "") {
                    mTextViewSummary.setText(summary);
                    mTextViewDescription.setText(description);
                } else {
                    mTextViewSummary.setText("Summary Test");
                    mTextViewDescription.setText("Description test");
                }

                //Proceed to Publishing the Event to Calendar and Event DB
                Button publishEvent = (Button) stub.findViewById(R.id.Close);
                publishEvent.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        closeTime = DateFormat.getDateTimeInstance().format(new Date());
                        ActivityResult result = new ActivityResult(startTime, closeTime, "", "", "", action, id);
                        new SendResultToMobile(result, TextMessageActivity.this).start();
                        finish();

                    }
                });
            }
        });

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            final int SWIPE_THRESHOLD = 100;
            final int SWIPE_VELOCITY_THRESHOLD = 100;
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        closeTime = DateFormat.getDateTimeInstance().format(new Date());
                        ActivityResult result = new ActivityResult(startTime,closeTime, "","","",action,id);
                        new SendResultToMobile(result, TextMessageActivity.this).start();

                    }else{
                        //right swipe
                    }
                }
            }
            return true;
        }
    }

}

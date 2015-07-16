package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class startPage extends Activity {

    private TextView mTextView;
    //Code for when wearable has speakers
   // MediaPlayer goodJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        //Code for when wearable has speakers
        // goodJob = MediaPlayer.create(this, R.raw.applause);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                //UI
                onClickStart();

            }
        });


    }
    public void onClickStart(){
        final ImageButton startGame = (ImageButton)findViewById(R.id.imageButton5);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Code for when wearable has speakers
                //goodJob.start();

                //finish();
                Intent intent = new Intent(startPage.this,simonSaysActivity.class);
                startActivity(intent);

            }
        });
    }
}

package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ActSequence extends Activity {

    private TextView mTextView;
    int currentPic = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sequence);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                //Button to transvers list
                tapToConitinue();
            }
        });
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
                           continueButton.setImageResource(R.drawable.pants);
                           break;
                       case 3:
                           currentPic++;
                           continueButton = (ImageButton)findViewById(R.id.imageButton6);
                           continueButton.setImageResource(R.drawable.sweater);
                           break;
                       case 4:
                           currentPic++;
                           continueButton = (ImageButton)findViewById(R.id.imageButton6);
                           continueButton.setImageResource(R.drawable.slippers);
                           break;
                       case 5:
                           currentPic++;
                           continueButton = (ImageButton)findViewById(R.id.imageButton6);
                           continueButton.setImageResource(R.drawable.finishedsequence);
                           break;
                       case 6:
                           finish();
                           break;

                   }



            }
        });
    }
}

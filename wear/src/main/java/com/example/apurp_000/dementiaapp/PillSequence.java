package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class PillSequence extends Activity {

    private TextView mTextView;
    int currentPill = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_sequence);
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
                        finish();
                        break;

                }



            }
        });
    }


}

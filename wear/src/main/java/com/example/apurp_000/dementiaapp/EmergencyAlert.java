package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class EmergencyAlert extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_alert);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                tapToConitinue();
            }
        });
    }

    public void tapToConitinue(){
        ImageButton continueButton = (ImageButton)findViewById(R.id.imageButton8);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();

            }
        });
    }
}

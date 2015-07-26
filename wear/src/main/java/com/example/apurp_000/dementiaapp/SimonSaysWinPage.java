package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SimonSaysWinPage extends Activity {

    private TextView mTextView;
    String simonScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //get passed intent - name of activity
        Intent intent = getIntent();
        simonScore = intent.getStringExtra("text");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_says_win_page);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                //set text
                changeText();
                //UI
                onClickClose();
            }
        });
    }

    public void changeText(){
        if(simonScore != null) {
            final TextView mTextView = (TextView) findViewById(R.id.simonWinText);
            mTextView.setText(simonScore);
        }else
        {
            final TextView mTextView = (TextView) findViewById(R.id.simonWinText);
            mTextView.setText("Debug");
        }
    }

    public void onClickClose(){
        final ImageButton startGame = (ImageButton)findViewById(R.id.simonWinButton);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Close Win Page
                finish();

            }
        });
    }

}

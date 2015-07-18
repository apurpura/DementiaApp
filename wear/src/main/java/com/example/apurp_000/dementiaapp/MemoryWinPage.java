package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MemoryWinPage extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_win_page);
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
        //change missed text hear when variable made
    }
    public void onClickClose(){
        final ImageButton startGame = (ImageButton)findViewById(R.id.imageButtonMemoryWinPage);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                //Close Win Page
                finish();

            }
        });
    }
}

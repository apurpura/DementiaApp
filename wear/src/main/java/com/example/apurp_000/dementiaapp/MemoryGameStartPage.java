package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MemoryGameStartPage extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game_start_page);
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
        final ImageButton startGame = (ImageButton)findViewById(R.id.imageButtonStartMemory);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //start Memory
                startActivity(new Intent(getApplicationContext(),MemoryGameActivity.class));
                //Close Start Page
                finish();

            }
        });
    }
}

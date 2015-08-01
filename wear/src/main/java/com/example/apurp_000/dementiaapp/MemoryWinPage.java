package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.NumberFormat;

public class MemoryWinPage extends Activity {

    private TextView mTextView;
    String memoryScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get passed intent - name of activity
        Intent intent = getIntent();
        memoryScore = intent.getStringExtra("text");

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
        if(memoryScore != null) {
            final TextView mTextView = (TextView) findViewById(R.id.textView3);
            mTextView.setText(memoryScore);
        }else
        {
            final TextView mTextView = (TextView) findViewById(R.id.textView3);
            mTextView.setText("Debug");
        }
    }
    public void onClickClose(){
        final ImageButton startGame = (ImageButton)findViewById(R.id.imageButtonMemoryWinPage);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //debug for trophy page
                //memoryScore = "10";

                int numberOfMisses=  Integer.parseInt(memoryScore);

                if (numberOfMisses <= 15) {

                    String goldText = "gold";
                    // start end page
                    Intent trophyIntent = new Intent(getApplicationContext(), TrophyPage.class);
                    trophyIntent.putExtra("text", goldText);
                    //start memory end page
                    startActivity(trophyIntent);

                    finish();

                }else if(numberOfMisses <= 20){
                    String silverText = "silver";
                    // start end page
                    Intent trophyIntent = new Intent(getApplicationContext(),TrophyPage.class);
                    trophyIntent.putExtra("text", silverText);
                    //start memory end page
                    startActivity(trophyIntent);

                    finish();

                }else if(numberOfMisses <= 30){
                    String bronzeText = "bronze";
                    // start end page
                    Intent trophyIntent = new Intent(getApplicationContext(), TrophyPage.class);
                    trophyIntent.putExtra("text", bronzeText);
                    //start memory end page
                    startActivity(trophyIntent);

                    finish();

                }else {

                    //Close Win Page with no trophy
                    finish();
                }

            }
        });
    }
}

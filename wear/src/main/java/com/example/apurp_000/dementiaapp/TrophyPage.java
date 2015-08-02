package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class TrophyPage extends Activity {

    //Set name for
    public String trophyName;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //get passed intent - name of activity
        Intent intent = getIntent();
        trophyName = intent.getStringExtra("text");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy_page);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                if(trophyName != null){

                    //dont do anything cause its populated
                }else{

                    //set debug so no null pointer error
                    trophyName = "debug";


                }


                //change trophy on name
                earnedTrophyChannge();

                //set close by tapping screen
                closeWithScreentap();
            }
        });

    }

    public void closeWithScreentap(){
        final ImageButton startGame = (ImageButton)findViewById(R.id.trophyImageBtn);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //close trophy page
                finish();
            }
        });}

    public void earnedTrophyChannge(){

        if(trophyName.equals("gold")){

            ImageButton trophyBtnGold = (ImageButton)findViewById(R.id.trophyImageBtn);
            trophyBtnGold.setImageResource(R.drawable.goldtrophypage);

        }else if (trophyName.equals("silver")){

            ImageButton trophyBtnSilver = (ImageButton)findViewById(R.id.trophyImageBtn);
            trophyBtnSilver.setImageResource(R.drawable.silvertrophypage);

        }else if (trophyName.equals("bronze") ){

            ImageButton trophyBtnBronze = (ImageButton)findViewById(R.id.trophyImageBtn);
            trophyBtnBronze.setImageResource(R.drawable.bronzetrophypage);

        }else{
            //nothing show default back with no trophy
            //Something wrong if they came to trophy page with no trophy
            //So if default background showingsomething wrong withintent pass


        }
    }
}

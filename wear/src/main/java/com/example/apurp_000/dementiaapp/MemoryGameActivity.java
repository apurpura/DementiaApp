package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Random;

public class MemoryGameActivity extends Activity {

    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                //set up card listeners by position
                setUpCardListeners();


            }
        });


    }

    public void setUpCardListeners(){
    cardListerSetUp1x1();
    cardListerSetUp1x2();
    cardListerSetUp1x3();
    cardListerSetUp1x4();
    cardListerSetUp2x1();
    cardListerSetUp2x2();
    cardListerSetUp2x3();
    cardListerSetUp2x4();
    cardListerSetUp3x1();
    cardListerSetUp3x2();
    cardListerSetUp3x3();
    cardListerSetUp3x4();
    cardListerSetUp4x1();
    cardListerSetUp4x2();
    cardListerSetUp4x3();
    cardListerSetUp4x4();
    }


    public void cardListerSetUp1x1(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton1x1);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                finish();

            }
        });
    }

    public void cardListerSetUp1x2(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton1x2);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();

            }
        });
    }

    public void cardListerSetUp1x3(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton1x3);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Code for when wearable has speakers
                //goodJob.start();

                finish();

            }
        });
    }

    public void cardListerSetUp1x4(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton1x4);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();

            }
        });
    }
    public void cardListerSetUp2x1(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton2x1);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();

            }
        });
    }

    public void cardListerSetUp2x2(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton2x2);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();

            }
        });
    }

    public void cardListerSetUp2x3(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton2x3);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();

            }
        });
    }

    public void cardListerSetUp2x4(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton2x4);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                finish();

            }
        });
    }
    public void cardListerSetUp3x1(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton3x1);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                finish();

            }
        });
    }

    public void cardListerSetUp3x2(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton3x2);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();

            }
        });
    }

    public void cardListerSetUp3x3(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton3x3);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();

            }
        });
    }

    public void cardListerSetUp3x4(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton3x4);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                finish();

            }
        });
    }
    public void cardListerSetUp4x1(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton4x1);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();

            }
        });
    }

    public void cardListerSetUp4x2(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton4x2);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();

            }
        });
    }

    public void cardListerSetUp4x3(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton4x3);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();

            }
        });
    }

    public void cardListerSetUp4x4(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton4x4);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();

            }
        });
    }

}

package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MemoryGameActivity extends Activity {
    Handler handler = new Handler();
    private TextView mTextView;
    Integer[] cardDefaultLayout = {
            R.drawable.memoryredstar,
            R.drawable.memoryredstar,
            R.drawable.memoryyellowstrix,
            R.drawable.memoryyellowstrix,
            R.drawable.memorypurpletriangle,
            R.drawable.memorypurpletriangle,
            R.drawable.memoryorangetriangle,
            R.drawable.memoryorangetriangle,
            R.drawable.memoryblackangle,
            R.drawable.memoryblackangle,
            R.drawable.memorybluecircle,
            R.drawable.memorybluecircle,
            R.drawable.memorypinkheart,
            R.drawable.memorypinkheart,
            R.drawable.memorygreensquare,
            R.drawable.memorygreensquare
    };
    //game layout
    Integer[] cardLayout = {};
    //Array to check matching cards when 2 flipped
    Integer[] checkArray =
            {
                    0,
                    0
            };
    //is one card on board flipped
    boolean isOneCardAlreadyFlipped = false;
    //is each card flipped
    boolean iscard1x1Flipped = false;
    boolean iscard1x2Flipped = false;
    boolean iscard1x3Flipped = false;
    boolean iscard1x4Flipped = false;
    boolean iscard2x1Flipped = false;
    boolean iscard2x2Flipped = false;
    boolean iscard2x3Flipped = false;
    boolean iscard2x4Flipped = false;
    boolean iscard3x1Flipped = false;
    boolean iscard3x2Flipped = false;
    boolean iscard3x3Flipped = false;
    boolean iscard3x4Flipped = false;
    boolean iscard4x1Flipped = false;
    boolean iscard4x2Flipped = false;
    boolean iscard4x3Flipped = false;
    boolean iscard4x4Flipped = false;

    //is card matched
    boolean iscard1x1Matched = false;
    boolean iscard1x2Matched = false;
    boolean iscard1x3Matched = false;
    boolean iscard1x4Matched = false;
    boolean iscard2x1Matched = false;
    boolean iscard2x2Matched = false;
    boolean iscard2x3Matched = false;
    boolean iscard2x4Matched = false;
    boolean iscard3x1Matched = false;
    boolean iscard3x2Matched = false;
    boolean iscard3x3Matched = false;
    boolean iscard3x4Matched = false;
    boolean iscard4x1Matched = false;
    boolean iscard4x2Matched = false;
    boolean iscard4x3Matched = false;
    boolean iscard4x4Matched = false;
    //int for case to know witch cards to lock
    int cardA = 0;
    int cardB = 0;
    //Make a boolean so if in timer it does not double click
    boolean inTimer = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                //shuffle layout
                List<Integer> shuffleList = Arrays.asList(cardDefaultLayout);
                Collections.shuffle(shuffleList);
                Integer[]shuffleLayoutArray = shuffleList.toArray(new Integer[shuffleList.size()]);
                //make default layout shuffled ready for new game
                cardLayout = shuffleLayoutArray;


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

    public void resetIfCardFlippedByButtons(){

         iscard1x1Flipped = false;
         iscard1x2Flipped = false;
         iscard1x3Flipped = false;
         iscard1x4Flipped = false;
         iscard2x1Flipped = false;
         iscard2x2Flipped = false;
         iscard2x3Flipped = false;
         iscard2x4Flipped = false;
         iscard3x1Flipped = false;
         iscard3x2Flipped = false;
         iscard3x3Flipped = false;
         iscard3x4Flipped = false;
         iscard4x1Flipped = false;
         iscard4x2Flipped = false;
         iscard4x3Flipped = false;
         iscard4x4Flipped = false;

    }
    public void reflipCardBacks(){
        switch(cardA){
            case 1:
                if(iscard1x1Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton1x1);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }

                break;
            case 2:
                if(iscard1x2Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton1x2);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 3:
                if(iscard1x3Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton1x3);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 4:
                if(iscard1x4Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton1x4);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 5:
                if(iscard2x1Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton2x1);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 6:
                if(iscard2x2Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton2x2);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 7:
                if(iscard2x3Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton2x3);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 8:
                if(iscard2x4Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton2x4);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 9:
                if(iscard3x1Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton3x1);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 10:
                if(iscard3x2Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton3x2);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 11:
                if(iscard3x3Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton3x3);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 12:
                if(iscard3x4Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton3x4);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 13:
                if(iscard4x1Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton4x1);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 14:
                if(iscard4x2Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton4x2);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 15:
                if(iscard4x3Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton4x3);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 16:
                if(iscard4x4Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton4x4);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
        }

        switch(cardB){
            case 1:
                if(iscard1x1Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton1x1);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }

                break;
            case 2:
                if(iscard1x2Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton1x2);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 3:
                if(iscard1x3Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton1x3);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 4:
                if(iscard1x4Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton1x4);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 5:
                if(iscard1x2Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton2x1);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 6:
                if(iscard2x2Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton2x2);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 7:
                if(iscard2x3Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton2x3);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 8:
                if(iscard2x4Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton2x4);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 9:
                if(iscard3x1Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton3x1);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 10:
                if(iscard3x2Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton3x2);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 11:
                if(iscard3x3Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton3x3);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 12:
                if(iscard3x4Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton3x4);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 13:
                if(iscard4x1Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton4x1);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 14:
                if(iscard4x2Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton4x2);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 15:
                if(iscard4x3Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton4x3);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
            case 16:
                if(iscard4x4Matched) {
                    //nonthing cause matched
                }else{
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton4x4);
                    imageViewMatch1.setImageResource(R.drawable.cardback);
                }
                break;
        }

    }
    public void closeCardsForMatches(){

            switch(cardA){
                case 1:
                    iscard1x1Matched = true;
                    ImageView imageViewMatch1 = (ImageView) findViewById(R.id.imageButton1x1);
                    imageViewMatch1.setImageResource(R.drawable.memorymatch);
                    break;
                case 2:
                    iscard1x2Matched = true;
                    ImageView imageViewMatch2 = (ImageView) findViewById(R.id.imageButton1x2);
                    imageViewMatch2.setImageResource(R.drawable.memorymatch);
                    break;
                case 3:
                    iscard1x3Matched = true;
                    ImageView imageViewMatch3 = (ImageView) findViewById(R.id.imageButton1x3);
                    imageViewMatch3.setImageResource(R.drawable.memorymatch);
                    break;
                case 4:
                    iscard1x4Matched = true;
                    ImageView imageViewMatch4 = (ImageView) findViewById(R.id.imageButton1x4);
                    imageViewMatch4.setImageResource(R.drawable.memorymatch);
                    break;
                case 5:
                    iscard2x1Matched = true;
                    ImageView imageViewMatch5 = (ImageView) findViewById(R.id.imageButton2x1);
                    imageViewMatch5.setImageResource(R.drawable.memorymatch);
                    break;
                case 6:
                    iscard2x2Matched = true;
                    ImageView imageViewMatch6 = (ImageView) findViewById(R.id.imageButton2x2);
                    imageViewMatch6.setImageResource(R.drawable.memorymatch);
                    break;
                case 7:
                    iscard2x3Matched = true;
                    ImageView imageViewMatch7 = (ImageView) findViewById(R.id.imageButton2x3);
                    imageViewMatch7.setImageResource(R.drawable.memorymatch);
                    break;
                case 8:
                    iscard2x4Matched = true;
                    ImageView imageViewMatch8 = (ImageView) findViewById(R.id.imageButton2x4);
                    imageViewMatch8.setImageResource(R.drawable.memorymatch);
                    break;
                case 9:
                    iscard3x1Matched = true;
                    ImageView imageViewMatch9 = (ImageView) findViewById(R.id.imageButton3x1);
                    imageViewMatch9.setImageResource(R.drawable.memorymatch);
                    break;
                case 10:
                    iscard3x2Matched = true;
                    ImageView imageViewMatch10 = (ImageView) findViewById(R.id.imageButton3x2);
                    imageViewMatch10.setImageResource(R.drawable.memorymatch);
                    break;
                case 11:
                    iscard3x3Matched = true;
                    ImageView imageViewMatch11 = (ImageView) findViewById(R.id.imageButton3x3);
                    imageViewMatch11.setImageResource(R.drawable.memorymatch);
                    break;
                case 12:
                    iscard3x4Matched = true;
                    ImageView imageViewMatch12 = (ImageView) findViewById(R.id.imageButton3x4);
                    imageViewMatch12.setImageResource(R.drawable.memorymatch);
                    break;
                case 13:
                    iscard4x1Matched = true;
                    ImageView imageViewMatch13 = (ImageView) findViewById(R.id.imageButton4x1);
                    imageViewMatch13.setImageResource(R.drawable.memorymatch);
                    break;
                case 14:
                    iscard4x2Matched = true;
                    ImageView imageViewMatch14 = (ImageView) findViewById(R.id.imageButton4x2);
                    imageViewMatch14.setImageResource(R.drawable.memorymatch);
                    break;
                case 15:
                    iscard4x3Matched = true;
                    ImageView imageViewMatch15 = (ImageView) findViewById(R.id.imageButton4x3);
                    imageViewMatch15.setImageResource(R.drawable.memorymatch);
                    break;
                case 16:
                    iscard4x4Matched = true;
                    ImageView imageViewMatch16 = (ImageView) findViewById(R.id.imageButton4x4);
                    imageViewMatch16.setImageResource(R.drawable.memorymatch);
                    break;
            }
        switch (cardB){

            case 1:
                iscard1x1Matched = true;
                break;
            case 2:
                iscard1x2Matched = true;
                break;
            case 3:
                iscard1x3Matched = true;
                break;
            case 4:
                iscard1x4Matched = true;
                break;
            case 5:
                iscard2x1Matched = true;
                break;
            case 6:
                iscard2x2Matched = true;
                break;
            case 7:
                iscard2x3Matched = true;
                break;
            case 8:
                iscard2x4Matched = true;
                break;
            case 9:
                iscard3x1Matched = true;
                break;
            case 10:
                iscard3x2Matched = true;
                break;
            case 11:
                iscard3x3Matched = true;
                break;
            case 12:
                iscard3x4Matched = true;
                break;
            case 13:
                iscard4x1Matched = true;
                break;
            case 14:
                iscard4x2Matched = true;
                break;
            case 15:
                iscard4x3Matched = true;
                break;
            case 16:
                iscard4x4Matched = true;
                break;
        }

    }

    public void isBoardCleared(){
        if(iscard1x1Matched&&iscard1x2Matched&&iscard1x3Matched&&iscard1x4Matched&&iscard2x1Matched&&iscard2x2Matched&&iscard2x3Matched&&iscard2x4Matched&&iscard3x1Matched&&iscard3x2Matched&&iscard3x3Matched&&iscard3x4Matched&&iscard4x1Matched&&iscard4x2Matched&&iscard4x3Matched&&iscard4x4Matched){

                finish();
        }else {
            //keep playing
        }
    }

    public void cardListerSetUp1x1(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton1x1);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            //fix to keep cards from being a double click match because code in a timer "frieze stae"
                if(inTimer){
                    //do nothing because code is frozen in a timer and errors occur like same card match
                }
                else if(iscard1x1Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard1x1Matched){
                    //do nothing if already matched

                }else {
                    ImageView imageView = (ImageView) findViewById(R.id.imageButton1x1);
                    imageView.setImageResource(cardLayout[0]);
                    //set intimer so no errors occur while code frozen
                    inTimer = true;

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ImageView imageView = (ImageView) findViewById(R.id.imageButton1x1);
                            //
                            inTimer = false;

                            if (isOneCardAlreadyFlipped) {
                                //reset if one card is flipped
                                isOneCardAlreadyFlipped = false;
                                //set int for first card case
                                cardB = 1;
                                //reset if any others are flipped
                                resetIfCardFlippedByButtons();
                                //store what this button is
                                checkArray[1] = cardLayout[0];
                                if (checkArray[0].equals(checkArray[1])) {

                                    closeCardsForMatches();
                                    imageView.setImageResource(R.drawable.memorymatch);

                                } else {

                                    reflipCardBacks();
                                }

                            } else {

                                //flip card to know to leave up
                                iscard1x1Flipped = true;
                                //Set a card is global flipped
                                isOneCardAlreadyFlipped = true;
                                //set int for first card case
                                cardA = 1;
                                //store to check
                                checkArray[0] = cardLayout[0];

                            }
                        }
                    }, 800);
                }
                    //check for cleared board
                     isBoardCleared();
            }
        });
    }

    public void cardListerSetUp1x2(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton1x2);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(iscard1x2Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard1x2Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton1x2);
                    imageView.setImageResource(cardLayout[1]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 2;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[1];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard1x2Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 2;
                        //store to check
                        checkArray[0]= cardLayout[1];

                    }
                    //check for cleared board
                    isBoardCleared();
                }

            }
        });
    }

    public void cardListerSetUp1x3(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton1x3);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if(iscard1x3Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard1x3Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton1x3);
                    imageView.setImageResource(cardLayout[2]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 3;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[2];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard1x3Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 3;
                        //store to check
                        checkArray[0]= cardLayout[2];

                    }
                    //check for cleared board
                    isBoardCleared();
                }

            }
        });
    }

    public void cardListerSetUp1x4(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton1x4);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(iscard1x4Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard1x4Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton1x4);
                    imageView.setImageResource(cardLayout[3]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 4;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[3];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard1x4Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 4;
                        //store to check
                        checkArray[0]= cardLayout[3];

                    }
                    //check for cleared board
                    isBoardCleared();
                }

            }
        });
    }
    public void cardListerSetUp2x1(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton2x1);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(iscard2x1Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard2x1Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton2x1);
                    imageView.setImageResource(cardLayout[4]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 5;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[4];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard2x1Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 5;
                        //store to check
                        checkArray[0]= cardLayout[4];

                    }
                    //check for cleared board
                    isBoardCleared();
                }

            }
        });
    }

    public void cardListerSetUp2x2(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton2x2);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(iscard2x2Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard2x2Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton2x2);
                    imageView.setImageResource(cardLayout[5]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 6;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[5];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard2x2Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 6;
                        //store to check
                        checkArray[0]= cardLayout[5];

                    }
                    //check for cleared board
                    isBoardCleared();
                }


            }
        });
    }

    public void cardListerSetUp2x3(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton2x3);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(iscard2x3Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard2x3Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton2x3);
                    imageView.setImageResource(cardLayout[6]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 7;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[6];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard2x3Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 7;
                        //store to check
                        checkArray[0]= cardLayout[6];

                    }
                    //check for cleared board
                    isBoardCleared();
                }


            }
        });
    }

    public void cardListerSetUp2x4(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton2x4);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(iscard2x4Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard2x4Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton2x4);
                    imageView.setImageResource(cardLayout[7]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 8;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[7];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard2x4Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 8;
                        //store to check
                        checkArray[0]= cardLayout[7];

                    }
                    //check for cleared board
                    isBoardCleared();
                }


            }
        });
    }
    public void cardListerSetUp3x1(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton3x1);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    if(iscard3x1Flipped){
                        //do nothing cause its flipped
                    }

                    else if(iscard3x1Matched){
                        //do nothing if already matched

                    }else {


                        ImageView imageView = (ImageView) findViewById(R.id.imageButton3x1);
                        imageView.setImageResource(cardLayout[8]);


                        if (isOneCardAlreadyFlipped){
                            //reset if one card is flipped
                            isOneCardAlreadyFlipped = false;
                            //set int for first card case
                            cardB = 9;
                            //reset if any others are flipped
                            resetIfCardFlippedByButtons();
                            //store what this button is
                            checkArray[1]= cardLayout[8];
                            if (checkArray[0].equals(checkArray[1])){

                                closeCardsForMatches();
                                imageView.setImageResource(R.drawable.memorymatch);


                            }else {

                                reflipCardBacks();
                            }

                        }else {

                            //flip card to know to leave up
                            iscard3x1Flipped = true;
                            //Set a card is global flipped
                            isOneCardAlreadyFlipped = true;
                            //set int for first card case
                            cardA = 9;
                            //store to check
                            checkArray[0]= cardLayout[8];

                        }
                        //check for cleared board
                        isBoardCleared();
                    }

            }
        });
    }

    public void cardListerSetUp3x2(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton3x2);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if(iscard3x2Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard3x2Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton3x2);
                    imageView.setImageResource(cardLayout[9]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 10;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[9];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard3x2Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 10;
                        //store to check
                        checkArray[0]= cardLayout[9];

                    }
                    //check for cleared board
                    isBoardCleared();
                }
            }
        });
    }

    public void cardListerSetUp3x3(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton3x3);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if(iscard3x3Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard3x3Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton3x3);
                    imageView.setImageResource(cardLayout[10]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 11;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[10];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard3x3Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 11;
                        //store to check
                        checkArray[0]= cardLayout[10];

                    }
                    //check for cleared board
                    isBoardCleared();
                }
            }
        });
    }

    public void cardListerSetUp3x4(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton3x4);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(iscard3x4Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard3x4Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton3x4);
                    imageView.setImageResource(cardLayout[11]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 12;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[11];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard3x4Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 12;
                        //store to check
                        checkArray[0]= cardLayout[11];

                    }
                    //check for cleared board
                    isBoardCleared();
                }

            }
        });
    }
    public void cardListerSetUp4x1(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton4x1);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(iscard4x1Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard4x1Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton4x1);
                    imageView.setImageResource(cardLayout[12]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 13;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[12];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard4x1Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 13;
                        //store to check
                        checkArray[0]= cardLayout[12];

                    }
                    //check for cleared board
                    isBoardCleared();
                }

            }
        });
    }

    public void cardListerSetUp4x2(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton4x2);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(iscard4x2Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard4x2Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton4x2);
                    imageView.setImageResource(cardLayout[13]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 14;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[13];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard4x2Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 14;
                        //store to check
                        checkArray[0]= cardLayout[13];

                    }
                    //check for cleared board
                    isBoardCleared();
                }

            }
        });
    }

    public void cardListerSetUp4x3(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton4x3);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if(iscard4x3Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard4x3Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton4x3);
                    imageView.setImageResource(cardLayout[14]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 15;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[14];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard4x3Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 15;
                        //store to check
                        checkArray[0]= cardLayout[14];

                    }
                    //check for cleared board
                    isBoardCleared();
                }

            }
        });
    }

    public void cardListerSetUp4x4(){
        final ImageButton card1x1 = (ImageButton)findViewById(R.id.imageButton4x4);
        card1x1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(iscard4x4Flipped){
                    //do nothing cause its flipped
                }

                else if(iscard4x4Matched){
                    //do nothing if already matched

                }else {


                    ImageView imageView = (ImageView) findViewById(R.id.imageButton4x4);
                    imageView.setImageResource(cardLayout[15]);


                    if (isOneCardAlreadyFlipped){
                        //reset if one card is flipped
                        isOneCardAlreadyFlipped = false;
                        //set int for first card case
                        cardB = 16;
                        //reset if any others are flipped
                        resetIfCardFlippedByButtons();
                        //store what this button is
                        checkArray[1]= cardLayout[15];
                        if (checkArray[0].equals(checkArray[1])){

                            closeCardsForMatches();
                            imageView.setImageResource(R.drawable.memorymatch);


                        }else {

                            reflipCardBacks();
                        }

                    }else {

                        //flip card to know to leave up
                        iscard4x4Flipped = true;
                        //Set a card is global flipped
                        isOneCardAlreadyFlipped = true;
                        //set int for first card case
                        cardA = 16;
                        //store to check
                        checkArray[0]= cardLayout[15];

                    }
                    //check for cleared board
                    isBoardCleared();
                }

            }
        });
    }

}

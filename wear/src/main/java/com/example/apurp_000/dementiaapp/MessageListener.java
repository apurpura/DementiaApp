package com.example.apurp_000.dementiaapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by apurpura on 6/18/2015.
 */
public class MessageListener extends WearableListenerService {

    private static final String START_ACTIVITY = "/start_activity";
    private JSONObject json;
    public int TIMEOUT_MS = 10000;
    public MainActivity MainActivity = new MainActivity();
    protected GoogleApiClient mGoogleApiClient;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("onMessageReceive", "made it");
        if( messageEvent.getPath().equalsIgnoreCase(START_ACTIVITY) ) {
            final String message = new String(messageEvent.getData());

            activityFactory(message);

        } else {
            super.onMessageReceived( messageEvent );
        }
    }

    private void activityFactory(String message){
        try {
            json = new JSONObject(message);
            String action = json.get("Action").toString();
            switch (action) {
                case "Text Message":
                    startTextMessageActivity(message);
                    break;
                case "Image Carousel":
                    startCarousel(message);
                    break;
                case "Simon Says Game":
                    simonSaysActivity(message);
                    break;
                case "Pills - Sequence":
                    pillSequence(message);
                    break;
                case "Female Dressed - Sequence":
                    actSequence(message);
                    break;
                case "Memory Matching Game":
                    MemoryMatchingActivity(message);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void simonSaysActivity(String message) {
        Log.d("activatingIntent", "MainActivityIntent");
        Intent messageIntent = new Intent(this, startPage.class);
        messageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        messageIntent.putExtra("text", message);
        this.startActivity(messageIntent);
    }
    private void pillSequence(String message) {
        Log.d("activatingIntent", "MainActivityIntent");
        Intent messageIntent = new Intent(this, PillSequence.class);
        messageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        messageIntent.putExtra("text", message);
        this.startActivity(messageIntent);
    }
    private void actSequence(String message) {
        Log.d("activatingIntent", "MainActivityIntent");
        Intent messageIntent = new Intent(this, ActSequence.class);
        messageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        messageIntent.putExtra("text", message);
        this.startActivity(messageIntent);
    }

    private void startTextMessageActivity(String message){
        Log.d("activatingIntent", "MainActivityIntent");
        Intent messageIntent = new Intent(this, TextMessageActivity.class);
        messageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        messageIntent.putExtra("text", message);
        this.startActivity(messageIntent);
    }

    private void startCarousel( String message) {
        Log.d("activatingIntent", "MainActivityIntent");
        Intent messageIntent = new Intent(this, ImageCarousel.class);
        messageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        messageIntent.putExtra("text", message);
        this.startActivity(messageIntent);
    }

    private void MemoryMatchingActivity(String message){
        Log.d("activatingIntent", "MainActivityIntent");
        Intent messageIntent = new Intent(this, MemoryGameStartPage.class);
        messageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        messageIntent.putExtra("text", message);
        this.startActivity(messageIntent);
    }


    //Image Asset Information From MOBILE

    public void setupGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
    // Request access only to the Wearable API
                .addApi(Wearable.API)
                .build();
                mGoogleApiClient.connect();

    }

    public void onDataChanged(DataEventBuffer dataEvents) {
        setupGoogleApiClient();
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED &&
                    event.getDataItem().getUri().getPath().equals("/image")) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                Asset profileAsset = dataMapItem.getDataMap().getAsset("profileImage");
                Bitmap bitmap = loadBitmapFromAsset(profileAsset);
                // Do something with the bitmap
                MainActivity.zSetNewImage(bitmap);
            }
        }
    }

    public Bitmap loadBitmapFromAsset(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset must be non-null");
        }
        ConnectionResult result =
              mGoogleApiClient.blockingConnect(TIMEOUT_MS, TimeUnit.MILLISECONDS);
        if (!result.isSuccess()) {
            return null;
        }
        // convert asset into a file descriptor and block until it's ready
        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                mGoogleApiClient, asset).await().getInputStream();
                mGoogleApiClient.disconnect();

        if (assetInputStream == null) {
            //Log.w(TAG, "Requested an unknown Asset.");
            return null;
        }
        // decode the stream into a bitmap
        return BitmapFactory.decodeStream(assetInputStream);
    }



}

package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * Created by Ryan on 7/27/2015.
 */
public class ImageTest extends Activity {

Bitmap zTest;

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tran);
        setupGoogleApiClient();

        Button zTestButton = (Button) findViewById(R.id.zSendImage);
        zTestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createDataMap();
            }
        });
    }

    public  void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    public void setupGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d("tag", "onConnected: " + connectionHint);
                        // Now you can use the Data Layer API
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d("tag", "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d("tag", "onConnectionFailed: " + result);
                    }
                })
                .addApi(Wearable.API)
                .build();

    }


    private static Asset createAssetFromBitmap(Bitmap bitmap) {
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        return Asset.createFromBytes(byteStream.toByteArray());
    }
    public void createDataMap(){
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.pillthumb);
        Asset asset = createAssetFromBitmap(bitmap);
        PutDataMapRequest request = PutDataMapRequest.create("/image");
        DataMap dataMap = request.getDataMap();
        dataMap.putAsset("profileImage", asset);
        //dataMap.putInt("profileImage", 90);
        dataMap.putLong("timestamp", System.currentTimeMillis());
        PutDataRequest dataRequest = request.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, dataRequest);
    }
}

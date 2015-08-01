package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
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
private  GoogleApiClient mGoogleApiClient;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tran);
        setupGoogleApiClient();

        Button zSendImage = (Button) findViewById(R.id.zSendImage);
        zSendImage.setOnClickListener(new View.OnClickListener() {
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
                .addApi(Wearable.API)
                .build();

    }

    public void onConnected(){

    }

    private static Asset createAssetFromBitmap(Bitmap bitmap) {
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream);
        return Asset.createFromBytes(byteStream.toByteArray());
    }
    public void createDataMap(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.trantestimage);
        Asset asset = createAssetFromBitmap(bitmap);
        PutDataRequest request = PutDataRequest.create("/image");
        request.putAsset("profileImage", asset);
        Wearable.DataApi.putDataItem(mGoogleApiClient, request);
    }
}

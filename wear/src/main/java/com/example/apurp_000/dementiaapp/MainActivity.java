package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Ryan on 8/1/2015.
 */
public class MainActivity extends Activity {
    ImageView zImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rect_activity_test);
    }

    public void zSetNewImage(Bitmap bitmap){
        zImage = (ImageView) findViewById(R.id.imageView);
        zImage.setImageBitmap(bitmap);
    }
}

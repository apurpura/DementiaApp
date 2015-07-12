package com.example.apurp_000.dementiaapp;

        import android.app.Activity;
        import android.os.Bundle;
        import android.os.Handler;
        import android.widget.ImageSwitcher;
        import android.widget.ImageView;

/**
 * Created by Ryan on 6/24/2015.
 */

public class ImageCarousel extends Activity {
    //Configure Array to Store Pictures.
    Integer[] imageIDs = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4
    };
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_carousel);

        final ImageView imageView = (ImageView) findViewById(R.id.image1);
        //final Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                imageView.setImageResource(imageIDs[i]);
                i++;
                if (i >= imageIDs.length) {
                    i = 0;
                }
                imageView.postDelayed(this, 5000); //set to go off again in 3 seconds.
            }
        };
        imageView.postDelayed(r, 3000);
    }
}



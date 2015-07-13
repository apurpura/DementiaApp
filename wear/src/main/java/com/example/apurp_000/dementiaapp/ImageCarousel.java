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
    Integer[] zImageIDs = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4
    };

    int zImageCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_carousel);

        //Pull the Image View variable from the XML Layout
        final ImageView zImageView = (ImageView) findViewById(R.id.image1);

        //Create a thread that scrolls through the imageID array with a set delay
        //before moving onto the next image.
        Runnable r = new Runnable() {
            public void run() {
                zImageView.setImageResource(zImageIDs[zImageCounter]);
                zImageCounter++;
                if (zImageCounter >= zImageIDs.length) {
                    zImageCounter = 0;
                }
                zImageView.postDelayed(this, 5000); //set to go off again in 5 seconds.
            }
        };
        zImageView.postDelayed(r, 3000);//set inital delay of 3 seconds before starting
    }

    //Insert Code Here for Basic Meta Data for Quiz to Use.
}



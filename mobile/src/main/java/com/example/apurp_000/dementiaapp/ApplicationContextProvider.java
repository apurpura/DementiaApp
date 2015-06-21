package com.example.apurp_000.dementiaapp;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class ApplicationContextProvider extends Application  {

    private static Context mContext;

  /*  public static Context getContext() {
        if(mContext != null)
            return mContext;
        else{
            Intent intent = new Intent(new Context(), SigningOnActivity.class);
            this.startActivity(intent);

        }
    }*/

    public static void setContext(Context mContexty) {
        mContext = mContexty;
    }

}

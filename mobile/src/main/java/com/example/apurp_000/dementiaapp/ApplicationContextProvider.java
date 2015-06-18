package com.example.apurp_000.dementiaapp;
import android.app.Application;
import android.content.Context;

public class ApplicationContextProvider extends Application  {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContexty) {
        mContext = mContexty;
    }

}

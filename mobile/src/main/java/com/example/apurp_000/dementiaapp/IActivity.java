package com.example.apurp_000.dementiaapp;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by apurpura on 7/16/2015.
 */
public abstract class IActivity extends Activity {
    Spinner accountSpinner;
    ArrayAdapter<String> dataAdapter;
    List<String> lables;

    public static Class theClass = IActivity.class;
}

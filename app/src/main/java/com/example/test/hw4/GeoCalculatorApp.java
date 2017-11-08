package com.example.test.hw4;

import android.app.Application;
import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by test on 11/7/2017.
 */


public class GeoCalculatorApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}



package com.edinstudio.app.demos;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by albert on 9/26/14.
 */
public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        ActiveAndroid.dispose();
    }
}

package com.example.laptoptcc.session4;

import android.app.Application;
import android.util.Log;

import com.example.laptoptcc.session4.settings.SharedPrefs;

/**
 * Created by laptopTCC on 2/6/2017.
 */

public class PomodoroApplication extends Application{
    private static final String TAG = PomodoroApplication.class.toString();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        SharedPrefs.init(this);
    }
}

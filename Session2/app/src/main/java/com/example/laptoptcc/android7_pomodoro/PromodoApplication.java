package com.example.laptoptcc.android7_pomodoro;

import android.app.Application;
import android.util.Log;

import com.example.laptoptcc.android7_pomodoro.settings.SharedPrefs;

import static android.content.ContentValues.TAG;


/**
 * Created by laptopTCC on 1/14/2017.
 */

public class PromodoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate: ");
        SharedPrefs.intit(this);
        SharedPrefs.initWorkTime(this);
    }
}

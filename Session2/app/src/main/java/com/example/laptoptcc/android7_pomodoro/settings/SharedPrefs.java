package com.example.laptoptcc.android7_pomodoro.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import static android.content.ContentValues.TAG;

/**
 * Created by laptopTCC on 1/14/2017.
 */

public class SharedPrefs {
    private static final String SHARE_PREFS_NAME = "SP" ;
    private static final String LOGIN_KEY = "LOGIN";
    private static final String TIME_DATA = "TIME DATA";
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private static SharedPrefs instance;
    private static SharedPrefs instanceWorkTime;

    public static SharedPrefs getInstance() {
            return instance;
        }

    public static void intit(Context context){
        instance = new SharedPrefs(context);
    }


    public static SharedPrefs getInstanceWorkTime() {
        return instanceWorkTime;
    }

    public static void initWorkTime(Context context) {instanceWorkTime = new SharedPrefs(context);}


    public SharedPrefs(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPrefs(Context context){
        this.sharedPreferences = context.getSharedPreferences(
                SHARE_PREFS_NAME,
                Context.MODE_PRIVATE
                );
        this.gson = new Gson();
    }

    public void put(LoginCredentials loginCredentials){
        //1. Convert object to String
        String loginJSON = (new Gson()).toJson(loginCredentials);
        //2. put
        this.sharedPreferences.edit().putString(LOGIN_KEY, loginJSON).commit();
    }

    public LoginCredentials getLoginCredentials(){
        //1. Get String;
        String  loginJSON = this.sharedPreferences.getString(LOGIN_KEY,null);
        //2. Convert String to Object
        if(loginJSON == null) return  null;
        LoginCredentials loginCredentials = gson.fromJson(loginJSON,LoginCredentials.class);
        return loginCredentials;
    }

    public void put(TimeData timeData){
        String timeJSON = (new Gson()).toJson(timeData);
        this.sharedPreferences.edit().putString(TIME_DATA,timeJSON).commit();
    }

    public TimeData getTimeData(){
        String timeJSON = this.sharedPreferences.getString(TIME_DATA,null);
        if(timeJSON == null) return null;
        TimeData timeData = gson.fromJson(timeJSON,TimeData.class);
        return timeData;
    }
}

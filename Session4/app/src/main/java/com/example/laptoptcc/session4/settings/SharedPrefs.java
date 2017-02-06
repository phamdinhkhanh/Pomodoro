package com.example.laptoptcc.session4.settings;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
/**
 * Created by laptopTCC on 2/6/2017.
 */


public class SharedPrefs {
    private static final String SHARED_PREFS_NAME = "SP";
    private static final String LOGIN_KEY = "Login";

    private static SharedPrefs instance ;

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public static SharedPrefs getInstance() {
        return instance;
    }

    public static void init(Context context){
        instance = new SharedPrefs(context);
    }

    public SharedPrefs(Context context) {
        this.sharedPreferences =context.getSharedPreferences(
                SHARED_PREFS_NAME,
                Context.MODE_PRIVATE
        );
        gson = new Gson();
    }

    public void put(LoginCredentials loginCredentials){
        //login....=>json String
        Gson gson = new Gson();
        String loginJSON = gson.toJson(loginCredentials);
        //out
        this.sharedPreferences.edit().putString(LOGIN_KEY,loginJSON).commit();

    }
    public  LoginCredentials getLoginCredentials(){
        String loginJSON = this.sharedPreferences.getString(LOGIN_KEY,null);
        //string to object
        if (loginJSON==null)return null;
        LoginCredentials loginCredentials = gson.fromJson(loginJSON,LoginCredentials.class);
        return loginCredentials;
    }

    public String getAccessToken(){
        LoginCredentials loginCredentials = getLoginCredentials();
        if (loginCredentials !=null){
            return loginCredentials.getAccessToken();
        }
        return null;
    }
}

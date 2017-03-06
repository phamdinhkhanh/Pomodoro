package techkids.vn.android7pomodoro.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import techkids.vn.android7pomodoro.settings.Setting;
import techkids.vn.android7pomodoro.settings.SharedPrefs;

/**
 * Created by huynq on 2/15/17.
 */

public class Utils {
    //    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final Utils instance = new Utils();
    private static final String TAG = "Utils";

    private Utils (){

    }
    public String getUUID(){
        String uuid = UUID.randomUUID().toString();
        Log.d(TAG, "getUUID: " + uuid);
        return uuid;
    }

    public static void setSolidColor(View v, String colorString) {
        GradientDrawable drawable = (GradientDrawable)v.getBackground();
        drawable.setColor(Color.parseColor(colorString));
    }


    public static Setting getSetting(){
        Setting setting = SharedPrefs.getInstance().getSettings();
        if (setting != null){
            return setting;
        } else {
            return new Setting(20,20,20,20);
        }
    }
    private static int count;
    public static <T> void enqueueWithRetry(Call<T> call, Callback<T> callback){
        call.clone().enqueue(callback);
    }
}

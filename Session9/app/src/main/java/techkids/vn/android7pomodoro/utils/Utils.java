package techkids.vn.android7pomodoro.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;

import java.util.UUID;

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

}

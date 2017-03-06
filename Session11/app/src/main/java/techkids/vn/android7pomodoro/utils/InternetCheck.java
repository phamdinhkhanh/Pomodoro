package techkids.vn.android7pomodoro.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


/**
 * Created by laptopTCC on 2/28/2017.
 */

public class InternetCheck {
    private static final String TAG = InternetCheck.class.toString();
    public static InternetCheck instance = new InternetCheck();
    public boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isNetActive = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (isNetActive){
        Log.d(TAG,String.format("InternetCheck: TRUE"));}
        else {
            Log.d(TAG,String.format("InternetCheck: FALSE"));
        }
        return isNetActive;
    }
}

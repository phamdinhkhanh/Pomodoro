package techkids.vn.android7pomodoro;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import techkids.vn.android7pomodoro.networks.services.PomodoroService;
import techkids.vn.android7pomodoro.settings.SharedPrefs;

/**
 * Created by apple on 1/14/17.
 */

public class PomodoroApplication extends Application {

    private static final String TAG = "PomodoroApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        SharedPrefs.init(this);

        Intent intent = new Intent(this, PomodoroService.class);
        startService(intent);

        /*for (Task task : DbContext.instance.allTasks()) {
            //Log.d(TAG, String.format("onCreate: %s", task));
        }*/
    }
}

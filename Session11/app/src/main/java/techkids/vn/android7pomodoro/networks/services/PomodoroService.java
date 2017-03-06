package techkids.vn.android7pomodoro.networks.services;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import techkids.vn.android7pomodoro.events.TimerCommandEvent;


/**
 * Created by laptopTCC on 3/4/2017.
 */

public class PomodoroService extends Service{
    private static final String TAG = PomodoroService.class.toString();
    private CountDownTimer countDownTimer;

    @Nullable
    public void onCreate(){
        super.onCreate();
        EventBus.getDefault().register(this);
    };
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onCommand(TimerCommandEvent event){
        Log.d(TAG,"onCommand: hura");
    }

    public void onStartTimer(){
        countDownTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG,String.format("Tick %s",millisUntilFinished));
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
    };
}

package techkids.vn.android7pomodoro.networks.services;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import techkids.vn.android7pomodoro.events.TimerCommandEvent;
import techkids.vn.android7pomodoro.events.TimerTickEvent;
import techkids.vn.android7pomodoro.settings.Setting;
import techkids.vn.android7pomodoro.utils.Utils;

/**
 * Created by laptopTCC on 3/5/2017.
 */

public class TimeServices extends Service {
    private CountDownTimer countDownTimer;
    private final String TAG = TimeServices.class.toString();
    private long timeUntilFinished;
    private final Setting SETTING= Utils.getSetting();
    private boolean isPause;
    private boolean isStop;


    @Override
    public void onCreate(){
        super.onCreate();
        EventBus.getDefault().register(this);
        startTimer(SETTING.getTimeBreak()*60*1000+1000,1000);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Subscribe
    public void onCommand(TimerCommandEvent event){
        switch (event.getTimerCommand()){
            case START_TIMER:
                startTimer(SETTING.getTimeBreak() * 60 * 1000 + 1000, 1000);
                break;
            case STOP_TIMER:
                countDownTimer.onFinish();
                countDownTimer.cancel();
                break;
            case RESUME_TIMER:
                startTimer(timeUntilFinished,1000);
                break;
            case PAUSE_TIMER:
                countDownTimer.cancel();
                break;
        }
    }

    public void startTimer(final long millisInFuture, long countDownInterval){
        countDownTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondRemaining = millisUntilFinished/1000;
                int minute = (int) (secondRemaining/60);
                int second = (int) (secondRemaining - minute*60);
                timeUntilFinished = millisUntilFinished;
                TimerTickEvent time = new TimerTickEvent(millisUntilFinished);
                EventBus.getDefault().post(time);
            }

            @Override
            public void onFinish() {
                TimerTickEvent event = new TimerTickEvent(timeUntilFinished);
                EventBus.getDefault().post(event);
            }
        };
        countDownTimer.start();
    }
}

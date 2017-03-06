package techkids.vn.android7pomodoro.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.lzyzsd.circleprogress.DonutProgress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.activities.TaskActivity;
import techkids.vn.android7pomodoro.databases.models.Task;
import techkids.vn.android7pomodoro.events.TimerCommand;
import techkids.vn.android7pomodoro.events.TimerCommandEvent;
import techkids.vn.android7pomodoro.events.TimerTickEvent;
import techkids.vn.android7pomodoro.settings.Setting;
import techkids.vn.android7pomodoro.utils.Utils;
/**
 * Created by laptopTCC on 2/18/2017.
 */

public class PomodoroFragment extends Fragment {

    //@Nullable
    @BindView(R.id.pg_time)
    DonutProgress pg_time;

    //@Nullable
    @BindView(R.id.ib_stop)
    ImageButton ib_stop;


    @BindView(R.id.ib_pause)
    ImageButton ib_pause;

    private boolean isPause;
    private boolean isStop;
    private long timeUtilFinished;
    final Setting SETTING = Utils.getSetting();
    private Task task;

    public void setSecond(int second) {
        this.second = second;
    }

    private int second;

    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    private Calendar calendar = Calendar.getInstance();

    public PomodoroFragment(){
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pomodoro, container, false);
        EventBus.getDefault().register(this);
        setupUI(view);
        return view;
    }

    public String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String time = sdf.format(new Date());
        return time;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mni_ok){
        getActivity().onBackPressed();
        }
        return true;
    }

    public void setupUI(View view){
        ButterKnife.bind(this, view);
        if(getActivity() instanceof TaskActivity){
                ((TaskActivity) getActivity()).getSupportActionBar().setTitle("Timer");
        }

        pg_time.setText(this.getTime());
        pg_time.setProgress(60);
        addListener();
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void addListener(){
        ib_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isStop){
                    if (!isPause){
                        isPause = true;
                        TimerCommandEvent event = new TimerCommandEvent(TimerCommand.PAUSE_TIMER);
                        EventBus.getDefault().post(event);
                        ib_pause.setImageResource(R.drawable.ic_pause_white_24px);
                    } else {
                        isPause = false;
                        TimerCommandEvent event = new TimerCommandEvent(TimerCommand.RESUME_TIMER);
                        EventBus.getDefault().post(event);
                        ib_pause.setImageResource(R.drawable.ic_play_arrow_24px);
                    }
                }
            }
        });

        ib_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerCommandEvent event = new TimerCommandEvent(TimerCommand.STOP_TIMER);
                EventBus.getDefault().post(event);
                isStop = true;
                //stop de ? va code tiep
            }
        });
    }


    @Subscribe
    public void onTimerTick(TimerTickEvent event){
        if(event.getTick() == 0){
            pg_time.setText("Done!");
            return;
        }
        long millisUntilFinished = event.getTick();
        long secondRemaining = millisUntilFinished/1000;
        int minute = (int) (secondRemaining/60);
        int second = (int) (secondRemaining-(minute*60));
        timeUtilFinished = millisUntilFinished;

        pg_time.setText(String.format("%02d:%02d",minute,second));
        pg_time.setMax(SETTING.getTimeBreak()*60);
        pg_time.setProgress((int) secondRemaining);

    }
}

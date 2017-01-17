package com.example.laptoptcc.android7_pomodoro.activities;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.laptoptcc.android7_pomodoro.R;
import com.example.laptoptcc.android7_pomodoro.settings.SharedPrefs;
import com.example.laptoptcc.android7_pomodoro.settings.TimeData;

import java.sql.Time;

import static com.example.laptoptcc.android7_pomodoro.R.id.sb_worktime;
import static com.example.laptoptcc.android7_pomodoro.R.id.time;
import static com.example.laptoptcc.android7_pomodoro.R.id.txt_breaktime;
import static com.example.laptoptcc.android7_pomodoro.R.id.txt_worktime;

/**
 * Created by laptopTCC on 1/16/2017.
 */

public class Setting extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private SeekBar sbWorkTime;
    private SeekBar sbBreakTime;
    private SeekBar sbLongBreakTime;
    private TextView txtWorkTime;
    private TextView txtBreakTime;
    private TextView txtLongBreakTime;
    private static final int WORKINGTIME = 25;
    private static final int BREAKTIME = 10;
    private static final int LONGBREAKTIME = 20;
    private Button def;
    private Spinner spinner;
    public static final String TAG = "WORKING TIME" ;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getReferrerences();
        addListener();
        setupUI();
    }

    public void getReferrerences(){
        sbWorkTime = (SeekBar) this.findViewById(R.id.sb_worktime);
        sbBreakTime = (SeekBar) this.findViewById(R.id.sb_breaktime);
        sbLongBreakTime = (SeekBar) this.findViewById(R.id.sb_longbreak);
        txtWorkTime = (TextView) this.findViewById(R.id.txt_worktime);
        txtBreakTime = (TextView) this.findViewById(R.id.txt_breaktime);
        txtLongBreakTime = (TextView) this.findViewById(R.id.txt_longbreak);
        def = (Button) this.findViewById(R.id.bt_default);
        spinner = (Spinner) this.findViewById(R.id.sp_break);
        def.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingDefault();
            }
        });
        this.settingDefault();
    }

    public void setupUI(){
        String[] numbers = new String[]{
                "1","2","3"
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                numbers);
        spinner.setAdapter(arrayAdapter);
    }

    public void addListener(){
        sbWorkTime.setProgress(WORKINGTIME);
        sbBreakTime.setProgress(BREAKTIME);
        sbLongBreakTime.setProgress(LONGBREAKTIME);
        if(SharedPrefs.getInstanceWorkTime().getTimeData() != null){
            TimeData timeData = SharedPrefs.getInstanceWorkTime().getTimeData();
            this.sbWorkTime.setProgress(Integer.valueOf(timeData.getWorkTime()));
            this.sbBreakTime.setProgress(Integer.valueOf(timeData.getBreakTime()));
            this.sbLongBreakTime.setProgress(Integer.valueOf(timeData.getLongBreakTime()));
        }
        txtWorkTime.setText(getResources().getString(R.string.worktime)+sbWorkTime.getProgress()+
        getString(R.string.mins));
        txtBreakTime.setText(getResources().getString(R.string.breaktime)+sbBreakTime.getProgress()+
        getString(R.string.mins));
        txtLongBreakTime.setText(getResources().getString(R.string.longbreaktime)+sbLongBreakTime.getProgress()+
        getString(R.string.mins));

        sbWorkTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtWorkTime.setText(getString(R.string.worktime) + " " +sbWorkTime.getProgress()+ " " +
                        getString(R.string.mins));
                SharedPrefs.getInstanceWorkTime().put(new TimeData(String.valueOf(sbWorkTime.getProgress()),
                        String.valueOf(sbBreakTime.getProgress()),
                        String.valueOf(sbLongBreakTime.getProgress())));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbBreakTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtBreakTime.setText(getString(R.string.breaktime)+sbBreakTime.getProgress()+
                        getString(R.string.mins));
                SharedPrefs.getInstanceWorkTime().put(new TimeData(String.valueOf(sbWorkTime.getProgress()),
                        String.valueOf(sbBreakTime.getProgress()),
                        String.valueOf(sbLongBreakTime.getProgress())));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){

            }
        });

        sbLongBreakTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtLongBreakTime.setText(getString(R.string.longbreaktime)+sbLongBreakTime.getProgress()+
                        getString(R.string.mins));
                SharedPrefs.getInstanceWorkTime().put(new TimeData(String.valueOf(sbWorkTime.getProgress()),
                        String.valueOf(sbBreakTime.getProgress()),
                        String.valueOf(sbLongBreakTime.getProgress())));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_setting);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void settingDefault(){
        if(SharedPrefs.getInstanceWorkTime() != null)
        sbWorkTime.setProgress(WORKINGTIME);
        sbBreakTime.setProgress(BREAKTIME);
        sbLongBreakTime.setProgress(LONGBREAKTIME);
    }
}

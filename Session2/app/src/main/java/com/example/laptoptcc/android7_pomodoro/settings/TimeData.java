package com.example.laptoptcc.android7_pomodoro.settings;

/**
 * Created by laptopTCC on 1/16/2017.
 */

public class TimeData {
    private String workTime;
    private String breakTime;
    private String longBreakTime;

    public TimeData(String workTime, String breakTime, String longBreakTime) {
        this.workTime = workTime;
        this.breakTime = breakTime;
        this.longBreakTime = longBreakTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public String getBreakTime() {
        return breakTime;
    }

    public String getLongBreakTime() {
        return longBreakTime;
    }
}

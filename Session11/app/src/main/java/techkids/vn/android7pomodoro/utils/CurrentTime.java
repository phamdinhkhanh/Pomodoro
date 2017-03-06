package techkids.vn.android7pomodoro.utils;

/**
 * Created by laptopTCC on 3/6/2017.
 */

public class CurrentTime {
    private int second;
    private int minute;
    private int hour;

    public CurrentTime(int second, int minute, int hour) {
        this.second = second;
        this.minute = minute;
        this.hour = hour;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}

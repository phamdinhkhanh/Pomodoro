package techkids.vn.android7pomodoro.events;

/**
 * Created by laptopTCC on 3/4/2017.
 */

public class TimerCommandEvent {
    private TimerCommand timerCommand;

    public TimerCommand getTimerCommand() {
        return timerCommand;
    }

    public void setTimerCommand(TimerCommand timerCommand) {
        this.timerCommand = timerCommand;
    }

    public TimerCommandEvent(TimerCommand timerCommand) {

        this.timerCommand = timerCommand;
    }
}

package techkids.vn.android7pomodoro.events;

/**
 * Created by laptopTCC on 3/5/2017.
 */

public class DataChange {
    private String toastNotification;

    private DataChangeType dataChangeType;

    public DataChangeType getDataChangeType() {
        return dataChangeType;
    }

    public void setDataChangeType(DataChangeType dataChangeType) {
        this.dataChangeType = dataChangeType;
    }

    public DataChange(String toastNotification, DataChangeType dataChangeType) {
        this.toastNotification = toastNotification;
        this.dataChangeType = dataChangeType;
    }

    public String getToastNotification() {
        return toastNotification;
    }

    public DataChange(String toastNotification) {

        this.toastNotification = toastNotification;
    }
}

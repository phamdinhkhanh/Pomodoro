package techkids.vn.android7pomodoro.databases.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import techkids.vn.android7pomodoro.utils.Utils;

/**
 * Created by huynq on 2/8/17.
 */

public class Task extends RealmObject{
    private String name;
    private String color;
    private float paymentPerHour;
    private boolean isDone = true;
    private String local_id;
    @PrimaryKey
    private String id;
    private String due_date;

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", paymentPerHour=" + paymentPerHour +
                ", isDone=" + isDone +
                ", id='" + id + '\'' +
                ", local_id='" + local_id + '\'' +
                ", due_date='" + due_date + '\'' +
                '}';
    }

    public Task(){};

    public Task(String name, String color, float paymentPerHour, boolean isDone) {
        this.name = name;
        this.color = color;
        this.paymentPerHour = paymentPerHour;
        this.isDone = isDone;
    }

    public Task(String name, String color, float payment_per_hour, boolean isDone, String id, String local_id, String due_date) {
        this.name = name;
        this.color = color;
        this.paymentPerHour = payment_per_hour;
        this.isDone = isDone;
        this.id = id;
        this.local_id = local_id;
        this.due_date = due_date;
    }

    public Task(String name, String color, float paymentPerHour, boolean isDone, String id, String due_date) {
        this.name = name;
        this.color = color;
        this.paymentPerHour = paymentPerHour;
        this.isDone = isDone;
        this.id = id;
        this.due_date = due_date;
        this.local_id = Utils.instance.getUUID();
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Task(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Task(String name, String color, float paymentPerHour) {
        this.name = name;
        this.color = color;
        this.paymentPerHour = paymentPerHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPaymentPerHour() {
        return paymentPerHour;
    }

    public void setPaymentPerHour(float paymentPerHour) {
        this.paymentPerHour = paymentPerHour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

}

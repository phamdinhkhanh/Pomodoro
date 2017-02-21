package techkids.vn.android7pomodoro.databases.models;

/**
 * Created by huynq on 2/8/17.
 */

public class Task {
    private String name;
    private String color;
    private float paymentPerHour;
    private boolean isDone = true;

    public Task(String name, String color, float paymentPerHour, boolean isDone) {
        this.name = name;
        this.color = color;
        this.paymentPerHour = paymentPerHour;
        this.isDone = isDone;
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

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}

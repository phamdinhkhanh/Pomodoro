package techkids.vn.android7pomodoro.databases;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import techkids.vn.android7pomodoro.databases.models.Task;
import techkids.vn.android7pomodoro.databases.models.TaskColor;

import static techkids.vn.android7pomodoro.adapters.TaskAdapter.TAG;


/**
 * Created by huynq on 2/8/17.
 */

public class DbContext {

    public static final DbContext instance = new DbContext();

    ArrayList<Task> tasks;

    public List<Task> allTasks() {
        //Fake data (dummy data)
        //1: Create array list
        if (tasks == null) {
            tasks = new ArrayList<>();

            //2: Add some tassk and return
            tasks.add(new Task("Study recyclerview", TaskColor.COLORS[0], 2.3f));
            tasks.add(new Task("Practice recyclerview", TaskColor.COLORS[1], 1.1f));
            tasks.add(new Task("Practice networking", TaskColor.COLORS[2], 0.5f));
            tasks.add(new Task("Party End-lectures", TaskColor.COLORS[3], 0.9f));
            tasks.add(new Task("Study API", "#D4E157"));
        }

        return tasks;
    }

    public void clearAllTask(){
        DbContext.instance.tasks.clear();
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
    }

    public boolean deleteTask(Task deleteTask) {
        /*if (deleteTask.getLocal_id() == null)
        {
            Log.d(ContentValues.TAG, "deleteTask: this task have null local id");
            return false;
        }*/
        for (Task t : tasks) {
            if (t.getLocal_id() != null )
                if (t.getLocal_id().equals(deleteTask.getLocal_id())) {
                    tasks.remove(t);
                    TaskManager.instance.deleteTask(deleteTask);
                    return true;
                }
                else {
                    //Log.d(ContentValues.TAG, String.format("deleteTask: %s", "local id null"));
                }
        }

        return false;
    }

    public void updateTask(Task updateTask) {
        String id = updateTask.getId();
        for (Task t : tasks) {
            if (t.getId().equals(id)) {
                t.setName(updateTask.getName());
                t.setColor(updateTask.getColor());
                t.setDone(updateTask.isDone());
                t.setPaymentPerHour(updateTask.getPaymentPerHour());
                t.setLocal_id(updateTask.getLocal_id());
                t.setDue_date(updateTask.getDue_date());
                Log.d(TAG, String.format("onOptionsItemSelected: %s ", t.toString()));
                break;
            }
        }
        TaskManager.instance.updateTask(updateTask);
    }

    public void setTask(ArrayList<Task> tasks){
        this.tasks = tasks;
    }
}

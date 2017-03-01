package techkids.vn.android7pomodoro.databases;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techkids.vn.android7pomodoro.databases.models.Task;
import techkids.vn.android7pomodoro.networks.NetContext;
import techkids.vn.android7pomodoro.networks.TaskResponseJson;
import techkids.vn.android7pomodoro.networks.jsonmodels.DeleteResponseJson;
import techkids.vn.android7pomodoro.networks.services.AddNewTask;
import techkids.vn.android7pomodoro.networks.services.DeleteTask;
import techkids.vn.android7pomodoro.networks.services.EditTask;
import techkids.vn.android7pomodoro.networks.services.GetAllTask;

/**
 * Created by laptopTCC on 2/25/2017.
 */

public class TaskManager {
    public static TaskManager instance = new TaskManager();
    public interface GetTasksListener{
           void onGetTasks(boolean ok);
    }

    private GetTasksListener getTasksListener;

    public GetTasksListener getGetTasksListener() {
        return getTasksListener;
    }

    public void setGetTasksListener(GetTasksListener getTasksListener) {
        this.getTasksListener = getTasksListener;
    }

    private String TAG = TaskManager.class.toString();
    private TaskManager(){};
    public void getTaskFromServer(){
        NetContext.instance
                .create(GetAllTask.class)
                .getAllTask()
                .enqueue(new Callback<List<TaskResponseJson>>() {
                    @Override
                    public void onResponse(Call<List<TaskResponseJson>> call, Response<List<TaskResponseJson>> response) {
                        List<TaskResponseJson> taskList = response.body();
                        if (taskList!= null){
                            DbContext.instance.clearAllTask();
                            for(TaskResponseJson taskResponse: taskList){
                                if(taskResponse.getColor() != null){
                                    Task task = new Task(
                                            taskResponse.getName(),
                                            taskResponse.getColor(),
                                            (float) taskResponse.getPaymentPerHour(),
                                            taskResponse.isDone(),
                                            taskResponse.getId(),
                                            taskResponse.getLocal_id(),
                                            taskResponse.getDue_date()
                                    );
                                    DbContext.instance.addOrUpdate(task);
                                }
                            }
                            getTasksListener.onGetTasks(true);
                            Log.d(TAG,"Load all Task from Server");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TaskResponseJson>> call, Throwable t) {
                        Log.d(TAG, String.format("on Load All Task Failure: %s",t.getCause()));
                        getTasksListener.onGetTasks(false);
                        //Toast.makeText(TaskActivity.class., "Logged in", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void addNewTask(final Task newTask){
        TaskResponseJson taskResponseJson = new TaskResponseJson(
                newTask.getLocal_id(),
                newTask.getName(),
                newTask.getPaymentPerHour(),
                newTask.getDue_date(),
                newTask.isDone(),
                newTask.getId(),
                newTask.getColor()
        );
        NetContext.instance
                  .create(AddNewTask.class)
                  .addNewTask(taskResponseJson)
                  .enqueue(new Callback<TaskResponseJson>() {
                      @Override
                      public void onResponse(Call<TaskResponseJson> call, Response<TaskResponseJson> response) {
                            //Log.d(TAG, String.format("addnewTask successful:"));
                      }

                      @Override
                      public void onFailure(Call<TaskResponseJson> call, Throwable t) {
                          //Log.d(TAG, String.format("on Add Failure: %s",t.getCause().toString()));
                      }
                  });
    }

    public void updateTask(final Task updateTask){
        final String local_id = updateTask.getLocal_id();
        final TaskResponseJson taskResponseJson = new TaskResponseJson(
                updateTask.getLocal_id(),
                updateTask.getName(),
                updateTask.getPaymentPerHour(),
                updateTask.getDue_date(),
                updateTask.isDone(),
                updateTask.getId(),
                updateTask.getColor()
        );

        NetContext.instance.create(EditTask.class)
                  .editTask(local_id, taskResponseJson)
                  .enqueue(new Callback<TaskResponseJson>() {
                      @Override
                      public void onResponse(Call<TaskResponseJson> call, Response<TaskResponseJson> response) {
                          if(response.body() != null)
                          Log.d(TAG, String.format("Update successful:",response.code()));

                          for(Task t: DbContext.instance.allTask()){
                              Log.d(TAG,t.toString());
                          }
                      }

                      @Override
                      public void onFailure(Call<TaskResponseJson> call, Throwable t) {
                          Log.d(TAG, String.format("update Failure:",t.getCause()));
                      }
                  });

    }

    public void deleteTask(final Task deletedTask){
        final String local_id = deletedTask.getLocal_id();
        NetContext.instance.create(DeleteTask.class)
                  .deleteTask(local_id)
                  .enqueue(new Callback<DeleteResponseJson>() {
                      @Override
                      public void onResponse(Call<DeleteResponseJson> call, Response<DeleteResponseJson> response) {
                          //Log.d(TAG, String.format("Delete successful:",response.code()));
                          DbContext.instance.deleteTask(deletedTask);
                      }

                      @Override
                      public void onFailure(Call<DeleteResponseJson> call, Throwable t) {
                          //Log.d(TAG, String.format("on Delete Failure: %s %s",local_id,t.getCause()));
                      }
                  });
    }

}

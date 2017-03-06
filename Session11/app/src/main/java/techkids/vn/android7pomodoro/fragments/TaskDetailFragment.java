package techkids.vn.android7pomodoro.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.activities.TaskActivity;
import techkids.vn.android7pomodoro.adapters.TaskAdapter;
import techkids.vn.android7pomodoro.adapters.TaskColorAdapter;
import techkids.vn.android7pomodoro.databases.DbContext;
import techkids.vn.android7pomodoro.databases.TaskManager;
import techkids.vn.android7pomodoro.databases.models.Task;
import techkids.vn.android7pomodoro.decorations.TaskColorDecor;
import techkids.vn.android7pomodoro.events.DataChange;
import techkids.vn.android7pomodoro.events.DataChangeType;
import techkids.vn.android7pomodoro.networks.NetContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {

    private static String TAG = "TaskDetailFragment";
    NetContext netContext = new NetContext();
    @BindView(R.id.rv_task_color)
    RecyclerView rvTaskColor;

    @BindView(R.id.et_task_name)
    EditText etTaskName;

    @BindView(R.id.et_payment_per_hour)
    EditText etPaymentPerHour;

    @BindView(R.id.sw_isDone)
    Switch swisDone;

    private TaskColorAdapter taskColorAdapter;

    private String title;
    private Task task;
    private ProgressDialog progressDialog;

    public TaskDetailFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        EventBus.getDefault().register(this);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getContext());
        taskColorAdapter = new TaskColorAdapter();

        rvTaskColor.setLayoutManager(new GridLayoutManager(this.getContext(), 4));
        rvTaskColor.setAdapter(taskColorAdapter);
        rvTaskColor.addItemDecoration(new TaskColorDecor());

        if (getActivity() instanceof TaskActivity) {
            ((TaskActivity) getActivity()).getSupportActionBar().setTitle(title);
        }

        if (task != null) {
            // Edit
            etTaskName.setText(task.getName());
            etPaymentPerHour.setText(String.format("%.1f", task.getPaymentPerHour()));
            taskColorAdapter.setSelectedColor(task.getColor());
            swisDone.setChecked(task.isDone());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_task, menu);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");


        if (item.getItemId() == R.id.mni_ok) {
            Log.d(TAG, "onOptionsItemSelected: OK clicked");

            // 1: Get data from UI
            String taskName = etTaskName.getText().toString();
            final float paymentPerHour = Float.parseFloat(etPaymentPerHour.getText().toString());
            String color = taskColorAdapter.getSelectedColor();
            boolean isDone = swisDone.isChecked();

            //boolean isDone = sw_isDone.isChecked();

            Task newTask = new Task(taskName, color, paymentPerHour, isDone, "", "");

            if (task == null) {
                progressDialog.setMessage("Add and Save to Server...");
                progressDialog.setCancelable(false);
                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getActivity().onBackPressed();
                    }
                });
                progressDialog.show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                },2000);
                DbContext.instance.add(newTask);
                TaskAdapter.taskAdapter.notifyDataSetChanged();
                TaskManager.instance.addNewTask(newTask);
                TaskManager.instance.setAddNewTaskListener(new TaskManager.AddNewTaskListener() {
                    @Override
                    public void onAddNewTask(boolean ok) {
                        if(ok){
                            ((TaskActivity) getActivity()).onChangeFragment(new TaskFragment(),true);
                            Toast.makeText(getContext(),"Add to server success",Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new DataChange("Successful"));
                        }
                        else {
                            ((TaskActivity) getActivity()).onChangeFragment(new TaskFragment(),true);
                            Toast.makeText(getContext(),"No internet connection, can't Save to server, add to Database local success",Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new DataChange("UnSucessful"));
                        }
                    }
                });

            }

            else {
                progressDialog.setMessage("Update and save to Server..");
                progressDialog.setCancelable(false);
                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getActivity().onBackPressed();
                    }
                });

                progressDialog.show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                },2000);

                newTask.setId(task.getId());
                newTask.setLocal_id(task.getLocal_id());
                newTask.setDue_date(task.getDue_date());
                DbContext.instance.addOrUpdate(newTask);
                TaskAdapter.taskAdapter.notifyDataSetChanged();
                TaskManager.instance.updateTask(newTask);
                TaskManager.instance.setUpdateTaskListener(new TaskManager.UpdateTaskListener() {
                    @Override
                    public void onUpdateTask(boolean ok) {
                        if(ok){
                            //progressDialog.dismiss();
                            ((TaskActivity) getActivity()).onChangeFragment(new TaskFragment(),true);
                            //Toast.makeText(getContext(), "Update to Server success", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new DataChange("Successful", DataChangeType.SUCESSFUL));
                        }
                        else {
                            //progressDialog.dismiss();
                            ((TaskActivity) getActivity()).onChangeFragment(new TaskFragment(),true);
                            //Toast.makeText(getContext(), "No internet connection, can't Save to server, update to Database local success", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new DataChange("UnSuccessful",DataChangeType.UNSUCESSFUL));
                        }
                    }
                });
            }
        }
        return false;
    }

    @Subscribe
    public void onEvent(DataChange dataChange){
        if(dataChange.getDataChangeType() == DataChangeType.SUCESSFUL){
            Toast.makeText(getContext(), dataChange.getToastNotification(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "Update to Server success", Toast.LENGTH_SHORT).show();
        }
        else if(dataChange.getDataChangeType() == DataChangeType.UNSUCESSFUL){
            Toast.makeText(getContext(), "No internet connection, can't Save to server, update to Database local success", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), dataChange.getToastNotification(), Toast.LENGTH_SHORT).show();
        }
    }
}




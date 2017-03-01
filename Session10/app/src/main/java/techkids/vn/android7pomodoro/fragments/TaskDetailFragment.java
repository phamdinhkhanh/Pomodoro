package techkids.vn.android7pomodoro.fragments;


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

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.activities.TaskActivity;
import techkids.vn.android7pomodoro.adapters.TaskColorAdapter;
import techkids.vn.android7pomodoro.databases.DbContext;
import techkids.vn.android7pomodoro.databases.TaskManager;
import techkids.vn.android7pomodoro.databases.models.Task;
import techkids.vn.android7pomodoro.decorations.TaskColorDecor;
import techkids.vn.android7pomodoro.networks.NetContext;
import techkids.vn.android7pomodoro.utils.InternetCheck;

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
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);

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
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");


        if (item.getItemId() == R.id.mni_ok) {
            Log.d(TAG, "onOptionsItemSelected: OK clicked");

            // 1: Get data from UI
            String taskName = etTaskName.getText().toString();
            float paymentPerHour = Float.parseFloat(etPaymentPerHour.getText().toString());
            String color = taskColorAdapter.getSelectedColor();
            boolean isDone = swisDone.isChecked();

            //boolean isDone = sw_isDone.isChecked();

            Task newTask = new Task(taskName, color, paymentPerHour, isDone, "", "");

            if (task == null) {
                DbContext.instance.add(newTask);
                TaskManager.instance.addNewTask(newTask);
                if (InternetCheck.instance.isNetWorkAvailable(getContext())) {
                    Toast.makeText(getContext(), "Saved to Server", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(getContext(), "No internet connection, can't Save to server, just save to Database local", Toast.LENGTH_SHORT);
                }
            } else {
                newTask.setId(task.getId());
                newTask.setLocal_id(task.getLocal_id());
                newTask.setDue_date(task.getDue_date());
                TaskManager.instance.updateTask(newTask);
                DbContext.instance.addOrUpdate(newTask);
                if (InternetCheck.instance.isNetWorkAvailable(getContext())) {
                    Toast.makeText(getContext(), "Saved to Server", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(getContext(), "No internet connection, can't Save to server, just save to Database local", Toast.LENGTH_SHORT);
                }
            }

            ((TaskActivity) getActivity()).onChangeFragment(new TaskFragment(),true);
        }
        return false;
    }
}




package techkids.vn.android7pomodoro.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.activities.TaskActivity;
import techkids.vn.android7pomodoro.adapters.TaskAdapter;
import techkids.vn.android7pomodoro.adapters.viewholders.TaskViewHolder;
import techkids.vn.android7pomodoro.databases.TaskManager;
import techkids.vn.android7pomodoro.databases.models.Task;
import techkids.vn.android7pomodoro.networks.TaskFragmentListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    @BindView(R.id.rv_task)
    RecyclerView rvTask;

    private TaskAdapter taskAdapter;

    private static String TAG = "TaskFragment";

    TaskFragmentListener taskFragmentListener;

    public void onAttach(Context context){
        super.onAttach(context);
        //taskFragmentListener = (TaskFragmentListener) context;
    }

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_task, container, false);

        /*if(InternetCheck.instance.isNetWorkAvailable(this.getContext())){
            TaskManager.instance.getTaskFromServer();
        } else {
            DbContext.instance.allTask();
        }*/

        addListener();
        setupUI(view);
        ButterKnife.bind(this,view);
        return view;
    }

    private void addListener() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);

        taskAdapter = TaskAdapter.taskAdapter;
        rvTask.setAdapter(taskAdapter);
        rvTask.setLayoutManager(new LinearLayoutManager(this.getContext()));

        taskAdapter.setTaskItemClickListener(new TaskAdapter.TaskItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Log.d(TAG, String.format("onItemClick: %s", task));
                TaskDetailFragment taskDetailFragment = new TaskDetailFragment();

                taskDetailFragment.setTitle("Edit task");
                taskDetailFragment.setTask(task);

                //TODO: Make TaskActivity and Fragment independent
                ((TaskActivity)getActivity()).replaceFragment(taskDetailFragment, true);
            }
        });

        taskAdapter.setTaskItemLongClick(new TaskAdapter.TaskItemLongClick() {
            @Override
            public void onLongClick(Task task) {
                showAlertDelete(task);
                if(TaskViewHolder.isClick){
                    Log.d(TAG, "clickonPlay");
                    ((TaskActivity)getActivity()).replaceFragment(new PomodoroFragment(),true);
                    TaskViewHolder.isClick = false;
                }
            }
        });

        taskAdapter.setImageBottonClickListener(new TaskAdapter.TaskImageBottonClickListener() {
            @Override
            public void onClick() {
                Log.d(TAG,"PomodoroFragment");
                ((TaskActivity)getActivity()).replaceFragment(new PomodoroFragment(),true);
            }
        });



        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle(R.string.tasks);

        //menu options

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rvTask.addItemDecoration(dividerItemDecoration);

        setHasOptionsMenu(true);
    }


    @OnClick(R.id.fab)
    void onFabClick() {
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        taskDetailFragment.setTitle("Add new task");

        //TODO: Make TaskActivity and Fragment independent
        ((TaskActivity)getActivity()).replaceFragment(taskDetailFragment,true);
    }


    private void showAlertDelete(final Task task) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this.getContext());
        builder.setCancelable(true)
                .setTitle("Delete")
                .setMessage("Delete this task")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position){
                            TaskManager.instance.deleteTask(task);
                            taskAdapter.notifyDataSetChanged();
                            if (task.getId() == null){
                            Toast.makeText(getContext(),"Can't delete the task because id is null",Toast.LENGTH_SHORT).show();}
                        }
                    })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position){}
                    });
        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}

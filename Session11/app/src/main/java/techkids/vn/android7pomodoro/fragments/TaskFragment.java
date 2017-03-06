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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.activities.TaskActivity;
import techkids.vn.android7pomodoro.adapters.TaskAdapter;
import techkids.vn.android7pomodoro.adapters.viewholders.TaskViewHolder;
import techkids.vn.android7pomodoro.databases.DbContext;
import techkids.vn.android7pomodoro.databases.TaskManager;
import techkids.vn.android7pomodoro.databases.TaskManagerType;
import techkids.vn.android7pomodoro.databases.models.Task;
import techkids.vn.android7pomodoro.events.TimerCommand;
import techkids.vn.android7pomodoro.events.TimerCommandEvent;
import techkids.vn.android7pomodoro.networks.services.PomodoroService;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    @BindView(R.id.rv_task)
    RecyclerView rvTask;

    private TaskAdapter taskAdapter;

    private static String TAG = "TaskFragment";

    public TaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_task, container, false);
        addListener();
        setupUI(view);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
                    ((TaskActivity)getActivity()).replaceFragment(new TaskDetailFragment(),true);
                    TaskViewHolder.isClick = false;
                }
            }
        });

        taskAdapter.setImageBottonClickListener(new TaskAdapter.TaskImageBottonClickListener() {
            @Override
            public void onClick() {
                TimerCommandEvent timerCommandEvent = new TimerCommandEvent(TimerCommand.START_TIMER);
                EventBus.getDefault().post(timerCommandEvent);
                if(getActivity() instanceof TaskActivity){
                    ((TaskActivity)getActivity()).getSupportActionBar().setTitle("Timer");
                }
                ((TaskActivity) getActivity()).onChangeFragment(new PomodoroFragment(),true);
                PomodoroService pomodoroService = new PomodoroService();
                pomodoroService.onStartTimer();
            }
        });

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle(R.string.tasks);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rvTask.addItemDecoration(dividerItemDecoration);

        setHasOptionsMenu(true);
    }


    @OnClick(R.id.fab)
    void onFabClick() {
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        taskDetailFragment.setTitle("Add new task");

        //TODO: Make TaskActivity and Fragment independent
        //((TaskActivity)getActivity()).replaceFragment(taskDetailFragment,true);
        EventBus.getDefault().post(new FragmentChange(taskDetailFragment,true));
    }


    private void showAlertDelete(final Task task) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this.getContext());
        builder.setCancelable(true)
                .setTitle("Delete")
                .setMessage("Delete this task")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position){
                            EventBus.getDefault().post( TaskManagerType.DELETETASK);
                            TaskManager.instance.deleteTask(task);
                            DbContext.instance.deleteTask(task);
                            taskAdapter.notifyDataSetChanged();
                        }
                    })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position){}
                    });
        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Subscribe
    /*public void onChangeData(DataChange dataChange){
        Log.e(TAG, "onDatachange: datachange" );
        Toast.makeText(getContext(), dataChange.getToastNotification(), Toast.LENGTH_SHORT).show();
    }*/

    public void onTimerCommand(TimerCommandEvent timerCommandEvent){
        Toast.makeText(getContext(), "Make a new Timer", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onFragmentChange(FragmentChange fragmentChange){
        ((TaskActivity) getContext()).replaceFragment(fragmentChange.getFragment(),true);
    }

    @Subscribe
    public void onDelete(TaskManagerType taskManagerType){
        if (taskManagerType.equals(TaskManagerType.DELETETASK))
        Toast.makeText(getContext(),"Delete Success",Toast.LENGTH_SHORT).show();
    }
}

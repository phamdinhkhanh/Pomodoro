package techkids.vn.android7pomodoro.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.activities.TaskActivity;
import techkids.vn.android7pomodoro.databases.models.Task;

/**
 * Created by laptopTCC on 2/18/2017.
 */

public class PomodoroFragment extends Fragment {
    @BindView(R.id.pg)
    ProgressBar progressBar;

    @BindView(R.id.ib_play_2)
    Button button;

    @BindView(R.id.tv_pr)
    TextView textView;

    private Task task;

    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public PomodoroFragment(){
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_task_detail, container, false);
        setupUI(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_task, menu);
    }


    public void setupUI(View view){
        if(getActivity() instanceof TaskActivity){
                ((TaskActivity) getActivity()).getSupportActionBar().setTitle(title);
        }
    }
}

package techkids.vn.android7pomodoro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.databases.DbContext;
import techkids.vn.android7pomodoro.databases.TaskManager;
import techkids.vn.android7pomodoro.networks.TaskFragmentListener;

/**
 * Created by laptopTCC on 3/1/2017.
 */

public class LoadingFragment extends Fragment {
    private static String TAG = LoadingFragment.class.toString();

    private TaskFragmentListener taskFragmentListener;

    public void onAttach(Context context){
        super.onAttach(context);
        taskFragmentListener = (TaskFragmentListener) context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        loadData();
        return view;
    }

    public void loadData() {
        TaskManager.instance.getTaskFromServer();
        TaskManager.instance.setGetTasksListener(new TaskManager.GetTasksListener() {
            @Override
            public void onGetTasks(boolean ok) {
                if(ok){
                    taskFragmentListener.onChangeFragment(new TaskFragment(),false);
                    Log.d(TAG,String.format("GetTasks true"));
                } else {
                    DbContext.instance.allTask();
                    taskFragmentListener.onChangeFragment(new TaskFragment(),false);
                    Log.d(TAG,String.format("Get size: %d",DbContext.instance.allTask().size()));
                    Log.d(TAG,String.format("Get task false"));
                }
            }
        });
    }
}

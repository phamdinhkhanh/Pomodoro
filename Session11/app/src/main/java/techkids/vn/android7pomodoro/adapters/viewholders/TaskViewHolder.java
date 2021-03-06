package techkids.vn.android7pomodoro.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.databases.DbContext;
import techkids.vn.android7pomodoro.databases.models.Task;
import techkids.vn.android7pomodoro.utils.Utils;

import static techkids.vn.android7pomodoro.adapters.TaskAdapter.TAG;

/**
 * Created by huynq on 2/8/17.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ib_task_color)
    ImageButton ibTaskColor;

    @BindView(R.id.iv_ticker)
    View ivTicker;

    @BindView(R.id.tv_task_name)
    TextView tvTaskName;

    @BindView(R.id.ib_play)
    ImageButton ibPlay;

    public ImageButton getIbPlay() {
        return ibPlay;
    }

    public static boolean isClick = false;


    public TaskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Task task) {
        //1: Bind Color
//        vTaskColor.setBackgroundColor(Color.parseColor(task.getColor()));
        Utils.setSolidColor(ibTaskColor, task.getColor());

        //2: Bind Task name
        tvTaskName.setText(task.getName());

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EventBus.getDefault().post(task);
                isClick = true;
                Log.d(TAG,String.format("isTicked %s",isClick));
                /*PomodoroFragment poromodoFragment = new PomodoroFragment();
                taskFragment.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_main, poromodoFragment)
                        .addToBackStack(null)
                        .commit();*/
            }
        });

        ibTaskColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(task.isDone()){
                    DbContext.instance.setDone(task,false);
                    ivTicker.setVisibility(View.INVISIBLE);
                } else {
                    DbContext.instance.setDone(task,true);
                    ivTicker.setVisibility(View.VISIBLE);
                }
            }
        });

        if (task.isDone()) {
            ivTicker.setVisibility(View.VISIBLE);
            //Log.d(TAG,"TRUE");
        } else {
            ivTicker.setVisibility(View.INVISIBLE);
            //Log.d(TAG,"FAIL");
        }
    }




    public class DeleteTask{
        private Task task;

        public DeleteTask(Task task) {
            this.task = task;
        }

        public Task getTask() {
            return task;
        }
    }
}

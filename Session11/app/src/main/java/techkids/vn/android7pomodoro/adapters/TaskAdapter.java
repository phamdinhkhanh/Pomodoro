package techkids.vn.android7pomodoro.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.adapters.viewholders.TaskViewHolder;
import techkids.vn.android7pomodoro.databases.DbContext;
import techkids.vn.android7pomodoro.databases.models.Task;

/**
 * Created by huynq on 2/8/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    public static final String TAG = TaskAdapter.class.toString();

    public static TaskAdapter taskAdapter = new TaskAdapter();

    public int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isLongClick = false;


    public interface TaskItemClickListener {
        void onItemClick(Task task);
    }

    public interface TaskImageBottonClickListener {
        void onClick();
    }


    public interface TaskItemLongClick {
        void onLongClick(Task task);
    }

    private TaskItemLongClick taskItemLongClick;

    public void setTaskItemLongClick(TaskItemLongClick taskItemLongClick) {
        this.taskItemLongClick = taskItemLongClick;
    }

    private TaskImageBottonClickListener imageBottonClickListener;

    public void setImageBottonClickListener(TaskImageBottonClickListener imageBottonClickListener) {
        this.imageBottonClickListener = imageBottonClickListener;
    }

    private TaskItemClickListener taskItemClickListener;

    public void setTaskItemClickListener(TaskItemClickListener taskItemClickListener) {
        this.taskItemClickListener = taskItemClickListener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //1: Create View
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_task, parent, false);

        //2: Create ViewHolder
        TaskViewHolder taskViewHolder = new TaskViewHolder(itemView);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, final int position) {
        //1: Get data based on position
        final Task task = DbContext.instance.allTask().get(position);

        this.position = position;
        //2: Bind data into view
        holder.bind(task);


        //khi click vao itemview se truyen task sang
        if(!isLongClick){
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send event to outside
                if (taskItemClickListener != null) {
                    taskItemClickListener.onItemClick(task);
                }
            }
        });
        isLongClick = false;
        }

        holder.getIbPlay().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBottonClickListener != null){
                    imageBottonClickListener.onClick();
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(taskItemLongClick != null){
                    taskItemLongClick.onLongClick(task);
                    Log.d(TAG,String.format("BodyLongClick: ",task.toString()));
                }
                isLongClick = true;
                return isLongClick;
            }
        });
    }

    @Override
    public int getItemCount() {
        return DbContext.instance.allTask().size();
    }
}

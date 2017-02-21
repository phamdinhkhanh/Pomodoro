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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.activities.TaskActivity;
import techkids.vn.android7pomodoro.adapters.TaskColorAdapter;
import techkids.vn.android7pomodoro.databases.DbContext;
import techkids.vn.android7pomodoro.databases.models.Task;
import techkids.vn.android7pomodoro.decorations.TaskColorDecor;
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
        View view =  inflater.inflate(R.layout.fragment_task_detail, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);

        taskColorAdapter = new TaskColorAdapter();

        rvTaskColor.setLayoutManager(new GridLayoutManager(this.getContext(), 4));
        rvTaskColor.setAdapter(taskColorAdapter);
        rvTaskColor.addItemDecoration(new TaskColorDecor());

        if(getActivity() instanceof TaskActivity) {
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



//            // 2: Create new TASK
//            Task newTask = new Task(taskName, color, paymentPerHour);
//
//            // 3: Add to database
//            DbContext.instance.addTask(newTask);
            Task newTask = new Task(taskName,color,paymentPerHour);

            DbContext.instance.addTask(newTask);

            Log.d(TAG,String.format("Vector size %d",DbContext.instance.allTasks().size()));

            getActivity().onBackPressed();

            netContext.addNewTask();
        }
        return false;
    }

    /*public interface UserServices{
        @Headers("Cache-Control: max-age=640000")
        @GET("task")
        Call<List<Task>> getTask();
    }

    public interface PostServices{
        @POST("task")
        Call<Task> createTasks(@Body Task task);
    }*/

    public void postTask(){
        /*//1. Create httpClient
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("Authorization","JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE0ODc2MDgzNjgsImlkZW50aXR5IjoiNThhYjFhMmM3OTA0ZjUwMDBiOWZkMjhhIiwiZXhwIjoxNDg3Njk0NzY4LCJuYmYiOjE0ODc2MDgzNjh9.yrMkVm095I4ImO9kQCt7GUrdDpuliKUbnhlfhZgdvd8")
                        .method(original.method(),original.body())
                        .build();
            return chain.proceed(request);
            };
        });

        OkHttpClient client = httpClient.build();*/
        //1. Create retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://a-task.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2. Create Services
        /*PostServices postServices = retrofit.create(PostServices.class);

        //Task task = new Task("khanh","#FFFFFF",1.4f,false);
        Call<Task> call = postServices.createTasks(task);
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.body() != null) {
                    Log.d(TAG, String.format("Response addTask %", response.body()));
                } else {
                    Log.d(TAG, String.format("ResponseFailure addTask"));
                }
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                Log.d(TAG,"Failure addTask");
            }
        });*/


    }
}

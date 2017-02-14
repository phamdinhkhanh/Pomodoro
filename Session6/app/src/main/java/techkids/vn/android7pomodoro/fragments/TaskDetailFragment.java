package techkids.vn.android7pomodoro.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.adapters.RoundAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {
    @BindView(R.id.rcv_round_table)
    RecyclerView rcv_round_table;

    public RoundAdapter roundAdapter;

    public TaskDetailFragment() {
        // Required empty public constructor
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
        roundAdapter = new RoundAdapter();
        ButterKnife.bind(this,view);
        rcv_round_table.setAdapter(roundAdapter);
        rcv_round_table.setLayoutManager(new GridLayoutManager(getActivity(),4));

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_task, menu);
    }
}

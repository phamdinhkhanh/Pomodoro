package techkids.vn.android7pomodoro.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.adapters.viewholders.RoundViewHolder;
import techkids.vn.android7pomodoro.databases.DbRound_Color;
import techkids.vn.android7pomodoro.databases.models.Round_Color;

/**
 * Created by laptopTCC on 2/13/2017.
 */

public class RoundAdapter extends RecyclerView.Adapter<RoundViewHolder> {
    @Override
    public RoundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.round_tick,parent,false);
        RoundViewHolder roundViewHolder = new RoundViewHolder(view);
        return roundViewHolder;
    }

    @Override
    public void onBindViewHolder(RoundViewHolder holder, int position) {
        Round_Color round_color = DbRound_Color.instance.allColor().get(position);
        holder.bind(round_color);
    }

    @Override
    public int getItemCount() {
        return DbRound_Color.instance.allColor().size();
    }
}

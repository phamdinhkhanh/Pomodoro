package techkids.vn.android7pomodoro.adapters.viewholders;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.android7pomodoro.R;
import techkids.vn.android7pomodoro.databases.models.Round_Color;

/**
 * Created by laptopTCC on 2/13/2017.
 */

public class RoundViewHolder extends RecyclerView.ViewHolder{
    public static String TAG = RoundViewHolder.class.toString();
    @BindView(R.id.v_round)
    View v_round;
    @BindView(R.id.iv_check)
    ImageView iv_check;
    private boolean click = true;
    public RoundViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        v_round.bringToFront();
        iv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click){
                    //iv_check.setColorFilter(Color.parseColor(DbRound_Color.instance.allColor().get(getAdapterPosition()).getColor()));
                    iv_check.bringToFront();
                } else {
                    v_round.bringToFront();
                    //iv_check.setColorFilter(Color.WHITE);
                }
                click = !click;
                Log.d(TAG,String.format("%s","Click iv_check"));
            }
        });
    }
    public void bind(Round_Color round_color){
        GradientDrawable drawable = (GradientDrawable) v_round.getBackground();
        drawable.setColor(Color.parseColor(round_color.getColor()));
    }
}

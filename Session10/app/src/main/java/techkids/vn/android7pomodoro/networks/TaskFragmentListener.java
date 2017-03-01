package techkids.vn.android7pomodoro.networks;

import android.support.v4.app.Fragment;

/**
 * Created by laptopTCC on 3/1/2017.
 */

public interface TaskFragmentListener {
    void onChangeFragment(Fragment fragment, boolean addToBackStack);
}

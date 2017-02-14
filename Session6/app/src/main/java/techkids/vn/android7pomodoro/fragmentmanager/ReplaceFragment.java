package techkids.vn.android7pomodoro.fragmentmanager;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by laptopTCC on 2/14/2017.
 */

public class ReplaceFragment {
    private FragmentManager fragmentManager;
    private @IdRes
    int fg_id;

    public ReplaceFragment(FragmentManager fragmentManager, @IdRes int fg_id) {
        this.fragmentManager = fragmentManager;
        this.fg_id = fg_id;
    }

    public void replanceFragment(Fragment fragment, @IdRes int fg_id, boolean addToBackStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fg_id,fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.commit();
        }
    }
}

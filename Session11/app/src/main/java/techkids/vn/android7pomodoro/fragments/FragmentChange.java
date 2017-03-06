package techkids.vn.android7pomodoro.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by laptopTCC on 3/5/2017.
 */

public class FragmentChange {
    private Fragment fragment;
    private boolean addtoBackStack;

    public Fragment getFragment() {
        return fragment;
    }

    public boolean isAddtoBackStack() {
        return addtoBackStack;
    }

    public FragmentChange(Fragment fragment, boolean addtoBackStack) {
        this.fragment = fragment;
        this.addtoBackStack = addtoBackStack;
    }
}

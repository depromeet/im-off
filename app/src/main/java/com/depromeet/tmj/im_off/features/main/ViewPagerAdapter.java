package com.depromeet.tmj.im_off.features.main;


import com.depromeet.tmj.im_off.features.statistics.StatisticsFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private boolean isLeaving = false;

    public ViewPagerAdapter(FragmentManager fm, boolean isLeaving) {
        super(fm);
        this.isLeaving = isLeaving;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TimerFragment.newInstance(isLeaving);
            case 1:
                return StatisticsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}

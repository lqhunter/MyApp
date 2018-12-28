package com.lq.himalaya.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lq.himalaya.utils.FragmentCreater;

/**
 * Created by lqhunter on 2018/12/26.
 */

public class MainContentAdapter extends FragmentPagerAdapter{
    public MainContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentCreater.getFragment(position);
    }

    @Override
    public int getCount() {
        return FragmentCreater.PAGE_COUNT;
    }
}

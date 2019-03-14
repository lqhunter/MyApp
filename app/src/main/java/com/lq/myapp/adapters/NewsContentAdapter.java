package com.lq.myapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.lq.myapp.utils.NewsFragmentCreator;
import com.lq.myapp.utils.PicFragmentCreator;

public class NewsContentAdapter extends FragmentPagerAdapter {

    public NewsContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragmentCreator.getFragment(position);
    }

    @Override
    public int getCount() {
        return NewsFragmentCreator.getPageCount();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //如果注释这行，那么不管怎么切换，page都不会被销毁
        //super.destroyItem(container, position, object);
    }
}

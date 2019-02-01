package com.lq.myapp.fragments;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.myapp.R;
import com.lq.myapp.base.BaseFragment;

public class PicFragment extends BaseFragment {

    private static final String TAG = "PicFragment";
    private View mRootView;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main_picture, container, false);


        return mRootView;
    }

}

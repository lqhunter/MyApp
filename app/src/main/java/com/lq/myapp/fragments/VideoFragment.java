package com.lq.myapp.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.myapp.R;
import com.lq.myapp.base.BaseFragment;

public class VideoFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main_video, container, false);

        return rootView;
    }
}

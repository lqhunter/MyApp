package com.lq.myapp.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.myapp.R;
import com.lq.myapp.base.BaseFragment;

/**
 * Created by lqhunter on 2018/12/26.
 */

public class RadioHistoryFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = layoutInflater.inflate(R.layout.frament_radio_history, container, false);
        return rootView;
    }
}

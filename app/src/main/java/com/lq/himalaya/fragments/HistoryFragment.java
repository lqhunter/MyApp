package com.lq.himalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.himalaya.R;
import com.lq.himalaya.base.BaseFragment;

/**
 * Created by lqhunter on 2018/12/26.
 */

public class HistoryFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = layoutInflater.inflate(R.layout.frament_history, container, false);
        return rootView;
    }
}

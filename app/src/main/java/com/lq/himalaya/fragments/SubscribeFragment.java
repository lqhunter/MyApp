package com.lq.himalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.himalaya.R;
import com.lq.himalaya.base.BaseFragment;

/**
 *
 * Created by lqhunter on 2018/12/26.
 */

public class SubscribeFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = layoutInflater.inflate(R.layout.fragment_subscribe, container, false);
        return rootView;
    }
}

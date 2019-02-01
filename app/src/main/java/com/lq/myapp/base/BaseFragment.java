package com.lq.myapp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * Created by lqhunter on 2018/12/26.
 */

public abstract class BaseFragment extends Fragment {

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = onSubViewLoaded(inflater, container);
        return mRootView;
    }

    // activity创建结束,在该方法内可以进行与Activity交互的UI操作
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化数据, 子类可以不实现
     */
    public void initData() {

    }


    protected abstract View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container);
}

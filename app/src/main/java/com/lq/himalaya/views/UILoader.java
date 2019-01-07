package com.lq.himalaya.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lq.himalaya.R;
import com.lq.himalaya.base.BaseApplication;

/**
 * UI 加载器
 * Created by lqhunter on 2019/1/6.
 */

public abstract class UILoader extends FrameLayout {

    private View loadingView;
    private View successView;
    private View errorView;
    private View emptyView;

    public enum UIStatus {
        LOADING, SUCCESS, NETWORK_ERROR, EMPTY, NONE
    }

    public UIStatus mCurrentStatus = UIStatus.NONE;

    public UILoader(@NonNull Context context) {
        this(context, null);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public void updateUIStatus(UIStatus uiStatus){
        mCurrentStatus = uiStatus;
        //更新ui需要在主线程
        BaseApplication.getsHandler().post(new Runnable() {
            @Override
            public void run() {
                switchUiByCurrentStatus();
            }
        });
    }

    private void init() {
        switchUiByCurrentStatus();
    }

    private void switchUiByCurrentStatus() {
        //加载中
        if (loadingView == null) {
            loadingView = getLoadingView();
            addView(loadingView);
        }
        //根据状态设置是否可见
        loadingView.setVisibility(mCurrentStatus == UIStatus.LOADING ? VISIBLE : GONE);

        //成功
        if (successView == null) {
            successView = getSuccessView(this);
            addView(successView);
        }
        //根据状态设置是否可见
        successView.setVisibility(mCurrentStatus == UIStatus.SUCCESS ? VISIBLE : GONE);

        //错误界面
        if (errorView == null) {
            errorView = getNetWorkErrorView();
            addView(errorView);
        }
        //根据状态设置是否可见
        errorView.setVisibility(mCurrentStatus == UIStatus.NETWORK_ERROR ? VISIBLE : GONE);

        //数据为空
        if (emptyView == null) {
            emptyView = getEmptyView();
            addView(emptyView);
        }
        //根据状态设置是否可见
        emptyView.setVisibility(mCurrentStatus == UIStatus.EMPTY ? VISIBLE : GONE);
    }

    private View getEmptyView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this, false);
        return view;
    }

    private View getNetWorkErrorView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_error_view, this, false);
        return view;
    }


    private View getLoadingView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_view, this, false);
        return view;
    }
    /**
     * 成功界面不确定, 由外部实现, 定义为抽象方法
     * @return
     */
    protected abstract View getSuccessView(ViewGroup container);
}

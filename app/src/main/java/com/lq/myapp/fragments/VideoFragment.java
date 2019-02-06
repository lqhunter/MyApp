package com.lq.myapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jimi_wu.ptlrecyclerview.LayoutManager.PTLGridLayoutManager;
import com.jimi_wu.ptlrecyclerview.PullToLoad.OnLoadListener;
import com.jimi_wu.ptlrecyclerview.PullToLoad.PullToLoadRecyclerView;
import com.lq.myapp.R;
import com.lq.myapp.VideoPlayActivity;
import com.lq.myapp.adapters.RecyclerAdapter;
import com.lq.myapp.base.BaseApplication;
import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.bean.VideoBean;
import com.lq.myapp.interfaces.IVideoViewCallBack;
import com.lq.myapp.presenters.SciencePresenter;
import com.lq.myapp.views.UILoader;

import java.util.List;

public class VideoFragment extends BaseFragment implements IVideoViewCallBack
        , RecyclerAdapter.OnRecommendItemClickListener, UILoader.OnRetryClickListener {

    private RecyclerAdapter mRecyclerAdapter;
    private PullToLoadRecyclerView mRecyclerView;
    private View mView;
    private String TAG = "VideoFragment";
    private SciencePresenter mSciencePresenter;
    //private android.support.v7.widget.Toolbar mToolbar;
    private UILoader mUILoader;

    @Override
    public void initData() {
        //SciencePresenter 获取数据
        mSciencePresenter = SciencePresenter.getInstance();
        mSciencePresenter.setVideoViewCallBack(this);
        mSciencePresenter.loadData();
    }

    @Override
    protected View onSubViewLoaded(final LayoutInflater layoutInflater, ViewGroup container) {

        mUILoader = new UILoader(getContext()) {
            @Override
            protected View getSuccessView(ViewGroup container) {
                Log.d(TAG, "container ----> " + container);
                return initView(layoutInflater, container);
            }
        };

        //不能重复绑定,这个特定的child如果已经有一个parent了，你必须在这个parent中首先调用removeView()方法，
        // 才能继续你的内容。这里很明显这个child是一个View，一个子（child）View必须依赖于父（parent）View，
        // 如果你要使用这个child，则必须通过parent，而你如果就是硬想使用这个child，
        // 那么就得让这个child与parent脱离父子关系（即removeView（））
        if (mUILoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) mUILoader.getParent()).removeView(mUILoader);
        }

        return mUILoader;
    }

    private View initView(LayoutInflater layoutInflater,ViewGroup container) {
        mView = layoutInflater.inflate(R.layout.fragment_science, container, false);

        mRecyclerView = mView.findViewById(R.id.recyclerview_science);
        mRecyclerView.setRefreshEnable(false);  //关闭上拉刷新
        //2.设置布局管理器
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        PTLGridLayoutManager gridLayoutManager = new PTLGridLayoutManager(2, PTLGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        //设置适配器
        mRecyclerAdapter = new RecyclerAdapter();
        mRecyclerAdapter.setOnRecommendItemClickListener(this);
        Log.d(TAG, "mRecyclerAdapter -->" + mRecyclerAdapter);

        mRecyclerView.setAdapter(mRecyclerAdapter);
        //上拉加载监听
        mRecyclerView.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onStartLoading(int skip) {
                Log.d(TAG, "setOnLoadListener");
                if (mSciencePresenter != null) {
                    //加载更多
                    mSciencePresenter.loadMore();
                }
            }
        });

        return mView;

    }


    @Override
    public void onSuccess(final List<VideoBean> data) {
        Log.d(TAG, "onSuccess -->" + data.size());

        mRecyclerAdapter.setData(data);
        mUILoader.updateUIStatus(UILoader.UIStatus.SUCCESS);
        //此处为网络请求的线程中, ui更新需要主线程
        BaseApplication.getsHandler().post(new Runnable() {
            @Override
            public void run() {
                //必须使用这一句，mRecyclerAdapter.notifyDataSetChanged()没有用
                mRecyclerView.completeLoad(data.size());
                //mRecyclerAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onLoading() {

        mUILoader.updateUIStatus(UILoader.UIStatus.LOADING);

    }

    @Override
    public void noMore() {
        Toast.makeText(getActivity(), "没有更多内容", Toast.LENGTH_SHORT).show();
        BaseApplication.getsHandler().post(new Runnable() {
            @Override
            public void run() {
                //必须使用这一句，mRecyclerAdapter.notifyDataSetChanged()没有用
                mRecyclerView.completeLoad(0);
                //mRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onNetworkError() {
        mUILoader.updateUIStatus(UILoader.UIStatus.NETWORK_ERROR);
        mUILoader.setOnRetryClickListener(this);

    }

    @Override
    public void onEmpty() {
        mUILoader.updateUIStatus(UILoader.UIStatus.EMPTY);
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), VideoPlayActivity.class);
        Bundle bundle = new Bundle();// 创建Bundle对象
        bundle.putInt("position", position);//  放入data值为int型
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRetryClick() {
        if (mSciencePresenter != null) {
            mSciencePresenter.loadData();
        }
    }
}

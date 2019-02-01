package com.lq.myapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lq.myapp.R;
import com.lq.myapp.VideoPlayActivity;
import com.lq.myapp.adapters.RecyclerAdapter;
import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.bean.VideoBean;
import com.lq.myapp.presenters.SciencePresenter;
import com.lq.myapp.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends BaseFragment implements SciencePresenter.OnGetURLDataSuccessCallBack, RecyclerAdapter.OnRecommendItemClickListener {

    List<VideoBean> data = new ArrayList<>();

    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView mRecyclerView;
    private View mView;

    private String TAG = "VideoFragment";


    @Override
    public void initData() {
        //SciencePresenter 获取数据
        SciencePresenter sciencePresenter = new SciencePresenter();
        sciencePresenter.setOnGetURLDataSuccessCallBack(this);
        sciencePresenter.getData();

    }

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        //view 加载
        mView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_science, container, false);

        //找到控件
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        //2.设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        //设置适配器
        mRecyclerAdapter = new RecyclerAdapter();
        mRecyclerAdapter.setOnRecommendItemClickListener(this);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        //item间隔



        return mView;
    }

    @Override
    public void onSuccess(List<VideoBean> data) {
        this.data.addAll(data);
        mRecyclerAdapter.setData(data);
        //此处为网络请求的线程中, ui更新需要主线程
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(int position, String detailURL) {
        LogUtil.d(TAG, detailURL);
        Intent intent = new Intent(getContext(), VideoPlayActivity.class);
        Bundle bundle = new Bundle();// 创建Bundle对象
        bundle.putString("detailURL", detailURL);//  放入data值为int型
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

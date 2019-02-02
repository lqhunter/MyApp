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
import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.bean.VideoBean;
import com.lq.myapp.presenters.SciencePresenter;
import com.lq.myapp.utils.LogUtil;

import java.util.List;

public class VideoFragment extends BaseFragment implements SciencePresenter.OnGetURLDataSuccessCallBack
        , RecyclerAdapter.OnRecommendItemClickListener {

    private RecyclerAdapter mRecyclerAdapter;
    private PullToLoadRecyclerView mRecyclerView;
    private View mView;
    private String TAG = "VideoFragment";
    private SciencePresenter mSciencePresenter;


    @Override
    public void initData() {
        //SciencePresenter 获取数据
        mSciencePresenter = new SciencePresenter();
        mSciencePresenter.setOnGetURLDataSuccessCallBack(this);

    }


    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        //view 加载
        mView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_science, container, false);


        initView();


        return mView;
    }

    private void initView() {
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

    }

    @Override
    public void onSuccess(final List<VideoBean> data) {
        Log.d(TAG, "onSuccess -->" + data.size());

        mRecyclerAdapter.setData(data);
        //此处为网络请求的线程中, ui更新需要主线程
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //必须使用这一句，mRecyclerAdapter.notifyDataSetChanged()没有用
                mRecyclerView.completeLoad(data.size());
                //mRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void noMore() {
        Toast.makeText(getActivity(), "没有更多内容", Toast.LENGTH_SHORT).show();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //必须使用这一句，mRecyclerAdapter.notifyDataSetChanged()没有用
                mRecyclerView.completeLoad(0);
                //mRecyclerAdapter.notifyDataSetChanged();
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

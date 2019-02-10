package com.lq.myapp.fragments;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimi_wu.ptlrecyclerview.AutoLoad.AutoLoadRecyclerView;
import com.jimi_wu.ptlrecyclerview.LayoutManager.PTLGridLayoutManager;
import com.jimi_wu.ptlrecyclerview.PullToLoad.OnLoadListener;
import com.jimi_wu.ptlrecyclerview.PullToRefresh.OnRefreshListener;
import com.lq.myapp.R;
import com.lq.myapp.adapters.PicListAdapter;
import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.bean.PicBean;
import com.lq.myapp.interfaces.IPicViewCallBack;
import com.lq.myapp.presenters.PicPresenter;
import com.lq.myapp.utils.LogUtil;

import java.util.List;

public class PicFragment extends BaseFragment implements IPicViewCallBack {

    private static final String TAG = "PicFragment";
    private View mRootView;
    private PicListAdapter mPicListAdapter;
    private PicPresenter mPicPresenter;
    private AutoLoadRecyclerView mPicRlv;
    private int page = 1;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main_picture, container, false);

        initView();

        return mRootView;
    }

    private void initView() {
        mPicRlv = mRootView.findViewById(R.id.pic_rlv);
        //2.设置布局管理器
        PTLGridLayoutManager gridLayoutManager = new PTLGridLayoutManager(2, PTLGridLayoutManager.VERTICAL);
        mPicRlv.setLayoutManager(gridLayoutManager);
        //3.设置适配器
        mPicListAdapter = new PicListAdapter();
        mPicRlv.setAdapter(mPicListAdapter);
        mPicRlv.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onStartLoading(int skip) {
                LogUtil.d(TAG, "onStartLoading...加载第: " + page + "页");
                mPicPresenter.loadMore(page++);
            }
        });

        mPicRlv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onStartRefreshing() {
                mPicPresenter.pull2Refresh(page++);
            }
        });

    }

    @Override
    public void initData() {
        mPicPresenter = new PicPresenter();
        mPicPresenter.registerCallBack(this);
        mPicPresenter.loadMore(page++);
    }

    @Override
    public void onPicListLoad(List<PicBean> result) {
        mPicListAdapter.setData(result);
        mPicRlv.completeLoad(result.size());
    }

    @Override
    public void onRefreshListLoad(List<PicBean> result) {
        mPicListAdapter.setRefreshData(result);
        mPicRlv.completeRefresh();

    }

    @Override
    public void onNetworkError() {

    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void onLoading() {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPicPresenter != null) {
            mPicPresenter.unRegisterCallBack();
        }
    }
}

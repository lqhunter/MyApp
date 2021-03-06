package com.lq.myapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimi_wu.ptlrecyclerview.AutoLoad.AutoLoadRecyclerView;
import com.jimi_wu.ptlrecyclerview.LayoutManager.PTLGridLayoutManager;
import com.jimi_wu.ptlrecyclerview.PullToLoad.OnLoadListener;
import com.lq.myapp.activities.PhotoViewActivity;
import com.lq.myapp.R;
import com.lq.myapp.adapters.PicWallpaperListAdapter;
import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.bean.PicWallpaperBean;
import com.lq.myapp.interfaces.IPicWallpaperViewCallBack;
import com.lq.myapp.presenters.PicWallpaperPresenter;
import com.lq.myapp.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


public class PicAnimationFragment extends BaseFragment implements IPicWallpaperViewCallBack, PicWallpaperListAdapter.OnItemClickListener {

    public static final String TAG = "PicAnimationFragment";
    private View mRootView;
    private AutoLoadRecyclerView mPicRlv;
    private PicWallpaperListAdapter mListAdapter;
    public PicWallpaperPresenter mPicWallpaperPresenter;


    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main_picture, container, false);
        initView();
        return mRootView;
    }

    private void initView() {
        mPicRlv = mRootView.findViewById(R.id.pic_rlv);
        //2.设置布局管理器
        PTLGridLayoutManager gridLayoutManager = new PTLGridLayoutManager(3, PTLGridLayoutManager.VERTICAL);
        mPicRlv.setLayoutManager(gridLayoutManager);
        mListAdapter = new PicWallpaperListAdapter();
        mListAdapter.setOnItemClickListener(this);
        mPicRlv.setAdapter(mListAdapter);
        mPicRlv.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onStartLoading(int skip) {
                LogUtil.d(TAG, "skip --> " + skip);
                if (mPicWallpaperPresenter != null) {
                    mPicWallpaperPresenter.loadMore(skip);
                }
            }
        });
    }

    @Override
    public void initData() {
        mPicWallpaperPresenter = new PicWallpaperPresenter();
        mPicWallpaperPresenter.registerCallBack(this);
        mPicWallpaperPresenter.loadMore(0);
    }

    @Override
    public void onPicListLoad(PicWallpaperBean result) {
        if (result != null && null != mListAdapter && null != mPicRlv) {
            mListAdapter.setData(result.getRes().getVertical());
            mPicRlv.completeLoad(result.getRes().getVertical().size());
        }
    }

    @Override
    public void onRefreshListLoad(PicWallpaperBean result) {

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
        if (mPicWallpaperPresenter != null) {
            mPicWallpaperPresenter.unRegisterCallBack();
        }
    }

    /**
     * 图片点击后，打开图片查看器
     *
     * @param position
     * @param data
     */
    @Override
    public void onItemClick(int position, List<PicWallpaperBean.ResBean.VerticalBean> data) {
        Intent intent = new Intent(getContext(), PhotoViewActivity.class);
        ArrayList<String> urls = new ArrayList<>();
        for (PicWallpaperBean.ResBean.VerticalBean bean :
                data) {
            urls.add(bean.getImg());
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("urls", urls);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

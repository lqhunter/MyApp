package com.lq.myapp.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.myapp.RadioDetailActivity;
import com.lq.myapp.R;
import com.lq.myapp.adapters.RecommendListAdapter;
import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.interfaces.IRecommendPresenter;
import com.lq.myapp.interfaces.IRecommendViewCallBack;
import com.lq.myapp.presenters.RadioDetailPresenter;
import com.lq.myapp.presenters.RecommendPresenter;
import com.lq.myapp.utils.LogUtil;
import com.lq.myapp.views.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/**
 *
 * Created by lqhunter on 2018/12/26.
 */

public class RecommendFragment extends BaseFragment implements IRecommendViewCallBack, UILoader.OnRetryClickListener, RecommendListAdapter.OnRecommendItemClickListener {

    private static final String TAG = "RecommendFragment";
    private View mRootView;
    private RecyclerView mRecommendRv;
    private RecommendListAdapter recommendListAdapter;
    private IRecommendPresenter mRecommendPresenter;
    private UILoader mUILoader;

    @Override
    protected View onSubViewLoaded(final LayoutInflater layoutInflater, ViewGroup container) {

        mUILoader = new UILoader(getContext()) {
            @Override
            protected View getSuccessView(ViewGroup container) {
                return createSuccessView(layoutInflater, container);
            }
        };




        //获取逻辑层对象
        mRecommendPresenter = RecommendPresenter.getInstance();
        //设置通知接口的注册
        mRecommendPresenter.registerViewCall(this);
        mRecommendPresenter.getRecommendData();

        //解绑???, 没懂！！！
        //不能重复绑定
        if (mUILoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) mUILoader.getParent()).removeView(mUILoader);
        }

        //返回 view, 给界面显示
        return mUILoader;
    }

    private View createSuccessView(LayoutInflater layoutInflater, ViewGroup container) {
        //view 加载
        mRootView = layoutInflater.inflate(R.layout.fragment_radio_recommend, container, false);

        //1.找到控件
        mRecommendRv = mRootView.findViewById(R.id.recommend_list);
        //2.设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecommendRv.setLayoutManager(linearLayoutManager);
        //3.设置适配器
        recommendListAdapter = new RecommendListAdapter();
        mRecommendRv.setAdapter(recommendListAdapter);
        //item间隔
        mRecommendRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 5);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 5);
                outRect.left = UIUtil.dip2px(view.getContext(), 5);
                outRect.right = UIUtil.dip2px(view.getContext(), 5);
            }
        });

        recommendListAdapter.setOnRecommendItemClickListener(this);
        return mRootView;
    }


    @Override
    public void onRecommendListLoad(List<Album> result) {
        LogUtil.d(TAG, Thread.currentThread().getName());
        //数据回来后更新ui
        recommendListAdapter.setData(result);
        mUILoader.updateUIStatus(UILoader.UIStatus.SUCCESS);
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
    public void onLoading() {
        mUILoader.updateUIStatus(UILoader.UIStatus.LOADING);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRecommendPresenter != null) {
            //取消注册
            mRecommendPresenter.unRegisterCallBack(this);
        }
    }

    @Override
    public void onRetryClick() {
        //网络不佳，点击重试
        if (mRecommendPresenter != null) {
            mRecommendPresenter.getRecommendData();
        }
    }

    @Override
    public void onItemClick(int position, Album album) {
        //根据位置拿到数据
        RadioDetailPresenter.getDetailPresenter().setTargetAlbum(album);
        //跳转界面
        Intent intent = new Intent(getContext(), RadioDetailActivity.class);
        startActivity(intent);
    }
}

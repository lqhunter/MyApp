package com.lq.myapp.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jimi_wu.ptlrecyclerview.AutoLoad.AutoLoadRecyclerView;
import com.jimi_wu.ptlrecyclerview.LayoutManager.PTLGridLayoutManager;
import com.jimi_wu.ptlrecyclerview.PullToRefresh.OnRefreshListener;
import com.lq.myapp.R;
import com.lq.myapp.adapters.RadioSubscribeListAdapter;
import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.interfaces.ISubscribeViewCallBack;
import com.lq.myapp.presenters.SubscribePresenter;
import com.lq.myapp.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

/**
 *
 * Created by lqhunter on 2018/12/26.
 */

public class RadioSubscribeFragment extends BaseFragment implements ISubscribeViewCallBack {


    private static final String TAG = "RadioSubscribeFragment";
    private View mRootView;
    private AutoLoadRecyclerView mRlv;
    private RadioSubscribeListAdapter mListAdapter;
    private SubscribePresenter mSubscribePresenter;
    private ImageView mNotLogin;
    private TextView mNotLoginText;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = layoutInflater.inflate(R.layout.fragment_radio_subscribe, container, false);

        initView();
        return mRootView;
    }

    @Override
    public void initData() {
        mSubscribePresenter = new SubscribePresenter();
        mSubscribePresenter.registerViewCall(this);
        mSubscribePresenter.getSubscribeData();

    }

    private void initView() {
        mNotLogin = mRootView.findViewById(R.id.not_login_image);
        mNotLoginText = mRootView.findViewById(R.id.not_login_text);
        mNotLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSubscribePresenter != null) {
                    LogUtil.d(TAG, "点击。。");
                    mSubscribePresenter.getSubscribeData();
                }
            }
        });
        mRlv = mRootView.findViewById(R.id.subscribe_list_view);
        PTLGridLayoutManager gridLayoutManager = new PTLGridLayoutManager(1, PTLGridLayoutManager.VERTICAL);
        mRlv.setLayoutManager(gridLayoutManager);

        mListAdapter = new RadioSubscribeListAdapter();
        mRlv.setAdapter(mListAdapter);
        mRlv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onStartRefreshing() {
                if (mSubscribePresenter != null) {
                    mSubscribePresenter.getSubscribeData();
                    mListAdapter.clearData();
                    mRlv.completeRefresh();
                }
            }
        });
    }

    @Override
    public void onLoginFail() {
    }

    @Override
    public void onSubscribeListLoad(List<Album> result) {
        mNotLogin.setVisibility(View.GONE);
        mNotLoginText.setVisibility(View.GONE);
        if (result != null && !result.isEmpty()) {
            LogUtil.d(TAG, "数据大小。。。" + result.size());
            mListAdapter.setData(result);
            mRlv.completeRefresh();
            //mRlv.completeLoad(result.size());
            mRlv.setNoMore(true);
        }
    }

    @Override
    public void onNetworkError() {
        Toast.makeText(getContext(), "未登录", Toast.LENGTH_SHORT).show();
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
        if (mSubscribePresenter != null) {
            mSubscribePresenter.unRegisterCallBack();
        }
    }
}

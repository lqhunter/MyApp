package com.lq.myapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jimi_wu.ptlrecyclerview.AutoLoad.AutoLoadRecyclerView;
import com.jimi_wu.ptlrecyclerview.LayoutManager.PTLGridLayoutManager;
import com.jimi_wu.ptlrecyclerview.LayoutManager.PTLLinearLayoutManager;
import com.jimi_wu.ptlrecyclerview.PullToLoad.OnLoadListener;
import com.lq.myapp.adapters.RadioDetailListViewAdapter;
import com.lq.myapp.base.BaseActivity;
import com.lq.myapp.interfaces.IDetailViewCallback;
import com.lq.myapp.presenters.RadioDetailPresenter;
import com.lq.myapp.utils.BlurTransformation;
import com.lq.myapp.utils.LogUtil;
import com.lq.myapp.views.RoundRectImageView;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public class RadioDetailActivity extends BaseActivity implements IDetailViewCallback {

    private static final String TAG = "RadioDetailActivity";
    private ImageView mLargeCover;
    private RoundRectImageView mSmallCover;
    private TextView mAlbumTitle;
    private TextView mAlbumAuthor;
    private RadioDetailPresenter mDetailPresenter;
    private AutoLoadRecyclerView mRlv;
    private RadioDetailListViewAdapter mListViewAdapter;
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datail);

        //状态栏透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initView();
        initData();
    }

    private void initData() {
        mDetailPresenter = RadioDetailPresenter.getDetailPresenter();
        mDetailPresenter.registerViewCallback(this);
    }

    private void initView() {
        mBack = findViewById(R.id.back);
        mLargeCover = findViewById(R.id.iv_large_cover);
        mSmallCover = findViewById(R.id.iv_small_cover);
        mAlbumTitle = findViewById(R.id.iv_title);
        mAlbumAuthor = findViewById(R.id.iv_author);
        mRlv = findViewById(R.id.detail_list_view);
        mRlv.setRefreshEnable(false);
        mRlv.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onStartLoading(int skip) {
                LogUtil.d(TAG, "skip -->" + skip);

                if (mDetailPresenter != null) {
                    if (mDetailPresenter.getAlbumCount() == skip) {
                        mRlv.setNoMore(true);
                    } else
                        mDetailPresenter.getAlbumDetail(skip);
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioDetailActivity.this.finish();
            }
        });
        //初始化recyclerView, linearLayoutManager上拉加载更多会提前加载，不是到顶部加载，用grid
        //PTLLinearLayoutManager linearLayoutManager = new PTLLinearLayoutManager(LinearLayoutManager.VERTICAL);
        //linearLayoutManager.setSpanCount(1);
        PTLGridLayoutManager gridLayoutManager = new PTLGridLayoutManager(1, PTLGridLayoutManager.VERTICAL);
        mRlv.setLayoutManager(gridLayoutManager);
        mListViewAdapter = new RadioDetailListViewAdapter();
        mRlv.setAdapter(mListViewAdapter);



    }

    @Override
    public void onDetailListLoad(List<Track> tracks) {
        if (mRlv != null && mListViewAdapter != null && tracks != null) {
            mListViewAdapter.setData(tracks);
            mRlv.completeLoad(tracks.size());
        }
    }

    @Override
    public void onAlbumLoad(Album album) {
        mAlbumTitle.setText(album.getAlbumTitle());
        mAlbumAuthor.setText(album.getAnnouncer().getNickname());

        Picasso.with(this).load(album.getCoverUrlSmall()).into(mSmallCover);
        Picasso.with(this).load(album.getCoverUrlLarge()).transform(new BlurTransformation(this)).into(mLargeCover);

        if (mDetailPresenter != null) {
            //第一次获取数据
            mDetailPresenter.getAlbumDetail(0);
        }


    }
}

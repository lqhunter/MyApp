package com.lq.myapp.activities;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.jimi_wu.ptlrecyclerview.AutoLoad.AutoLoadRecyclerView;
import com.jimi_wu.ptlrecyclerview.LayoutManager.PTLGridLayoutManager;
import com.jimi_wu.ptlrecyclerview.PullToLoad.OnLoadListener;
import com.lq.myapp.R;
import com.lq.myapp.adapters.RadioDetailListViewAdapter;
import com.lq.myapp.base.BaseActivity;
import com.lq.myapp.interfaces.IDetailViewCallback;
import com.lq.myapp.presenters.RadioDetailPresenter;
import com.lq.myapp.utils.BlurTransformation;
import com.lq.myapp.utils.CurrentPlayerManager;
import com.lq.myapp.utils.LogUtil;
import com.lq.myapp.views.RoundRectImageView;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.appnotification.XmNotificationCreater;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.List;

public class RadioDetailActivity extends BaseActivity implements IDetailViewCallback, IXmPlayerStatusListener {

    private static final String TAG = "RadioDetailActivity";
    private ImageView mLargeCover;
    private RoundRectImageView mSmallCover;
    private TextView mAlbumTitle;
    private TextView mAlbumAuthor;
    private RadioDetailPresenter mDetailPresenter;
    private AutoLoadRecyclerView mRlv;
    private RadioDetailListViewAdapter mListViewAdapter;
    private ImageView mBack;
    private ImageView mPlayOrPause;
    private TextView mTvPlayStatus;
    private XmPlayerManager mXmPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datail);

        //状态栏透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initPlayer();
        initView();
        initData();
    }

    private void initPlayer() {
        Notification mNotification = XmNotificationCreater.getInstanse(this).initNotification(this.getApplicationContext(), RadioPlayActivity.class);
        mXmPlayerManager = XmPlayerManager.getInstance(this);
        mXmPlayerManager.init((int) System.currentTimeMillis(), mNotification);
        mXmPlayerManager.setBreakpointResume(false);
        mXmPlayerManager.addPlayerStatusListener(this);

    }

    private void initData() {
        mDetailPresenter = RadioDetailPresenter.getDetailPresenter();
        mDetailPresenter.registerViewCallback(this);
        mDetailPresenter.clearTracks();
        mDetailPresenter.getAlbumDetail(0);
    }

    private void initView() {
        mBack = findViewById(R.id.back);
        mLargeCover = findViewById(R.id.iv_large_cover);
        mSmallCover = findViewById(R.id.iv_small_cover);
        mAlbumTitle = findViewById(R.id.iv_title);
        mAlbumAuthor = findViewById(R.id.iv_author);

        mTvPlayStatus = findViewById(R.id.tv_play_status);
        mPlayOrPause = findViewById(R.id.iv_play_status);
        mPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mXmPlayerManager != null) {
                    //播放按钮显示功能切换
                    if (mTvPlayStatus.getText().equals("全部播放")) {//如果为默认的"全部播放",则表明当前播放的album与打开的album不同
                        //如果第一次进入详情界面或者进入不是正在播放的音频的详情界面，点击播放直接播放全部
                        mPlayOrPause.setImageResource(R.mipmap.pausecircleo);
                        mTvPlayStatus.setText("停止播放");

                        mXmPlayerManager.resetPlayList();
                        mXmPlayerManager.playList(mDetailPresenter.getTracks(), 0);
                        //每次播放新的album集要更新CurrentPlayerManager的当前album
                        CurrentPlayerManager.getInstance().setAlbum(mDetailPresenter.getAlbum());
                    } else if (mXmPlayerManager.isPlaying()) {
                        mXmPlayerManager.pause();
                        mPlayOrPause.setImageResource(R.mipmap.playcircleo);
                        mTvPlayStatus.setText("继续播放");
                    } else {
                        mXmPlayerManager.play();
                        mPlayOrPause.setImageResource(R.mipmap.pausecircleo);
                        mTvPlayStatus.setText("停止播放");
                    }
                }
            }
        });

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
        mListViewAdapter.setOnItemClickListener(new RadioDetailListViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(RadioDetailActivity.this, RadioPlayActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
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
    public void onAlbumLoad(Album album) {//从推荐界面传来的album，注册此界面后回调到这里，初始化不需要访问网络的数据
        mAlbumTitle.setText(album.getAlbumTitle());
        mAlbumAuthor.setText(album.getAnnouncer().getNickname());

        Picasso.with(this).load(album.getCoverUrlSmall()).into(mSmallCover);
        Picasso.with(this).load(album.getCoverUrlLarge()).transform(new BlurTransformation(this)).into(mLargeCover);

        //判断详情界面的album与当前播放的albums是否相同，来确认播放按钮的状态
        if (CurrentPlayerManager.getInstance().getAlbum() != null) {//表面当前播放列表有数据
            if (album.getId() == CurrentPlayerManager.getInstance().getAlbum().getId()) {//如果相同，就不需要“全部播放”, 如果不同，就默认“全部播放”
                //使正在播放的item显示播放动画
                mListViewAdapter.setSelectedIndex(mXmPlayerManager.getCurrentIndex());
                mRlv.completeRefresh();//这个refresh会刷新所有可见的item
                if (mXmPlayerManager.isPlaying()) {
                    mPlayOrPause.setImageResource(R.mipmap.pausecircleo);
                    mTvPlayStatus.setText("停止播放");
                } else {
                    mPlayOrPause.setImageResource(R.mipmap.playcircleo);
                    mTvPlayStatus.setText("继续播放");
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mXmPlayerManager != null) {
            mXmPlayerManager.removePlayerStatusListener(this);
        }
    }

    @Override
    public void onPlayStart() {
        Track track = mXmPlayerManager.getTrack(mXmPlayerManager.getCurrentIndex());
        LogUtil.d(TAG, "正在播放id-->" + track.getAlbum().getAlbumId());
        LogUtil.d(TAG, "界面album id-->" + mDetailPresenter.getAlbum().getId());
        if (track.getAlbum().getAlbumId() == mDetailPresenter.getAlbum().getId()) {
            mPlayOrPause.setImageResource(R.mipmap.pausecircleo);
            mTvPlayStatus.setText("停止播放");
        }

    }

    @Override
    public void onPlayPause() {
        mPlayOrPause.setImageResource(R.mipmap.playcircleo);
        mTvPlayStatus.setText("继续播放");
    }

    @Override
    public void onPlayStop() {

    }

    @Override
    public void onSoundPlayComplete() {

    }

    @Override
    public void onSoundPrepared() {

    }

    @Override
    public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {
        mListViewAdapter.setSelectedIndex(mXmPlayerManager.getCurrentIndex());
        mRlv.completeRefresh();//这个refresh会刷新所有可见的item
    }

    @Override
    public void onBufferingStart() {

    }

    @Override
    public void onBufferingStop() {

    }

    @Override
    public void onBufferProgress(int i) {

    }

    @Override
    public void onPlayProgress(int i, int i1) {

    }

    @Override
    public boolean onError(XmPlayerException e) {
        return false;
    }

}

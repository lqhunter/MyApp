package com.lq.myapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lq.myapp.adapters.VideoDetailListAdapter;
import com.lq.myapp.base.BaseApplication;
import com.lq.myapp.bean.VideoBean;
import com.lq.myapp.presenters.VideoDetailPresenter;
import com.lq.myapp.utils.LogUtil;
import com.squareup.picasso.Picasso;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoPlayActivity extends AppCompatActivity implements VideoDetailPresenter.OnGetVideoURLCallBack {

    private static final String TAG = "VideoPlayActivity";
    private VideoBean mVideo;

    private VideoDetailListAdapter mListAdapter = null;
    private ListView mListView = null;
    private VideoDetailPresenter mDetailPresenter;
    private JZVideoPlayerStandard mJzvdStd;
    private ImageView mVideoPlayerCover;
    private TextView mVideoName;
    private TextView mVideoCount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);


        Intent intent = getIntent();
        mVideo = (VideoBean) intent.getSerializableExtra("video_bean");
        //mVideo = VideoPresenter.getInstance().getData(intent.getExtras().getInt("position"));
        //状态栏透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initView();

        mDetailPresenter = new VideoDetailPresenter(mVideo.getDetailURL(), this);
        mDetailPresenter.setOnGetVideoURLCallBack(this);

    }

    private void initView() {
        mJzvdStd = findViewById(R.id.video_player);
        //mJzvdStd.thumbImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.cover));
        mJzvdStd.setVisibility(View.GONE);//初始化时不让其显示

        //播放器封面
        mVideoPlayerCover = findViewById(R.id.video_play_cover);
        Picasso.with(this).load(mVideo.getCoverURL()).into(mVideoPlayerCover);

        //设置标题
        mVideoName = findViewById(R.id.video_name);
        mVideoName.setText(mVideo.getTitle());
        mVideoCount = findViewById(R.id.video_count);


        mListView = findViewById(R.id.count);
        mListAdapter = new VideoDetailListAdapter(this);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.d(TAG, "点击了====" + position);
                mDetailPresenter.getVideoURL(position + 1);
            }
        });
    }


    @Override
    public void onGetVideoURL(final String videoURL, final int num) {
        if (!videoURL.contains("m3u8")) {
            //主线路爬取失败，用备线
            mDetailPresenter.getVideoURL(100 + num);
        } else {
            BaseApplication.getsHandler().post(new Runnable() {
                @Override
                public void run() {
                    mJzvdStd.setUp(videoURL, JZVideoPlayer.SCREEN_WINDOW_NORMAL, "第" + num + "集");
                    mVideoPlayerCover.setVisibility(View.GONE);
                    mJzvdStd.setVisibility(View.VISIBLE);
                    mJzvdStd.startVideo();
                }
            });
        }

    }

    @Override
    public void onGetCount(int count) {
        mListAdapter.setCount(count);
        //设置集数
        mVideoCount.setText("共" + count + "集");
        BaseApplication.getsHandler().post(new Runnable() {
            @Override
            public void run() {
                mListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mJzvdStd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mJzvdStd.releaseAllVideos();
    }
}

package com.lq.myapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lq.myapp.adapters.VideoDetailListAdapter;
import com.lq.myapp.presenters.VideoDetailPresenter;
import com.lq.myapp.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoPlayActivity extends AppCompatActivity implements VideoDetailPresenter.OnGetVideoURLCallBack {

    private static final String TAG = "VideoPlayActivity";
    private String detailURL;
    private List<String> videoURL = new ArrayList<>();
    //集数
    private int count;
    private VideoDetailListAdapter mListAdapter = null;
    private ListView mListView = null;
    private VideoDetailPresenter mDetailPresenter;
    private JZVideoPlayerStandard mJzvdStd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);


        Intent intent = getIntent();
        detailURL = intent.getExtras().getString("detailURL");

        initView();

        mDetailPresenter = new VideoDetailPresenter(detailURL, this);
        mDetailPresenter.setOnGetVideoURLCallBack(this);



       // mJzvdStd.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");


    }

    private void initView() {



        mJzvdStd = findViewById(R.id.video_player);
        mJzvdStd.thumbImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.cover));

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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mJzvdStd.setUp(videoURL, JZVideoPlayer.SCREEN_WINDOW_NORMAL, "第" + num + "集");
                    mJzvdStd.startVideo();

                }
            });
        }

    }

    @Override
    public void onGetCount(int count) {
        mListAdapter.setCount(count);
        runOnUiThread(new Runnable() {
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

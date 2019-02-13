package com.lq.myapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lq.myapp.presenters.RadioDetailPresenter;
import com.lq.myapp.utils.BlurTransformation;
import com.lq.myapp.utils.LogUtil;
import com.lq.myapp.utils.MergeTransformation;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.List;

public class RadioPlayActivity extends AppCompatActivity implements IXmPlayerStatusListener {

    private static final String TAG = "RadioPlayActivity";
    private XmPlayerManager mXmPlayerManager;
    private ImageView mBack;
    private static Track mCurrentTrack;
    private TextView mTitle;
    private Button mPlayOrPause;
    private Button mPrior;
    private Button mNext;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_play);

        //状态栏透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        LogUtil.d(TAG, "position-->" + position);
        List<Track> mTracks = RadioDetailPresenter.getDetailPresenter().getTracks();
        mXmPlayerManager = XmPlayerManager.getInstance(this);
        mXmPlayerManager.playList(mTracks, position);
        LogUtil.d(TAG, "play_position-->" + mXmPlayerManager.getCurrentIndex());
        mCurrentTrack = mXmPlayerManager.getTrack(mXmPlayerManager.getCurrentIndex());
        mXmPlayerManager.addPlayerStatusListener(this);
    }

    private void initView() {
        mTitle = findViewById(R.id.radio_title);
        mTitle.setText(mCurrentTrack.getTrackTitle());
        mTitle.setSelected(true);//跑马灯效果必须加

        mBack = findViewById(R.id.back_radio_play);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioPlayActivity.this.finish();
            }
        });

        ImageView bg = findViewById(R.id.bg_guss);
        Picasso.with(this).load(mCurrentTrack.getCoverUrlLarge())
                .transform(new BlurTransformation(this))
                .into(bg);

        ImageView cover = findViewById(R.id.cover);
        Picasso.with(this).load(mCurrentTrack.getCoverUrlLarge())
                .placeholder(R.mipmap.play_cover_default)
                .priority(Picasso.Priority.HIGH)
                .transform(new MergeTransformation(BitmapFactory.decodeResource(getResources(), R.mipmap.cover_edge)))
                .into(cover);


        mPlayOrPause = findViewById(R.id.play_pause);
        mPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mXmPlayerManager.isPlaying()) {//正在播放，切换成暂停
                    mPlayOrPause.setBackground(getResources().getDrawable(R.drawable.selector_radio_play_pause));
                    mXmPlayerManager.pause();
                } else {
                    //播放开始，切换播放按键
                    mPlayOrPause.setBackground(getResources().getDrawable(R.drawable.selector_radio_play_play));
                    mXmPlayerManager.play();
                }
            }
        });

        mPrior = findViewById(R.id.prior);
        mPrior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mXmPlayerManager.hasPreSound()) {
                    mXmPlayerManager.playPre();
                    mXmPlayerManager.play();
                }
            }
        });

        mNext = findViewById(R.id.next);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mXmPlayerManager.hasNextSound()) {
                    mXmPlayerManager.playNext();
                    mXmPlayerManager.play();
                }
            }
        });

        mSeekBar = findViewById(R.id.seek_bar);


    }

    @Override
    public void onPlayStart() {
        if (mPlayOrPause != null) {
            mPlayOrPause.setBackground(getResources().getDrawable(R.drawable.selector_radio_play_pause));
        }
    }

    @Override
    public void onPlayPause() {
        if (mPlayOrPause != null) {

            mPlayOrPause.setBackground(getResources().getDrawable(R.drawable.selector_radio_play_play));
        }
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
        LogUtil.d(TAG, "切换-->");
        if (playableModel != null) {
            mCurrentTrack = mXmPlayerManager.getTrack(mXmPlayerManager.getCurrentIndex());
            if (mTitle != null) {
                mTitle.setText(mCurrentTrack.getTrackTitle());
            }
            LogUtil.d(TAG, "上一首-->" + playableModel.getKind());
        }
        if (playableModel1 != null) {
            if (mTitle != null) {
                mCurrentTrack = mXmPlayerManager.getTrack(mXmPlayerManager.getCurrentIndex());
                mTitle.setText(mCurrentTrack.getTrackTitle());
            }
            LogUtil.d(TAG, "下一首-->" + playableModel1.getKind());
        }


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
        LogUtil.d(TAG, "当前进度-->" + i);
        LogUtil.d(TAG, "当前进度-->" + i1);
        float progress = (i * 0.1f / i1) * 10000;
        LogUtil.d(TAG, "比例-->" + progress);
        if (mSeekBar != null) {
            mSeekBar.setProgress((int) progress);
        }


    }

    @Override
    public boolean onError(XmPlayerException e) {
        return false;
    }
}

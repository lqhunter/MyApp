package com.lq.myapp.activities;

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
import android.widget.Toast;

import com.lq.myapp.R;
import com.lq.myapp.presenters.RadioDetailPresenter;
import com.lq.myapp.utils.BlurTransformation;
import com.lq.myapp.utils.CurrentPlayerManager;
import com.lq.myapp.utils.LogUtil;
import com.lq.myapp.utils.MergeTransformation;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;
import com.ximalaya.ting.android.sdkdownloader.XmDownloadManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

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
    private Button mPlayOrder;
    private TextView mCurrentStatus;
    private TextView mTotal;

    private boolean isTouchSeekBar = false;
    private int touchProgress;
    private CurrentPlayerManager mCurrentPlayerManager;
    private Button mDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_play);

        //状态栏透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initPlayer();
        initData();
        initView();
    }

    private void initPlayer() {
        mXmPlayerManager = XmPlayerManager.getInstance(this);
        mXmPlayerManager.addPlayerStatusListener(this);

        mCurrentPlayerManager = CurrentPlayerManager.getInstance();
        mXmPlayerManager.setPlayMode(mCurrentPlayerManager.getPlayMode());
    }

    private void initData() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        LogUtil.d(TAG, "position-->" + position);
        if (-1 != position) {
            List<Track> mTracks = RadioDetailPresenter.getDetailPresenter().getTracks();
            mXmPlayerManager.playList(mTracks, position);
            mCurrentPlayerManager.setAlbum(RadioDetailPresenter.getDetailPresenter().getAlbum());

        }
        mCurrentTrack = mXmPlayerManager.getTrack(mXmPlayerManager.getCurrentIndex());
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
                }
            }
        });

        mNext = findViewById(R.id.next);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mXmPlayerManager.hasNextSound()) {
                    mXmPlayerManager.playNext();
                }
            }
        });

        mSeekBar = findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (isTouchSeekBar) {
                    touchProgress = progress;
                    LogUtil.d(TAG, "触摸时进度条-->" + progress);
                    mXmPlayerManager.seekToByPercent((float) (touchProgress / 1000.0));

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTouchSeekBar = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LogUtil.d(TAG, "触摸结束百分百-->" + (float) (touchProgress / 10.0));
                isTouchSeekBar = false;
            }
        });

        mPlayOrder = findViewById(R.id.play_mode);
        setPlayModeView(mCurrentPlayerManager.getPlayMode(), false);
        mPlayOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mXmPlayerManager != null) {
                    XmPlayListControl.PlayMode playMode = mCurrentPlayerManager.getNextPlayMode();
                    mXmPlayerManager.setPlayMode(playMode);
                    setPlayModeView(playMode, true);
                }

            }
        });

        mCurrentStatus = findViewById(R.id.current_status);
        mTotal = findViewById(R.id.total);

        mDownload = findViewById(R.id.download);
        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void setPlayModeView(XmPlayListControl.PlayMode playMode, boolean isShowToast) {
        if (playMode == XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP) {
            mPlayOrder.setBackground(getResources().getDrawable(R.drawable.selector_radio_play_order_default));
            if (isShowToast) {
                Toast.makeText(RadioPlayActivity.this, "列表循环", Toast.LENGTH_SHORT).show();
            }
        } else if (playMode == XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP) {
            mPlayOrder.setBackground(getResources().getDrawable(R.drawable.selector_radio_play_order_single_loop));
            if (isShowToast) {
                Toast.makeText(RadioPlayActivity.this, "单曲循环", Toast.LENGTH_SHORT).show();
            }
        } else if (playMode == XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM) {
            mPlayOrder.setBackground(getResources().getDrawable(R.drawable.selector_radio_play_order_random));
            if (isShowToast) {
                Toast.makeText(RadioPlayActivity.this, "随机播放", Toast.LENGTH_SHORT).show();
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
        if (mPlayOrPause != null) {
            mPlayOrPause.setBackground(getResources().getDrawable(R.drawable.selector_radio_play_pause));
        }

        if (mTotal != null) {
            mTotal.setText(transData(mXmPlayerManager.getDuration()));
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
        if (playableModel != null) {
            mCurrentTrack = mXmPlayerManager.getTrack(mXmPlayerManager.getCurrentIndex());
            if (mTitle != null) {
                mTitle.setText(mCurrentTrack.getTrackTitle());
            }
        }
        if (playableModel1 != null) {
            if (mTitle != null) {
                mCurrentTrack = mXmPlayerManager.getTrack(mXmPlayerManager.getCurrentIndex());
                mTitle.setText(mCurrentTrack.getTrackTitle());
            }
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
        //LogUtil.d(TAG, "当前进度-->" + i);
        //LogUtil.d(TAG, "当前进度-->" + i1);
        float progress = (i * 0.1f / i1) * 10000;
        //LogUtil.d(TAG, "比例-->" + progress);
        if (mSeekBar != null && !isTouchSeekBar) {
            mSeekBar.setProgress((int) progress);
            mCurrentStatus.setText(transData(mXmPlayerManager.getPlayCurrPositon()));
            mTotal.setText(transData(mXmPlayerManager.getDuration()));
        }


    }

    @Override
    public boolean onError(XmPlayerException e) {
        return false;
    }

    public String transData(long ms) {
        SimpleDateFormat formatter;
        if (ms < 3600000) {
            formatter = new SimpleDateFormat("mm:ss");//初始化Formatter的转换格式。
        } else {
            formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式
        }
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return formatter.format(ms);
    }
}

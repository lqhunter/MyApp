package com.lq.myapp.views;

import android.content.Context;
import android.util.AttributeSet;

import com.lq.myapp.R;

import cn.jzvd.JZVideoPlayerStandard;

public class VideoPlayer extends JZVideoPlayerStandard {
    public VideoPlayer(Context context) {
        this(context, null);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_player;
    }
}

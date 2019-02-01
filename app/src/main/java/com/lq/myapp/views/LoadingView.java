package com.lq.myapp.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lq.myapp.R;

@SuppressLint("AppCompatCustomView")
public class LoadingView extends ImageView {
    //旋转角度
    private float rotate_degree = 0;
    private boolean mNeedRotate = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //设置图标
        setImageResource(R.mipmap.loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mNeedRotate = true;
        //绑定windows的时候
        post(new Runnable() {
            @Override
            public void run() {
                rotate_degree += 30;
                rotate_degree = rotate_degree <= 360 ? rotate_degree : 0;
                invalidate();
                if (mNeedRotate) {
                    postDelayed(this, 100);
                }
            }
        });

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //解绑
        mNeedRotate = false;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(rotate_degree, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);
    }
}

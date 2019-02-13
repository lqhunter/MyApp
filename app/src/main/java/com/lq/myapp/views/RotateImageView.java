package com.lq.myapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class RotateImageView extends android.support.v7.widget.AppCompatImageView {

    //旋转角度
    private float rotate_degree = 0;
    private boolean mNeedRotate = false;

    public RotateImageView(Context context) {
        this(context, null);
    }

    public RotateImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mNeedRotate = true;
        //绑定windows的时候
        post(new Runnable() {
            @Override
            public void run() {
                rotate_degree += 0.5;
                rotate_degree = rotate_degree <= 360 ? rotate_degree : 0;
                invalidate();
                if (mNeedRotate) {
                    postDelayed(this, 60);
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

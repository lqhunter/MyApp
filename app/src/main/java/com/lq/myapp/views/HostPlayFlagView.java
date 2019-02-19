package com.lq.myapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class HostPlayFlagView extends View {
    public HostPlayFlagView(Context context) {
        this(context, null);
    }

    public HostPlayFlagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HostPlayFlagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}

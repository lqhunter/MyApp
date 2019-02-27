package com.lq.myapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hjm.bottomtabbar.BottomTabBar;
import com.lq.myapp.R;
import com.lq.myapp.fragments.PicFragment;
import com.lq.myapp.fragments.RadioFragment;
import com.lq.myapp.fragments.UserFragment;
import com.lq.myapp.fragments.VideoFragment;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BottomTabBar mBottomTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //状态栏透明
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        //getWindow().setStatusBarColor(Color.TRANSPARENT);

        initView();
    }

    private void initView() {

        mBottomTabBar = findViewById(R.id.bottom_tab_bar);
        mBottomTabBar.init(getSupportFragmentManager())
                .setImgSize(50, 50)//设置ICON图片的尺寸
                .setFontSize(12)//设置文字的尺寸
                .setTabPadding(10, 6, 10)//设置ICON图片与上部分割线的间隔、图片与文字的间隔、文字与底部的间隔
                .addTabItem("图片", R.mipmap.picture, PicFragment.class)//设置文字、一张图片、fragment
                .addTabItem("电台", R.mipmap.radio, RadioFragment.class)
                .addTabItem("美剧", R.mipmap.movie, VideoFragment.class)
                .addTabItem("我的", R.mipmap.main_user, UserFragment.class)
                .isShowDivider(false)//设置是否显示分割线
                .setCurrentTab(0);   //设置当前选中的Tab，从0开始
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XmPlayerManager.release();
    }


}

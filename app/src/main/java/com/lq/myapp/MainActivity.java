package com.lq.myapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hjm.bottomtabbar.BottomTabBar;
import com.lq.myapp.fragments.PicFragment;
import com.lq.myapp.fragments.RadioFragment;
import com.lq.myapp.fragments.VideoFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BottomTabBar mBottomTabBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        mBottomTabBar = findViewById(R.id.bottom_tab_bar);

        mBottomTabBar.init(getSupportFragmentManager())
                .setImgSize(50,50)//设置ICON图片的尺寸
                .setFontSize(12)//设置文字的尺寸
                .setTabPadding(4,6,10)//设置ICON图片与上部分割线的间隔、图片与文字的间隔、文字与底部的间隔
                .addTabItem("图片", R.drawable.picture, PicFragment.class)//设置文字、一张图片、fragment
                .addTabItem("电台", R.drawable.radio,RadioFragment.class)//设置文字、两张图片、fragment
                .addTabItem("美剧", R.drawable.movie, VideoFragment.class)
                .isShowDivider(false)//设置是否显示分割线
                .setTabBarBackgroundColor(Color.WHITE)//设置底部导航栏颜色
                //.setTabBarBackgroundResource(R.mipmap.ic_launcher)//设置底部导航栏的背景图片【与设置底部导航栏颜色方法不能同时使用，否则会覆盖掉前边设置的颜色】
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name, View view) {
                        Log.d(TAG, "position" + position);
                    }
                })
                .setCurrentTab(0);//设置当前选中的Tab，从0开始

    }
}

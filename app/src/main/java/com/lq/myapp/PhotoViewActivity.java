package com.lq.myapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lq.myapp.adapters.WallpaperViewPagerAdapter;
import com.lq.myapp.views.PhotoViewPager;

import java.util.ArrayList;
import java.util.List;

public class PhotoViewActivity extends AppCompatActivity {

    private WallpaperViewPagerAdapter mAdapter;
    private PhotoViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        List<String> urls = extras.getStringArrayList("urls");
        if (urls != null) {
            mAdapter.setData(urls);
            mAdapter.notifyDataSetChanged();
        }
        mViewPager.setCurrentItem(extras.getInt("position"));
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewpager);
        mAdapter = new WallpaperViewPagerAdapter(this);
        mViewPager.setAdapter(mAdapter);
    }
}

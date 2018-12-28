package com.lq.himalaya;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.lq.himalaya.adapters.IndicaterAdapter;
import com.lq.himalaya.adapters.MainContentAdapter;
import com.lq.himalaya.utils.LogUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    MagicIndicator magicIndicator;
    ViewPager mViewPager;
    IndicaterAdapter indicaterAdapter;
    MainContentAdapter mainContentAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();

    }

    private void initEvent() {
        indicaterAdapter.setIndicaterListener(new IndicaterAdapter.OnIndicaterTapClickListener() {
            @Override
            public void onTabClick(int index) {
                LogUtil.d(TAG, "click index is --->" + index);
                mViewPager.setCurrentItem(index);
            }
        });
    }

    private void initView() {

        mViewPager = findViewById(R.id.content_pager);
        //创建内容适配器
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        mainContentAdapter = new MainContentAdapter(supportFragmentManager);
        mViewPager.setAdapter(mainContentAdapter);

        magicIndicator = findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(getResources().getColor(R.color.main_color));
        //创建 indicater 的适配器
        indicaterAdapter = new IndicaterAdapter(this);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(indicaterAdapter);
        commonNavigator.setAdjustMode(true);


        magicIndicator.setNavigator(commonNavigator);

        //把 indicater 和 ViewPager 绑定
        ViewPagerHelper.bind(magicIndicator, mViewPager);



    }
}

package com.lq.myapp.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.myapp.R;
import com.lq.myapp.adapters.IndicatorAdapter;
import com.lq.myapp.adapters.MainContentAdapter;
import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.utils.LogUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class RadioFragment extends BaseFragment {

    private static final String TAG = "RadioFragment";
    MagicIndicator magicIndicator;
    ViewPager mViewPager;
    IndicatorAdapter mIndicatorAdapter;
    MainContentAdapter mainContentAdapter;
    private View mRootView;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main_radio, container, false);

        initView();
        initEvent();

        return mRootView;
    }

    private void initEvent() {
        mIndicatorAdapter.setIndicaterListener(new IndicatorAdapter.OnIndicaterTapClickListener() {
            @Override
            public void onTabClick(int index) {
                LogUtil.d(TAG, "click index is --->" + index);
                mViewPager.setCurrentItem(index);
            }
        });
    }

    private void initView() {
        mViewPager = mRootView.findViewById(R.id.content_pager);
        //创建内容适配器
        FragmentManager supportFragmentManager = getChildFragmentManager();
        mainContentAdapter = new MainContentAdapter(supportFragmentManager);
        mViewPager.setAdapter(mainContentAdapter);

        magicIndicator = mRootView.findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(getResources().getColor(R.color.colorWhite));

        //创建 indicater 的适配器
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        mIndicatorAdapter = new IndicatorAdapter(getContext(), R.array.radio_indicator_name);
        commonNavigator.setAdapter(mIndicatorAdapter);
        commonNavigator.setAdjustMode(true);


        magicIndicator.setNavigator(commonNavigator);

        //把 indicater 和 ViewPager 绑定
        ViewPagerHelper.bind(magicIndicator, mViewPager);

    }
}

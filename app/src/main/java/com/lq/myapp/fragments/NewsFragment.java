package com.lq.myapp.fragments;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.myapp.R;
import com.lq.myapp.adapters.IndicatorAdapter;
import com.lq.myapp.adapters.NewsContentAdapter;
import com.lq.myapp.adapters.PicContentAdapter;
import com.lq.myapp.base.BaseApplication;
import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.utils.LogUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class NewsFragment extends BaseFragment {

    private static final String TAG = "NewsFragment";
    private View mRootView;
    private ViewPager mViewPager;
    private IndicatorAdapter mIndicatorAdapter;
    private Toolbar mToolbar;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = layoutInflater.inflate(R.layout.fragment_news_main, container, false);

        initView();
        initEvent();
        return mRootView;
    }

    private void initEvent() {
        mIndicatorAdapter.setIndicaterListener(new IndicatorAdapter.OnIndicaterTapClickListener() {
            @Override
            public void onTabClick(int index) {
                LogUtil.d(TAG, "click index is --->" + index);
                // indicator 点击，viewPager滑动
                mViewPager.setCurrentItem(index);
            }
        });
    }

    private void initView() {
        mToolbar = mRootView.findViewById(R.id.pic_tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(false);

        //1.初始化 viewPager
        mViewPager = mRootView.findViewById(R.id.pic_content_pager);
        //创建内容适配器
        NewsContentAdapter picContentAdapter = new NewsContentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(picContentAdapter);

        //2.初始化 MagicIndicator
        MagicIndicator magicIndicator = mRootView.findViewById(R.id.pic_magic_indicator);
        magicIndicator.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        //创建 CommonNavigator 和 IndicatorAdapter
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        mIndicatorAdapter = new IndicatorAdapter(getContext(), R.array.news_indicator);
        commonNavigator.setAdapter(mIndicatorAdapter);
        commonNavigator.setAdjustMode(true);
        magicIndicator.setNavigator(commonNavigator);
        //把 ViewPager 和 indicator  绑定, viewPager滑动, indicator也跟着滑动
        ViewPagerHelper.bind(magicIndicator, mViewPager);


    }

}

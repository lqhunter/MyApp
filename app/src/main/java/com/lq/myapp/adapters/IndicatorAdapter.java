package com.lq.myapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.lq.myapp.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 *
 * Created by lqhunter on 2018/12/26.
 */
public class IndicatorAdapter extends CommonNavigatorAdapter {
    String[] mTitles;
    OnIndicaterTapClickListener onTabClickListener;

    public IndicatorAdapter(Context context, int id) {
        mTitles = context.getResources().getStringArray(id);

    }

    @Override
    public int getCount() {
        if (mTitles != null) {
            return mTitles.length;
        }
        return 0;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
        simplePagerTitleView.setNormalColor(Color.GRAY);
        simplePagerTitleView.setSelectedColor(Color.parseColor("#FB7299"));
        simplePagerTitleView.setText(mTitles[index]);
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mViewPager.setCurrentItem(index);
                //点击指示器时，   修改内容页面
                if (onTabClickListener != null) {
                    onTabClickListener.onTabClick(index);
                }
            }
        });
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        linePagerIndicator.setColors(Color.WHITE);
        return linePagerIndicator;
    }

    public void setIndicaterListener(OnIndicaterTapClickListener listener) {
        this.onTabClickListener = listener;
    }

    public interface OnIndicaterTapClickListener {
        void onTabClick(int index);
    }
}

package com.lq.myapp.utils;

import com.lq.myapp.base.BaseApplication;
import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.fragments.PicAnimalFragment;
import com.lq.myapp.fragments.PicAnimationFragment;
import com.lq.myapp.fragments.PicBeautyFragment;
import com.lq.myapp.fragments.PicMzituFragment;
import com.lq.myapp.testFragments.NewsOneFragment;
import com.lq.myapp.testFragments.NewsThreeFragment;
import com.lq.myapp.testFragments.NewsTwoFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lqhunter on 2018/12/26.
 */

public class NewsFragmentCreator {


    private final static int RADIO_INDEX_ANIMATION = 0;
    private final static int RADIO_INDEX_ANIMAL = 1;
    private final static int RADIO_INDEX_BEAUTY = 2;

    private final static int PAGE_COUNT = 3;
    private static final String TAG = "PicFragmentCreator";

    private static Map<Integer, BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragment(int index) {
        LogUtil.d(TAG, "index --> " + index);

        BaseFragment indexFragment = sCache.get(index);
        if (indexFragment != null) {
            LogUtil.d(TAG, indexFragment.toString());
            return indexFragment;
        }

        switch (index) {
            case RADIO_INDEX_ANIMATION:
                indexFragment = new NewsOneFragment();
                break;
            case RADIO_INDEX_ANIMAL:
                indexFragment = new NewsTwoFragment();
                break;
            case RADIO_INDEX_BEAUTY:
                indexFragment = new NewsThreeFragment();
                break;
        }
        sCache.put(index, indexFragment);
        return indexFragment;
    }

    public static int getPageCount() {
        return PAGE_COUNT;
    }
}

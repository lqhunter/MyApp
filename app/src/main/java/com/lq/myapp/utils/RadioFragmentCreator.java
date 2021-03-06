package com.lq.myapp.utils;

import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.fragments.RadioHistoryFragment;
import com.lq.myapp.fragments.RadioRecommendFragment;
import com.lq.myapp.fragments.RadioSubscribeFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lqhunter on 2018/12/26.
 */

public class RadioFragmentCreator {


    private final static int RADIO_INDEX_RECOMMEND = 0;
    private final static int RADIO_INDEX_SUBSCRIBE = 1;
    private final static int RADIO_INDEX_HISTORY = 2;

    public final static int PAGE_COUNT = 3;

    private static Map<Integer, BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragment(int index) {
        BaseFragment indexFragment = sCache.get(index);
        if (indexFragment != null) {
            return indexFragment;
        }

        switch (index) {
            case RADIO_INDEX_RECOMMEND:
                indexFragment = new RadioRecommendFragment();
                break;
            case RADIO_INDEX_SUBSCRIBE:
                indexFragment = new RadioSubscribeFragment();
                break;
            case RADIO_INDEX_HISTORY:
                indexFragment = new RadioHistoryFragment();
                break;
        }

        sCache.put(index, indexFragment);
        return indexFragment;
    }

}

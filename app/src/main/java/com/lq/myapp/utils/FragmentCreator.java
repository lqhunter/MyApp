package com.lq.myapp.utils;

import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.fragments.HistoryFragment;
import com.lq.myapp.fragments.RecommendFragment;
import com.lq.myapp.fragments.SubscribeFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lqhunter on 2018/12/26.
 */

public class FragmentCreator {


    private final static int INDEX_RECOMMEND = 0;
    private final static int INDEX_SUBSCRIBE = 1;
    private final static int INDEX_HISTORY = 2;

    public final static int PAGE_COUNT = 3;

    private static Map<Integer, BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragment(int index) {
        BaseFragment indexFragment = sCache.get(index);
        if (indexFragment != null) {
            return indexFragment;
        }

        switch (index) {
            case INDEX_RECOMMEND:
                indexFragment = new RecommendFragment();
                break;
            case INDEX_SUBSCRIBE:
                indexFragment = new SubscribeFragment();
                break;
            case INDEX_HISTORY:
                indexFragment = new HistoryFragment();
                break;
        }

        sCache.put(index, indexFragment);
        return indexFragment;
    }

}

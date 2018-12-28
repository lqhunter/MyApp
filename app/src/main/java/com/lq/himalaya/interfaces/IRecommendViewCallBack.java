package com.lq.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

/**
 * Created by lqhunter on 2018/12/28.
 */

public interface IRecommendViewCallBack {

    /**
     * 获取推荐内容结果
     */
    void onRecommendListLoad(List<Album> result);

    /**
     * 加载更多
     */
    void onLoadMore(List<Album> result);

    /**
     * 上拉刷新
     */
    void onRefreshMore(List<Album> result);
}

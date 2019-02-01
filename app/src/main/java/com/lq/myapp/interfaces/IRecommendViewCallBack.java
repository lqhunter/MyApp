package com.lq.myapp.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

/**
 *
 * Created by lqhunter on 2018/12/28.
 */

public interface IRecommendViewCallBack {

    /**
     * 获取推荐内容结果
     */
    void onRecommendListLoad(List<Album> result);


    /**
     * 网络错误
     */
    void onNetworkError();

    /**
     * 数据为空
     */
    void onEmpty();

    /**
     * 加载中
     */
    void onLoading();

}

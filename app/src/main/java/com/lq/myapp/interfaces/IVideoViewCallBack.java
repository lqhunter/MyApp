package com.lq.myapp.interfaces;

import com.lq.myapp.bean.VideoBean;

import java.util.List;

public interface IVideoViewCallBack {

    /**
     * 加载完成
     *
     * @param data
     */
    void onSuccess(List<VideoBean> data);

    /**
     * 加载中
     */
    void onLoading();


    void noMore();

    /**
     * 网络错误
     */
    void onNetworkError();

    /**
     * 数据为空
     */
    void onEmpty();
}

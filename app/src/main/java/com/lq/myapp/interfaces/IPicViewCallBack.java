package com.lq.myapp.interfaces;

import com.lq.myapp.bean.PicMzituBean;

import java.util.List;

public interface IPicViewCallBack {
    /**
     * 获取内容结果
     */
    void onPicListLoad(List<PicMzituBean> result);


    void onRefreshListLoad(List<PicMzituBean> result);

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

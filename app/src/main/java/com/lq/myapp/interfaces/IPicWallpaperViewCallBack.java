package com.lq.myapp.interfaces;

import com.lq.myapp.bean.PicMzituBean;
import com.lq.myapp.bean.PicWallpaperBean;

import java.util.List;

public interface IPicWallpaperViewCallBack {
    /**
     * 获取内容结果
     */
    void onPicListLoad(PicWallpaperBean result);


    void onRefreshListLoad(PicWallpaperBean result);

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

package com.lq.myapp.interfaces;

public interface IPicWallpaperPresenter<T> {
    /**
     * 下拉刷新
     */
    void pull2Refresh();

    /**
     * 上拉加载更多
     */
    void loadMore(int skip);

    void registerCallBack(T callBack);

    void unRegisterCallBack();
}

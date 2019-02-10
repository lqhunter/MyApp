package com.lq.myapp.interfaces;

public interface IPicPresenter {

    /**
     * 下拉刷新
     */
    void pull2Refresh(int page);

    /**
     * 上拉加载更多
     */
    void loadMore(int page);

    void registerCallBack(IPicViewCallBack callBack);

    void unRegisterCallBack();
}

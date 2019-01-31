package com.lq.himalaya.interfaces;

public interface IDetailPresenter {

    /**
     * 下拉刷新
     */
    void pull2Refresh();

    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 获取专辑详情
     *
     * @param albumId
     * @param page
     */
    void getAlbumDetail(int albumId, int page);

    /**
     * 注册ui通知接口
     *
     * @param detailViewCallback
     */
    void registerViewCallback(IDetailViewCallback detailViewCallback);

    /**
     * 取消ui通知接口
     *
     * @param detailViewCallback
     */
    void unregisterViewCallback(IDetailViewCallback detailViewCallback);
}
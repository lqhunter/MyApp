package com.lq.myapp.interfaces;

public interface IDetailPresenter {

    /**
     * 下拉刷新
     */
    void pull2Refresh();



    /**
     * 获取专辑详情
     *
     * @param skip
     */
    void getAlbumDetail(int skip);

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
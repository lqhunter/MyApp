package com.lq.myapp.interfaces;

/**
 *
 *
 * Created by lqhunter on 2018/12/28.
 */

public interface ISubscribePresenter {

    /**
     * 获取推荐内容
     */
    void getSubscribeData();

    /**
     * 获取推荐加载更过内容
     */
    void getMoreData(int skip);

    /**
     * 注册ui的回调
     * @param callBack
     */
    void registerViewCall(ISubscribeViewCallBack callBack);

    /**
     * 取消ui的回调
     */
    void unRegisterCallBack();

}

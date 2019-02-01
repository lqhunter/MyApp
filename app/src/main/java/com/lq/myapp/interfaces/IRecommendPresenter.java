package com.lq.myapp.interfaces;

/**
 *
 *
 * Created by lqhunter on 2018/12/28.
 */

public interface IRecommendPresenter {

    /**
     * 获取推荐内容
     */
    void getRecommendData();


    /**
     * 注册ui的回调
     * @param callBack
     */
    void registerViewCall(IRecommendViewCallBack callBack);

    /**
     * 取消ui的回调
     * @param callBack
     */
    void unRegisterCallBack(IRecommendViewCallBack callBack);

}
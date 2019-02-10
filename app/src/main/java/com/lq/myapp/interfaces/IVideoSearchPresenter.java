package com.lq.myapp.interfaces;

public interface IVideoSearchPresenter {

    void loadData(String key);

    void registerCallBack(IVideoSearchViewCallBack callBack);

    void unRegisterCallBack();


}

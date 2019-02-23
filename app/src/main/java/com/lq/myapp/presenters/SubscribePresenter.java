package com.lq.myapp.presenters;

import android.support.annotation.Nullable;

import com.lq.myapp.interfaces.ISubscribePresenter;
import com.lq.myapp.interfaces.ISubscribeViewCallBack;
import com.lq.myapp.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.auth.model.XmlyAuth2AccessToken;
import com.ximalaya.ting.android.opensdk.auth.utils.QrcodeLoginUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.SubscribeAlbumList;

import java.util.HashMap;
import java.util.Map;

/**
 * author : lqhunter
 * date : 2019/2/23 0023
 * description :
 */
public class SubscribePresenter implements ISubscribePresenter {
    private static final String TAG = "SubscribePresenter";
    private ISubscribeViewCallBack mSubscribeViewCallBack = null;

    @Override
    public void getSubscribeData() {
        Map<String, String> map = new HashMap<>();
        map.put("updated_at", 0 + "");
        CommonRequest.getAlbumByUid(map, new IDataCallBack<SubscribeAlbumList>() {
            @Override
            public void onSuccess(@Nullable SubscribeAlbumList subscribeAlbumList) {
                if (mSubscribeViewCallBack != null) {
                    LogUtil.d(TAG, "获取数据成功。。。");
                    mSubscribeViewCallBack.onSubscribeListLoad(subscribeAlbumList.getAlbums());
                }
            }

            @Override
            public void onError(int i, String s) {
                if (mSubscribeViewCallBack != null) {
                    LogUtil.d(TAG, "获取数据失败。。。"+ i);
                    mSubscribeViewCallBack.onNetworkError();
                }
            }
        });
    }

    @Override
    public void getMoreData(int skip) {

    }

    @Override
    public void registerViewCall(ISubscribeViewCallBack callBack) {
        this.mSubscribeViewCallBack = callBack;
    }

    @Override
    public void unRegisterCallBack() {
        if (mSubscribeViewCallBack != null) {
            mSubscribeViewCallBack = null;
        }
    }

}

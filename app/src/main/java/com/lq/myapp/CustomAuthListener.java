package com.lq.myapp;

import android.os.Bundle;

import com.lq.myapp.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.auth.call.IXmlyAuthListener;
import com.ximalaya.ting.android.opensdk.auth.exception.XmlyException;
import com.ximalaya.ting.android.opensdk.auth.model.XmlyAuth2AccessToken;
import com.ximalaya.ting.android.opensdk.datatrasfer.AccessTokenManager;

/**
 * author : lqhunter
 * date : 2019/2/23 0023
 * description :
 */

public abstract class CustomAuthListener implements IXmlyAuthListener {

    private static final String TAG = "CustomAuthListener";
    /**
     * 封装了 "access_token"，"refresh_token"，并提供了他们的管理功能
     */
    private XmlyAuth2AccessToken mAccessToken;

    // 当认证授权成功时，回调该方法
    @Override
    public void onComplete(Bundle bundle) {
        LogUtil.d(TAG, "授权成功...");
        parseAccessToken(bundle);
        initUserInfo();
    }

    public abstract void initUserInfo();


    // 当授权过程中发生异常（如回调地址无效等信息等）时，回调该方法
    @Override
    public void onXmlyException(final XmlyException e) {

    }

    // 当用户主动取消授权时，回调该方法
    @Override
    public void onCancel() {

    }

    private void parseAccessToken(Bundle bundle) {
        // 从 Bundle 中解析 access token
        mAccessToken = XmlyAuth2AccessToken.parseAccessToken(bundle);
        if (mAccessToken.isSessionValid()) {
            /**
             * 关键!!!!!!!!!!
             * 结果返回之后将取回的结果设置到token管理器中
             */
            AccessTokenManager.getInstanse().setAccessTokenAndUid(mAccessToken.getToken(), mAccessToken
                    .getRefreshToken(), mAccessToken.getExpiresAt(), mAccessToken.getUid());

        }
    }
}

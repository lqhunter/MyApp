package com.lq.myapp.base;

import android.app.Application;
import android.app.Notification;
import android.os.Handler;

import com.lq.myapp.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

/**
 *
 * Created by lqhunter on 2018/12/25.
 */

public class BaseApplication extends Application {

    private static Handler sHandler = null;
    public static boolean showMzitu = false;
    private Notification mNotification;

    @Override
    public void onCreate() {
        super.onCreate();

        CommonRequest mXimalaya = CommonRequest.getInstanse();
        if(DTransferConstants.isRelease) {
            String mAppSecret = "8646d66d6abe2efd14f2891f9fd1c8af";
            mXimalaya.setAppkey("9f9ef8f10bebeaa83e71e62f935bede8");
            mXimalaya.setPackid("com.app.test.android");
            mXimalaya.init(this ,mAppSecret);
        } else {
            String mAppSecret = "0a09d7093bff3d4947a5c4da0125972e";
            mXimalaya.setAppkey("f4d8f65918d9878e1702d49a8cdf0183");
            mXimalaya.setPackid("com.ximalaya.qunfeng");
            mXimalaya.init(this ,mAppSecret);
        }

        LogUtil.init(this.getPackageName(), false);

        sHandler = new Handler();

        mNotification = new Notification();
        XmPlayerManager.getInstance(this).init(0, mNotification);
    }

    public static  Handler getsHandler() {
        return sHandler;
    }


}

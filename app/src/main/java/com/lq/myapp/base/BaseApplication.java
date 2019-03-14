package com.lq.myapp.base;

import android.app.Application;
import android.app.Notification;
import android.os.Handler;

import com.lq.myapp.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.sdkdownloader.XmDownloadManager;

/**
 *
 * Created by lqhunter on 2018/12/25.
 */

public class BaseApplication extends Application {

    private static Handler sHandler = null;
    public static boolean showMzitu = false;

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

        Notification notification = new Notification();
        XmPlayerManager.getInstance(this).init(1, notification);
        XmDownloadManager.Builder(this)
                .maxDownloadThread(1)			// 最大的下载个数 默认为1 最大为3
                .maxSpaceSize(Long.MAX_VALUE)	// 设置下载文件占用磁盘空间最大值，单位字节。不设置没有限制
                .connectionTimeOut(15000)		// 下载时连接超时的时间 ,单位毫秒 默认 30000
                .readTimeOut(15000)				// 下载时读取的超时时间 ,单位毫秒 默认 30000
                .fifo(false)					// 等待队列的是否优先执行先加入的任务. false表示后添加的先执行(不会改变当前正在下载的音频的状态) 默认为true
                .maxRetryCount(3)				// 出错时重试的次数 默认2次
                .progressCallBackMaxTimeSpan(1000)//  进度条progress 更新的频率 默认是800
                .savePath(getExternalFilesDir("mp3").getAbsolutePath())	// 保存的地址 会检查这个地址是否有效
                .create();

    }

    public static  Handler getsHandler() {
        return sHandler;
    }


}

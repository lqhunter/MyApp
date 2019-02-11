package com.lq.myapp.utils;

import com.lq.myapp.interfaces.IPicService;
import com.lq.myapp.interfaces.IPicWallpaperService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 得到 IPicService接口 的实现类, 可以建立不同的接口在此取得 mMzituRetrofit 创建的实现类
 */
public class PicRetrofitHelper {
    private static PicRetrofitHelper sInstance = null;
    private static Retrofit mMzituRetrofit;
    private static Retrofit mWallpaperRetrofit;

    private IPicService mIPicService = null;
    private IPicWallpaperService mIPicWallpaperService = null;

    private PicRetrofitHelper() {
        mMzituRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.meizitu.net/")
                .addConverterFactory(GsonConverterFactory.create())
                //配置回调库，采用RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //可以设置OKHttpClient为网络客户端
                //.client(okHttpClientBuilder.build())
                .build();
        mWallpaperRetrofit = new Retrofit.Builder()
                .baseUrl("http://service.aibizhi.adesk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                //配置回调库，采用RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //可以设置OKHttpClient为网络客户端
                //.client(okHttpClientBuilder.build())
                .build();
    }

    public static PicRetrofitHelper getInstance() {
        if (sInstance == null) {
            synchronized (PicRetrofitHelper.class) {
                if (sInstance == null) {
                    sInstance = new PicRetrofitHelper();
                }
            }
        }

        return sInstance;
    }


    public IPicService getIPicMzituService() {
        if (mIPicService == null) {
            mIPicService = mMzituRetrofit.create(IPicService.class);
        }
        return mIPicService;
    }

    public IPicWallpaperService getIPicWallpaperService() {
        if (mIPicWallpaperService == null) {
            mIPicWallpaperService = mWallpaperRetrofit.create(IPicWallpaperService.class);
        }
        return mIPicWallpaperService;
    }
}

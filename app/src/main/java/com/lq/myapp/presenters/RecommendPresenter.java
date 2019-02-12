package com.lq.myapp.presenters;

import android.support.annotation.Nullable;

import com.lq.myapp.interfaces.IRecommendPresenter;
import com.lq.myapp.interfaces.IRecommendViewCallBack;
import com.lq.myapp.utils.Constants;
import com.lq.myapp.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by lqhunter on 2018/12/28.
 */

public class RecommendPresenter implements IRecommendPresenter {

    private static final String TAG = "RecommendPresenter";
    private List<IRecommendViewCallBack> callbacks = new ArrayList<>();

    private RecommendPresenter() {}

    private static RecommendPresenter sInstance = null;

    //单例
    public static RecommendPresenter getInstance() {
        if (sInstance == null) {
            synchronized (RecommendPresenter.class) {
                if (sInstance == null) {
                    sInstance = new RecommendPresenter();
                }
            }
        }

        return sInstance;
    }

    /**
     * 3.2.3 根据分类和标签获取某个分类某个标签下的专辑列表（最火/最新/最多播放）
     */
    @Override
    public void getRecommendData() {
        //显示正在加载界面
        updataLoading();
        //获取推荐内容
        getMoreData(0);
    }

    @Override
    public void getMoreData(int skip) {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.CATEGORY_ID ,"0");
        map.put(DTransferConstants.CALC_DIMENSION ,"1");
        map.put(DTransferConstants.PAGE, (skip / 20 + 1) + "");
        LogUtil.d(TAG, "page -- >" + (skip / 20 + 1));
        CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
            @Override
            public void onSuccess(@Nullable AlbumList albumList) {
                if (albumList != null) {
                    //数据回来更新UI
                    //upRecommendUI(albumList);
                    handlerRecommendResult(albumList.getAlbums());

                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void handlerError() {
        if (callbacks != null) {
            for (IRecommendViewCallBack callback : callbacks) {
                callback.onNetworkError();
            }
        }
    }

    private void handlerRecommendResult(List<Album> albumList) {
        LogUtil.d(TAG, Thread.currentThread().getName());
        //通知UI更新
        if (albumList != null) {
            //数据为空, 更新为 空 的ui
            if (albumList.size() == 0) {
                for (IRecommendViewCallBack callback : callbacks) {
                    callback.onEmpty();
                }
            } else {    //数据不为空, 正常显示
                for (IRecommendViewCallBack callback : callbacks) {
                    callback.onRecommendListLoad(albumList);
                }
            }
        }

    }

    private void updataLoading() {
        for (IRecommendViewCallBack callback : callbacks) {
            callback.onLoading();
        }
    }

    @Override
    public void registerViewCall(IRecommendViewCallBack callBack) {
        if (callbacks != null && !callbacks.contains(callBack)) {
            callbacks.add(callBack);
        }
    }

    @Override
    public void unRegisterCallBack(IRecommendViewCallBack callBack) {
        if (callbacks != null) {
            callbacks.remove(callBack);
        }
    }
}

package com.lq.himalaya.presents;

import android.support.annotation.Nullable;

import com.lq.himalaya.interfaces.IRecommendPresenter;
import com.lq.himalaya.interfaces.IRecommendViewCallBack;
import com.lq.himalaya.utils.Constants;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
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
     * 获取推荐内容
     * 接口：3.10.6 获取猜你喜欢专辑
     */
    @Override
    public void getRecommendData() {
        //显示正在加载界面
        updataLoading();
        //获取推荐内容
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.LIKE_COUNT, Constants.RECOMMEND_COUNT + "");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(@Nullable GussLikeAlbumList gussLikeAlbumList) {
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    //数据回来更新UI
                    //upRecommendUI(albumList);
                    handlerRecommendResult(albumList);

                }
            }

            @Override
            public void onError(int i, String s) {
                //处理错误
                handlerError();

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
        if (!callbacks.contains(callBack) && callbacks != null) {
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

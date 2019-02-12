package com.lq.myapp.presenters;

import android.support.annotation.Nullable;

import com.lq.myapp.interfaces.IDetailPresenter;
import com.lq.myapp.interfaces.IDetailViewCallback;
import com.lq.myapp.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadioDetailPresenter implements IDetailPresenter {

    private static final String TAG = "RadioDetailPresenter";
    private List<IDetailViewCallback> mCallbacks = new ArrayList<>();

    private Album mAlbum = null;

    private RadioDetailPresenter() {

    }

    private static RadioDetailPresenter sInstance = null;

    public static RadioDetailPresenter getDetailPresenter() {
        if (sInstance == null) {
            synchronized (RadioDetailPresenter.class) {
                if (sInstance == null) {
                    sInstance = new RadioDetailPresenter();
                }
            }
        }

        return sInstance;
    }

    public long getAlbumCount() {
        if (mAlbum != null) {
            return mAlbum.getIncludeTrackCount();
        }
        return 0;
    }

    @Override
    public void pull2Refresh() {

    }


    @Override
    public void getAlbumDetail(int skip) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.ALBUM_ID, mAlbum.getId() + "");
        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, (skip / 20 + 1) + "");
        LogUtil.d(TAG, "page-->" + (skip / 20 + 1));
        CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(@Nullable TrackList trackList) {
                if (trackList != null) {
                    LogUtil.d(TAG, "size-->" + trackList.getTracks().size());

                    for (IDetailViewCallback callback : mCallbacks) {
                        callback.onDetailListLoad(trackList.getTracks());
                    }
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    @Override
    public void registerViewCallback(IDetailViewCallback detailViewCallback) {
        if (!mCallbacks.contains(detailViewCallback)) {
            mCallbacks.add(detailViewCallback);
            if (mAlbum != null) {
                //更新ui
                //此数据的设置不需要再从网络获取，注册回调后可直接调用回调设置ui
                detailViewCallback.onAlbumLoad(mAlbum);
            }
        }
    }

    @Override
    public void unregisterViewCallback(IDetailViewCallback detailViewCallback) {
        mCallbacks.remove(detailViewCallback);
    }

    public void setTargetAlbum(Album album) {
        this.mAlbum = album;
    }
}

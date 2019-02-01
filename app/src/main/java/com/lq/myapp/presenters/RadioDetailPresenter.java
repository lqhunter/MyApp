package com.lq.myapp.presenters;

import com.lq.myapp.interfaces.IDetailPresenter;
import com.lq.myapp.interfaces.IDetailViewCallback;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class RadioDetailPresenter implements IDetailPresenter {

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

    @Override
    public void pull2Refresh() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void getAlbumDetail(int albumId, int page) {

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

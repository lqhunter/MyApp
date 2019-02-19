package com.lq.myapp.presenters;

import com.lq.myapp.bean.PicWallpaperBean;
import com.lq.myapp.interfaces.IPicWallpaperPresenter;
import com.lq.myapp.interfaces.IPicWallpaperViewCallBack;
import com.lq.myapp.utils.LogUtil;
import com.lq.myapp.utils.PicRetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PicWallpaperPresenter implements IPicWallpaperPresenter<IPicWallpaperViewCallBack> {


    private static final String TAG = "PicWallpaperPresenter";
    public IPicWallpaperViewCallBack picWallpaperViewCallBack;

    @Override
    public void pull2Refresh() {

    }

    @Override
    public void loadMore(int skip) {
        PicRetrofitHelper.getInstance().getIPicWallpaperService()
                .getCatoonWallpaperObservable(30, skip, true, 0, "new")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PicWallpaperBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PicWallpaperBean value) {
                        LogUtil.d(TAG, "onNext, value.size() --- > " + value.getRes().getVertical().size());
                        if (picWallpaperViewCallBack != null) {
                            picWallpaperViewCallBack.onPicListLoad(value);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "onError..");

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d(TAG, "onComplete... ");
                    }
                });
    }


    @Override
    public void registerCallBack(IPicWallpaperViewCallBack callBack) {
        this.picWallpaperViewCallBack = callBack;
    }

    @Override
    public void unRegisterCallBack() {
        if (picWallpaperViewCallBack != null) {
            picWallpaperViewCallBack = null;
        }
    }
}

package com.lq.myapp.fragments;

import com.lq.myapp.bean.PicWallpaperBean;
import com.lq.myapp.presenters.PicWallpaperPresenter;
import com.lq.myapp.utils.LogUtil;
import com.lq.myapp.utils.PicRetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PicBeautyFragment extends PicAnimationFragment {

    private static String TAG = "PicBeautyFragment";

    @Override
    public void initData() {
        mPicWallpaperPresenter = new PicWallpaperPresenter() {
            @Override
            public void loadMore(int skip) {
                PicRetrofitHelper.getInstance().getIPicWallpaperService()
                        .getArtWallpaperObservable(30, skip, true, 0, "new")
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
        };
        mPicWallpaperPresenter.registerCallBack(this);
        mPicWallpaperPresenter.loadMore(0);
    }
}

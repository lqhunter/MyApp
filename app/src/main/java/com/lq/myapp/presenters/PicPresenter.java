package com.lq.myapp.presenters;

import com.lq.myapp.bean.PicMzituBean;
import com.lq.myapp.interfaces.IPicPresenter;
import com.lq.myapp.interfaces.IPicViewCallBack;
import com.lq.myapp.utils.LogUtil;
import com.lq.myapp.utils.PicRetrofitHelper;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PicPresenter implements IPicPresenter {


    private static final String TAG = "PicPresenter";
    private IPicViewCallBack picViewCallBack = null;

    @Override
    public void pull2Refresh(int page) {
        PicRetrofitHelper.getInstance().getIPicService()
                .getAlbumsObservable(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PicMzituBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PicMzituBean> value) {
                        LogUtil.d(TAG, "数据大小 -->" + value.size());
                        picViewCallBack.onRefreshListLoad(value);

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "onError -->" + "onError");

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadMore(int page) {
        PicRetrofitHelper.getInstance().getIPicService()
                .getAlbumsObservable(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PicMzituBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PicMzituBean> value) {
                        LogUtil.d(TAG, value.size() + "");
                        picViewCallBack.onPicListLoad(value);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void registerCallBack(IPicViewCallBack callBack) {
        this.picViewCallBack = callBack;
    }

    @Override
    public void unRegisterCallBack() {
        if (picViewCallBack != null) {
            picViewCallBack = null;
        }
    }
}

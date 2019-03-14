package com.lq.myapp.testFragments;

import com.lq.myapp.bean.News;
import com.lq.myapp.interfaces.INewsService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsThreeFragment extends NewsOneFragment {


    //http://api.cportal.cctv.com/api/rest/navListInfo/getHandDataListInfoNew?id=Nav-iqwRTtNj4tQCEkyUkBzW160812&toutuNum=1&version=1&p=2&n=20
    @Override
    public void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.cportal.cctv.com/")
                .addConverterFactory(GsonConverterFactory.create())
                //配置回调库，采用RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        INewsService newsService = retrofit.create(INewsService.class);

        newsService.getNewObservable("Nav-iqwRTtNj4tQCEkyUkBzW160812", 1, 1, 5,20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<News>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(News value) {
                        mAdapter.setData(value.getItemList());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

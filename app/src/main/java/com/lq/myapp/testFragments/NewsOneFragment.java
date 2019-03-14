package com.lq.myapp.testFragments;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.myapp.R;
import com.lq.myapp.activities.WebActivity;
import com.lq.myapp.adapters.NewsRlvAdapter;
import com.lq.myapp.base.BaseFragment;
import com.lq.myapp.bean.News;
import com.lq.myapp.interfaces.INewsService;
import com.lq.myapp.utils.LogUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsOneFragment extends BaseFragment {


    private static final String TAG = "NewsOneFragment";
    public View mRootView;
    public RecyclerView mRlv;
    public NewsRlvAdapter mAdapter;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {

        mRootView = layoutInflater.inflate(R.layout.fragment_news_one, container,false);

        initView();

        return mRootView;
    }

    private void initView() {
        mRlv = mRootView.findViewById(R.id.news_rlv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRlv.setLayoutManager(gridLayoutManager);
        mAdapter = new NewsRlvAdapter();
        mRlv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NewsRlvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String url) {
                LogUtil.d(TAG, "url--->" + url);
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.cportal.cctv.com/")
                .addConverterFactory(GsonConverterFactory.create())
                //配置回调库，采用RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        INewsService newsService = retrofit.create(INewsService.class);

        newsService.getNewObservable("Nav-9Nwml0dIB6wAxgd9EfZA160510", 5, 1, 5,20)
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

package com.lq.myapp.presenters;

import com.lq.myapp.bean.VideoBean;
import com.lq.myapp.interfaces.IVideoViewCallBack;
import com.lq.myapp.utils.LogUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoPresenter {

    private static boolean hasMore = true;
    private int page = 2;

    List<VideoBean> mAllData = new ArrayList<>();

    List<VideoBean> data = new ArrayList<>();
    private static final String TAG = "VideoPresenter";
    String scienceUrl = "https://91mjw.com/category/all_mj/kehuanpian";
    String scienceUrl2 = "https://91mjw.com/category/all_mj/kehuanpian/page/2";
    private IVideoViewCallBack mVideoViewCallBack = null;
    private static VideoPresenter sInstance = null;

    private VideoPresenter() {

    }

    //单例
    public static VideoPresenter getInstance() {
        if (sInstance == null) {
            synchronized (RecommendPresenter.class) {
                if (sInstance == null) {
                    sInstance = new VideoPresenter();
                }
            }
        }

        return sInstance;
    }


    public VideoBean getData(int position) {
        return mAllData.get(position);
    }

    public void loadData() {
        mVideoViewCallBack.onLoading();
        loadDataByUrl(scienceUrl, "0");
    }

    private void loadDataByUrl(String url, String page) {
        String s = url + "/page/" + page;
        LogUtil.d(TAG, "获取 " + s);

        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url(url + "/page/" + page).method("GET",null).build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e(TAG, "获取 " + scienceUrl + " 失败");
                mVideoViewCallBack.onNetworkError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.e(TAG, "获取 " + scienceUrl + "_html 成功");
                LogUtil.d(TAG, Thread.currentThread().getName());
                parseHtml(response.body().string());

            }
        });
    }

    private void parseHtml(String html) {
        Document document = Jsoup.parse(html);
        Elements All = document.select("div[class=m-movies clearfix]").select("article[class=u-movie]");
        if (All.size() < 42) {  //当前页面小于42个，表面以后就没有了
            hasMore = false;
        }

        data.clear();
        for(Element one : All) {
            VideoBean temp = new VideoBean();

            //标题
            temp.setTitle(one.select("a").select("h2").text());
            //Log.d(TAG, "title " + temp.getTitle());

            temp.setUpdateStatus(one.select("div[class=zhuangtai]").select("span").text());
            //Log.d(TAG, "title " + temp.getUpdateStatus());

            //详情 URL
            temp.setDetailURL(one.select("a").attr("href"));
            //Log.d(TAG, "DetailURL " + temp.getDetailURL());

            //封面 URL 获取
            String coverURL = one.select("a").select("div[class=list-poster]").select("img").attr("data-original");
            if (!coverURL.contains("https")) {
                coverURL = "https:" + coverURL;
            }
            temp.setCoverURL(coverURL);
            //Log.d(TAG, "CoverURL " + temp.getCoverURL());

            data.add(temp);
        }

        mAllData.addAll(data);

        mVideoViewCallBack.onSuccess(data);

    }

    public void setVideoViewCallBack(IVideoViewCallBack callBack) {
        this.mVideoViewCallBack = callBack;
    }




    public void loadMore() {
        if (hasMore) {
            loadDataByUrl(scienceUrl, page + "");
            page++;
        } else
            mVideoViewCallBack.noMore();
    }


}

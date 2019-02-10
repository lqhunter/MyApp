package com.lq.myapp.presenters;

import com.lq.myapp.bean.VideoBean;
import com.lq.myapp.interfaces.IVideoSearchPresenter;
import com.lq.myapp.interfaces.IVideoSearchViewCallBack;

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

public class VideoSearchPresenter implements IVideoSearchPresenter {

    private static VideoSearchPresenter sInstance = null;
    private List<VideoBean> mData = new ArrayList<>();
    private String baseURL = "https://91mjw.com/?s=";
    private IVideoSearchViewCallBack mVideoSearchViewCallBack = null;

    private VideoSearchPresenter() {

    }

    public static VideoSearchPresenter getInstance() {
        if (sInstance == null) {
            synchronized (VideoSearchPresenter.class) {
                if (sInstance == null) {
                    sInstance = new VideoSearchPresenter();
                }
            }
        }

        return sInstance;
    }

    @Override
    public void loadData(String key) {
        loadDataByURL(key);
    }

    private void loadDataByURL(String key) {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url(baseURL + key).method("GET",null).build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                parseHtml(response.body().string());
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }


        });
    }

    private void parseHtml(String html) {
        Document document = Jsoup.parse(html);
        Elements All = document.select("div[class=m-movies clearfix]").select("article[class=u-movie]");

        mData.clear();
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

            mData.add(temp);
        }

        mVideoSearchViewCallBack.onSuccess(mData);

    }

    @Override
    public void registerCallBack(IVideoSearchViewCallBack callBack) {
        this.mVideoSearchViewCallBack = callBack;
    }

    public static boolean hasInstance() {
        if (sInstance == null) {
            return false;
        } else
            return true;
    }

    @Override
    public void unRegisterCallBack() {
        this.mVideoSearchViewCallBack = null;
    }
}

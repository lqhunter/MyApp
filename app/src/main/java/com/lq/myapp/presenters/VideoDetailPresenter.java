package com.lq.myapp.presenters;

import android.content.Context;
import android.util.Log;

import com.lq.myapp.utils.LogUtil;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoDetailPresenter {

    private int count = 0;
    private static final String TAG = "RadioDetailPresenter";
    private String detailURL;
    private List<String> videoURL = new ArrayList<>();
    private HashMap<Integer, String> data = new HashMap<>();
    private OnGetVideoURLCallBack mOnGetVideoURLCallBack = null;

    public VideoDetailPresenter(String detailURL, Context context) {
        this.detailURL = detailURL;
        getCount();
    }


    public void getCount() {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url(detailURL).method("GET", null).build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "获取 " + detailURL + " 失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到总集数
                parseHtml(response.body().string());
                mOnGetVideoURLCallBack.onGetCount(count);

            }
        });
    }

    public void getVideoURL(final int num) {

        LogUtil.d(TAG, "getVideoURL");
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url(detailURL + "?Play=" + num).method("GET", null).build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "获取 " + detailURL + " 失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.d(TAG, "onResponse");
                //String s = response.body().string();
                //System.out.println("RadioDetailPresenter === " + s);

                Document document = Jsoup.parse(response.body().string());
                //Elements select = document.select("div[class=video-content]");
                Elements a = document.select("div[class=video-content]").select("script");
                LogUtil.d(TAG, "a.size()=====" + a.size());
                String m3u8URL = StringUtils.substringBetween(a.get(3).data(), "tvid=\"", "\";");
                LogUtil.d(TAG, "tvid=====" + m3u8URL);
                mOnGetVideoURLCallBack.onGetVideoURL(m3u8URL, num);
            }
        });
    }

    private void parseHtml(String detailHTML) {
        //解析，得到总集数，播放页面URL = detailURL + "?play=" + count;
        Document document = Jsoup.parse(detailHTML);
        Elements all = document.select("div[class=video_list]");

        if (!all.isEmpty()) {
            Elements all1 = all.get(1).select("a");
            count = all1.size();
        }

    }

    public void setOnGetVideoURLCallBack(OnGetVideoURLCallBack callBack) {
        this.mOnGetVideoURLCallBack = callBack;
    }

    //获得M3U8 URL地址后的回调
    public interface OnGetVideoURLCallBack {
        void onGetVideoURL(String videoURL, int num);

        void onGetCount(int count);
    }

}

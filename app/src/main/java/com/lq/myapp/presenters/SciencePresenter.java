package com.lq.myapp.presenters;

import com.lq.myapp.bean.VideoBean;
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

public class SciencePresenter {

    List<VideoBean> data = new ArrayList<>();
    private static final String TAG = "SciencePresenter";
    String scienceUrl = "https://91mjw.com/category/all_mj/kehuanpian";
    private OnGetURLDataSuccessCallBack mOnGetURLDataSuccessCallBack = null;

    public SciencePresenter() {

    }

    public void getData() {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url(scienceUrl).method("GET",null).build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e(TAG, "获取 " + scienceUrl + " 失败");
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


        mOnGetURLDataSuccessCallBack.onSuccess(data);


    }

    public void setOnGetURLDataSuccessCallBack(OnGetURLDataSuccessCallBack callBack) {
        this.mOnGetURLDataSuccessCallBack = callBack;
    }


    public interface OnGetURLDataSuccessCallBack {
        void onSuccess(List<VideoBean> data);
    }


}

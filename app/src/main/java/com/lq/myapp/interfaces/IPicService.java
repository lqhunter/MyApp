package com.lq.myapp.interfaces;



import com.lq.myapp.bean.PicMzituBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * 第一个接口只使用 Retrofit
 *
 * 第二个使用 Retrofit + RxJava
 *
 * 此处由于api返回的json是无 Key 值的 json数组，用list作为返回结果
 */
public interface IPicService {


    @Headers("referer:https://app.mmzztt.com")
    @GET("wp-json/wp/v2/rand")
    Observable<List<PicMzituBean>> getAlbumsObservable(@Query("page") int page);



}

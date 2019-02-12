package com.lq.myapp.interfaces;



import com.lq.myapp.bean.PicMzituBean;
import com.lq.myapp.bean.PicWallpaperBean;

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
    Observable<List<PicMzituBean>> getMzituObservable(@Query("page") int page);

    /**
     * http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000003/vertical?limit=30&adult=false&first=1&order=new
     * http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000003/vertical?limit=30&skip=30&adult=false&first=0&order=new
     * @return
     */


}

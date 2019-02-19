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
public interface IPicWallpaperService {

    /**
     * 动漫
     * http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000003/vertical?limit=30&adult=false&first=1&order=new
     * http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000003/vertical?limit=30&skip=30&adult=false&first=0&order=new
     * @return
     */

    @GET("v1/vertical/category/4e4d610cdf714d2966000003/vertical")
    Observable<PicWallpaperBean> getAnimeWallpaperObservable(@Query("limit") int limit,
                                                             @Query("skip") int skip,
                                                             @Query("adult") boolean adult,
                                                             @Query("first") int first,
                                                             @Query("order") String order);


    /**
     * 妹子
     * http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000000/vertical?limit=30&skip=30&adult=false&first=0&order=new
     * @param limit
     * @param skip
     * @param adult
     * @param first
     * @param order
     * @return
     */
    @GET("v1/vertical/category/4e4d610cdf714d2966000000/vertical")
    Observable<PicWallpaperBean> getBeautyWallpaperObservable(@Query("limit") int limit,
                                                             @Query("skip") int skip,
                                                             @Query("adult") boolean adult,
                                                             @Query("first") int first,
                                                             @Query("order") String order);

    /**
     * 动物
     * http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000001/vertical?limit=30&skip=30&adult=false&first=0&order=new
     * @param limit
     * @param skip
     * @param adult
     * @param first
     * @param order
     * @return
     */
    @GET("v1/vertical/category/4e4d610cdf714d2966000001/vertical")
    Observable<PicWallpaperBean> getAnimalWallpaperObservable(@Query("limit") int limit,
                                                              @Query("skip") int skip,
                                                              @Query("adult") boolean adult,
                                                              @Query("first") int first,
                                                              @Query("order") String order);

    /**
     * 卡通
     * http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000004/vertical?limit=30&adult=false&first=1&order=new
     * @param limit
     * @param skip
     * @param adult
     * @param first
     * @param order
     * @return
     */
    @GET("v1/vertical/category/4e4d610cdf714d2966000004/vertical")
    Observable<PicWallpaperBean> getCatoonWallpaperObservable(@Query("limit") int limit,
                                                              @Query("skip") int skip,
                                                              @Query("adult") boolean adult,
                                                              @Query("first") int first,
                                                              @Query("order") String order);

    /**
     * 卡通
     * http://service.aibizhi.adesk.com/v1/vertical/category/4ef0a3330569795757000000/vertical?limit=30&skip=30&adult=false&first=0&order=new
     * @param limit
     * @param skip
     * @param adult
     * @param first
     * @param order
     * @return
     */
    @GET("v1/vertical/category/4ef0a3330569795757000000/vertical")
    Observable<PicWallpaperBean> getArtWallpaperObservable(@Query("limit") int limit,
                                                              @Query("skip") int skip,
                                                              @Query("adult") boolean adult,
                                                              @Query("first") int first,
                                                              @Query("order") String order);
}

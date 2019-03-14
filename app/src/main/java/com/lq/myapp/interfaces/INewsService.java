package com.lq.myapp.interfaces;


import com.lq.myapp.bean.News;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * http://api.cportal.cctv.com/api/rest/navListInfo/getHandDataListInfoNew?id=Nav-9Nwml0dIB6wAxgd9EfZA160510&toutuNum=5&version=1&p=5&n=20
 */
public interface INewsService {

    @GET("api/rest/navListInfo/getHandDataListInfoNew")
    Observable<News> getNewObservable(@Query("id") String id,
                                      @Query("toutuNum") int toutuNum,
                                      @Query("version") int version,
                                      @Query("p") int p,
                                      @Query("n") int n
    );

}

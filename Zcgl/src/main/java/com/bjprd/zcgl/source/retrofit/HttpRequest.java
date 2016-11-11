package com.bjprd.zcgl.source.retrofit;


import com.bjprd.zcgl.source.db.bean.LoginBean;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 王少岩 on 2016/11/1.
 */

public class HttpRequest {

    private static final int DEFAULT_TIMEOUT = 10;

    private static Retrofit retrofit;

    private HttpRequest() {
    }

    //获取单例
    private static Retrofit getInstance() {
        if (retrofit == null) {
            //手动创建一个OkHttpClient并设置超时时间
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new BaseInterceptor());
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(HttpConsts.getServer())
                    .build();
        }
        return retrofit;
    }

    public static Observable<LoginBean> login(Map<String, String> params){
        return getInstance().create(RequestApi.class).login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}

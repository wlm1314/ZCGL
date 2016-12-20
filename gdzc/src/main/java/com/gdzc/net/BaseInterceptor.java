package com.gdzc.net;

import com.gdzc.utils.BaseLog;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 王少岩 on 2016/11/9.
 */

/**
 * retrofit拦截器
 * 添加通用参数
 */
public class BaseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {


        Request original = chain.request();
        //添加通用请求参数
        HttpUrl url = original.url().newBuilder()
                .build();

        Request request = original.newBuilder()
                .url(url)
                .build();
        BaseLog.i("BaseParam----------->>>" + url.toString());
        return chain.proceed(request);
    }
}

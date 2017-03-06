package com.gdzc.net.http;

import android.text.TextUtils;

import com.gdzc.net.consts.MD5Tools;
import com.gdzc.utils.SPUtils;

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
        HttpUrl url;
        if (TextUtils.isEmpty(SPUtils.getString(SPUtils.kUser_username, "")))
            url = original.url().newBuilder().build();
        else
            url = original.url().newBuilder()
                    .addQueryParameter("username", SPUtils.getString(SPUtils.kUser_username, ""))
                    .addQueryParameter("md5", MD5Tools.getMd5(SPUtils.getString(SPUtils.kUser_username, "")))
                    .build();

        Request request = original.newBuilder()
                .url(url)
                .build();
        return chain.proceed(request);
    }
}

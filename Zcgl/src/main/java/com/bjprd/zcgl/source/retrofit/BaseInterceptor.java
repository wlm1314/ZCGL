package com.bjprd.zcgl.source.retrofit;

import com.bjprd.zcgl.utils.SPUtils;
import com.bjprd.zcgl.utils.Utils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 王少岩 on 2016/11/9.
 */

public class BaseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        String time = System.currentTimeMillis() + "";
        String timeCheckValue = Utils.INSTANCE.md5(time + HttpConsts.kTime);
        String token = SPUtils.INSTANCE.getString(SPUtils.INSTANCE.getKUser_token());
        String tokenCheckValue = Utils.INSTANCE.md5(token + HttpConsts.kToken);

        Request original = chain.request();
        //添加通用请求参数
        HttpUrl url = original.url().newBuilder()
                .addQueryParameter(HttpConsts.kRequest_params_time, time)
                .addQueryParameter(HttpConsts.kRequest_params_timeCheckValue, timeCheckValue)
                .addQueryParameter(HttpConsts.kRequest_params_sourceType, HttpConsts.kSourceType_android)
                .addQueryParameter(HttpConsts.kRequest_params_projectId, HttpConsts.kProjectId)
                .addQueryParameter(HttpConsts.kRequest_params_token, token)
                .addQueryParameter(HttpConsts.kRequest_params_tokenCheckValue, tokenCheckValue)
                .build();

        Request request = original.newBuilder()
                .url(url)
                .build();
        return chain.proceed(request);
    }
}

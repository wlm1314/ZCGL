package com.bjprd.zcgl.source.retrofit;


import com.bjprd.zcgl.source.db.bean.LoginBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 王少岩 on 2016/11/9.
 */

public interface RequestApi {
    @FormUrlEncoded
    @POST(HttpPath.UserUrl.appLoginUrl)
    Observable<LoginBean> login(@FieldMap Map<String, String> params);
}

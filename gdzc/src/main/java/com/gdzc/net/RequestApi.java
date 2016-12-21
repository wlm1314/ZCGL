package com.gdzc.net;

import com.gdzc.flh.model.FlhBean;
import com.gdzc.login.model.LoginBean;
import com.gdzc.lydw.model.LydwBean;
import com.gdzc.syfx.model.SyfxBean;
import com.gdzc.zcdj.model.ZcdjBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 王少岩 on 2016/11/9.
 */

/**
 * retrofit 以注解的方式访问接口
 * <p>
 * post请求方式如下
 *
 * @FormUrlEncoded
 * @POST(url) Observable<T> method(@FieldMap Map<String, String> params);
 */
public interface RequestApi {
    //登录
    @FormUrlEncoded
    @POST(HttpPath.loginUrl)
    Observable<LoginBean> Login(@FieldMap Map<String, String> params);
    //获取分类号
    @FormUrlEncoded
    @POST(HttpPath.getFlhUrl)
    Observable<FlhBean> GetFlh(@FieldMap Map<String, String> params);
    //资产登记页面
    @FormUrlEncoded
    @POST(HttpPath.getTsxxUrl)
    Observable<ZcdjBean> GetTsxx(@FieldMap Map<String, String> params);
    //获取单位列表
    @FormUrlEncoded
    @POST(HttpPath.getDwUrl)
    Observable<LydwBean> GetDwList(@FieldMap Map<String, String> params);
    //获取使用方向
    @FormUrlEncoded
    @POST(HttpPath.getMkUrl)
    Observable<SyfxBean> GetMkList(@FieldMap Map<String, String> params);

}

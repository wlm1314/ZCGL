package com.gdzc.net.http;

import com.gdzc.BuildConfig;
import com.gdzc.base.BaseBean;
import com.gdzc.login.model.LoginBean;
import com.gdzc.net.consts.HttpConsts;
import com.gdzc.net.entity.HttpResult;
import com.gdzc.zccx.model.ZccxBean;
import com.gdzc.zcdj.cch.model.CchBean;
import com.gdzc.zcdj.flh.model.FlhBean;
import com.gdzc.zcdj.mk.model.CfdBean;
import com.gdzc.zcdj.mk.model.DwBean;
import com.gdzc.zcdj.mk.model.MKBean;
import com.gdzc.zcdj.mk.model.RyBean;
import com.gdzc.zcdj.zcdj.model.TsxxBean;
import com.gdzc.zcdj.zcdj.model.ZcxgBean;
import com.gdzc.zcdj.zcdj.model.ZcxgEditBean;
import com.gdzc.zctj.model.ZctjBean;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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

            if (BuildConfig.DEBUG) {
                //新建log拦截器
                builder.addInterceptor(new LoggerInterceptor());
            }

            retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(HttpConsts.getServer())
                    .build();
        }
        return retrofit;
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private static class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (!httpResult.getStatus().isSuccess()) {
                throw new ApiException(httpResult.getStatus().code, httpResult.getStatus().msg);
            }
            return httpResult.getData();
        }
    }

    /**
     * 登录
     *
     * @param params 登录参数
     * @return
     */
    public static Observable<LoginBean> Login(Map<String, String> params) {
        return getInstance().create(RequestApi.class).Login(params)
                .map(new HttpResultFunc<>())
                .compose(applySchedulers());
    }

    /**
     * 获取分类号
     *
     * @param params
     * @return
     */
    public static Observable<FlhBean> GetFlh(Map<String, String> params) {
        return getInstance().create(RequestApi.class).GetFlh(params)
                .map(new HttpResultFunc<>())
                .compose(applySchedulers());
    }

    /**
     * 获取资产登记页面
     *
     * @param params
     * @return
     */
    public static Observable<HttpResult<TsxxBean>> GetTsxx(Map<String, String> params) {
        return getInstance().create(RequestApi.class).GetTsxx(params)
                .compose(applySchedulers());
    }

    /**
     * 获取单位
     *
     * @param params
     * @return
     */
    public static Observable<DwBean> GetDwList(Map<String, String> params) {
        return getInstance().create(RequestApi.class).GetDwList(params)
                .map(new HttpResultFunc<>())
                .compose(applySchedulers());
    }

    /**
     * 获取使用方向
     *
     * @param params
     * @return
     */
    public static Observable<MKBean> GetMkList(Map<String, String> params) {
        return getInstance().create(RequestApi.class).GetMkList(params)
                .map(new HttpResultFunc<>())
                .compose(applySchedulers());
    }

    /**
     * 获取人员
     *
     * @param params
     * @return
     */
    public static Observable<RyBean> GetRyList(Map<String, String> params) {
        return getInstance().create(RequestApi.class).GetRyList(params)
                .map(new HttpResultFunc<>())
                .compose(applySchedulers());
    }

    /**
     * 获取存放地
     *
     * @param params
     * @return
     */
    public static Observable<CfdBean> GetCfdList(Map<String, String> params) {
        return getInstance().create(RequestApi.class).GetCfdList(params)
                .map(new HttpResultFunc<>())
                .compose(applySchedulers());
    }

    /**
     * 新增数据
     *
     * @param params
     * @return
     */
    public static Observable<HttpResult> AddNew(Map<String, String> params) {
        return getInstance().create(RequestApi.class).AddNew(params)
                .compose(applySchedulers());
    }

    /**
     * 更新数据
     *
     * @param params
     * @return
     */
    public static Observable<HttpResult> UpdateZj(Map<String, String> params) {
        return getInstance().create(RequestApi.class).UpdateZj(params)
                .compose(applySchedulers());
    }

    /**
     * 查询自己录入的数据
     *
     * @param params
     * @return
     */
    public static Observable<ZcxgBean> SearchMyData(Map<String, String> params) {
        return getInstance().create(RequestApi.class).SearchMyData(params)
                .compose(applySchedulers());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<ArrayList<ZcxgEditBean>> SearchZJById(Map<String, String> params) {
        return getInstance().create(RequestApi.class).SearchZJById(params)
                .map(new HttpResultFunc<>())
                .compose(applySchedulers());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<CchBean> SelectCchByYqbh(Map<String, String> params) {
        return getInstance().create(RequestApi.class).SelectCchByYqbh(params)
                .map(new HttpResultFunc<>())
                .compose(applySchedulers());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<BaseBean> UpdateCchById(Map<String, String> params) {
        return getInstance().create(RequestApi.class).UpdateCchById(params)
                .compose(applySchedulers());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<BaseBean> DeleteZjById(Map<String, String> params) {
        return getInstance().create(RequestApi.class).DeleteZjById(params)
                .compose(applySchedulers());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<ZctjBean> SelectMyDataTotalByCategory(Map<String, String> params) {
        return getInstance().create(RequestApi.class).SelectMyDataTotalByCategory(params)
                .compose(applySchedulers());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<ZcxgBean> SelectMyAllData(Map<String, String> params) {
        return getInstance().create(RequestApi.class).SelectMyAllData(params)
                .compose(applySchedulers());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<ZccxBean> SelectPoolById(Map<String, String> params) {
        return getInstance().create(RequestApi.class).SelectPoolById(params)
                .compose(applySchedulers());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<ZcxgBean> selectMySonData(Map<String, String> params) {
        return getInstance().create(RequestApi.class).selectMySonData(params)
                .compose(applySchedulers());
    }

    /**
     * @param
     * @return
     */
    public static Observable<String> ImageUpload(Map<String, RequestBody> params) {
        return getInstance().create(RequestApi.class).ImageUpload(params)
                .map(new HttpResultFunc<>())
                .compose(applySchedulers());
    }

    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}

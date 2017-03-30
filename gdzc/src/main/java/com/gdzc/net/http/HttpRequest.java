package com.gdzc.net.http;

import android.support.annotation.NonNull;

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
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
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
                builder.addInterceptor(sLoggingInterceptor);
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
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = chain -> {
        final Request request = chain.request();
        Buffer requestBuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestBuffer);
        } else {
            Logger.d("LogTAG", "request.body() == null");
        }
        //打印url信息
        Logger.w(request.url() + (request.body() != null ? "?" + parseParams(request.body(), requestBuffer) : ""));
        final Response response = chain.proceed(request);

        //the response data
        ResponseBody body = response.body();

        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.defaultCharset();
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String bodyString = buffer.clone().readString(charset);
        Logger.i(bodyString);
        return response;
    };

    @NonNull
    private static String parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
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
     * @param params
     * @return
     */
    public static Observable<ZcxgBean> underMyNameData(Map<String, String> params) {
        return getInstance().create(RequestApi.class).underMyNameData(params)
                .compose(applySchedulers());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<ZcxgBean> recipientsWho(Map<String, String> params) {
        return getInstance().create(RequestApi.class).recipientsWho(params)
                .compose(applySchedulers());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<ZcxgBean> selectDataByDept(Map<String, String> params) {
        return getInstance().create(RequestApi.class).selectDataByDept(params)
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

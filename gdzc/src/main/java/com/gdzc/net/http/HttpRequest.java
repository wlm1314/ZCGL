package com.gdzc.net.http;

import com.gdzc.base.BaseBean;
import com.gdzc.cfd.model.CfdBean;
import com.gdzc.flh.model.FlhBean;
import com.gdzc.login.model.LoginBean;
import com.gdzc.lydw.model.LydwBean;
import com.gdzc.net.consts.HttpConsts;
import com.gdzc.net.entity.HttpResult;
import com.gdzc.ry.model.RyBean;
import com.gdzc.syfx.model.SyfxBean;
import com.gdzc.utils.BaseLog;
import com.gdzc.zccx.model.ZccxBean;
import com.gdzc.zcdj.model.CchBean;
import com.gdzc.zcdj.model.ZcdjBean;
import com.gdzc.zcdj.model.ZcxgBean;
import com.gdzc.zcdj.model.ZcxgEditBean;
import com.gdzc.zctj.model.ZctjBean;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
        printParam(params);
        return getInstance().create(RequestApi.class).Login(params)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取分类号
     *
     * @param params
     * @return
     */
    public static Observable<FlhBean> GetFlh(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).GetFlh(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取资产登记页面
     *
     * @param params
     * @return
     */
    public static Observable<HttpResult<ZcdjBean>> GetTsxx(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).GetTsxx(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取单位
     *
     * @param params
     * @return
     */
    public static Observable<LydwBean> GetDwList(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).GetDwList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取使用方向
     *
     * @param params
     * @return
     */
    public static Observable<SyfxBean> GetMkList(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).GetMkList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 新增数据
     *
     * @param params
     * @return
     */
    public static Observable<BaseBean> AddNew(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).AddNew(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 更新数据
     *
     * @param params
     * @return
     */
    public static Observable<BaseBean> UpdateZj(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).UpdateZj(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 查询自己录入的数据
     *
     * @param params
     * @return
     */
    public static Observable<ZcxgBean> SearchMyData(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).SearchMyData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<ZcxgEditBean> SearchZJById(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).SearchZJById(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<CchBean> SelectCchByYqbh(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).SelectCchByYqbh(params)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<BaseBean> UpdateCchById(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).UpdateCchById(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<BaseBean> DeleteZjById(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).DeleteZjById(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<ZctjBean> SelectMyDataTotalByCategory(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).SelectMyDataTotalByCategory(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<ZcxgBean> SelectMyAllData(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).SelectMyAllData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<ZccxBean> SelectPoolById(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).SelectPoolById(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<ZcxgBean> selectMySonData(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).selectMySonData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<CfdBean> GetCfd(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).GetCfd(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param params
     * @return
     */
    public static Observable<RyBean> GetRy(Map<String, String> params) {
        printParam(params);
        return getInstance().create(RequestApi.class).GetRy(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 打印请求参数
     *
     * @param params
     */
    private static void printParam(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        String param = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            System.out.println(entry.getKey() + "--->" + entry.getValue());
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (sb.length() > 0)
            param = sb.toString().substring(0, sb.length() - 1);
        else
            param = sb.toString();
        BaseLog.i("RequestParam---------->>>" + "&" + param);
    }

}

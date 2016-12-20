package com.gdzc.net;

import com.gdzc.base.App;
import com.gdzc.base.BaseBean;
import com.gdzc.utils.BaseLog;
import com.gdzc.utils.NetStateUtils;
import com.gdzc.utils.Utils;
import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by 王少岩 on 2016/11/1.
 */

/**
 * 封装Subcriber
 *
 * @param <T>
 */
public class RetrofitSubscriber<T extends BaseBean> extends Subscriber<T> {
    final Action1<? super T> onNext;//接口成功返回处理动作
    final Action1<Throwable> onError;//接口失败返回处理动作

    public RetrofitSubscriber(Action1<? super T> onNext, Action1<Throwable> onError) {
        this.onNext = onNext;
        this.onError = onError;
    }

    public RetrofitSubscriber(Action1<? super T> onNext) {
        this.onNext = onNext;
        this.onError = null;
    }

    @Override
    public void onStart() {
        Utils.showLoading(App.getAppContext().getCurrentActivity());
        if (!NetStateUtils.isNetworkConnected(App.getAppContext())) {
            Utils.showToast(App.getAppContext(), "请检查网络是否连接");
        }
    }

    @Override
    public void onCompleted() {
        Utils.hideLoading();
    }

    @Override
    public void onError(Throwable e) {
        Utils.hideLoading();
        if (e instanceof SocketTimeoutException) {
            Utils.showToast(App.getAppContext(), "请求超时,请重试");
        } else if (e instanceof JsonSyntaxException) {
            Utils.showToast(App.getAppContext(), "数据解析错误");
            BaseLog.e("数据解析错误:::" + e.getMessage());
        }
        if (onError != null) onError.call(e);
    }

    @Override
    public void onNext(T t) {
        BaseLog.i(t.toString());
        if (t.status.isSuccess())
            onNext.call(t);
        else
            Utils.showToast(App.getAppContext(), t.status.msg);
    }
}

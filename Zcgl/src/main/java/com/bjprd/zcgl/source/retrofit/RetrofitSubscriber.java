package com.bjprd.zcgl.source.retrofit;

import android.app.Activity;

import com.bjprd.zcgl.utils.BaseLog;
import com.bjprd.zcgl.utils.NetStateUtils;
import com.bjprd.zcgl.utils.Utils;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by 王少岩 on 2016/11/1.
 */

public class RetrofitSubscriber<T extends BaseBean> extends Subscriber<T> {
    final Activity mActivity;
    final Action1<? super T> onNext;
    final Action1<Throwable> onError;

    public RetrofitSubscriber(Activity activity, Action1<? super T> onNext, Action1<Throwable> onError) {
        mActivity = activity;
        this.onNext = onNext;
        this.onError = onError;
    }

    public RetrofitSubscriber(Activity activity, Action1<? super T> onNext) {
        mActivity = activity;
        this.onNext = onNext;
        this.onError = null;
    }

    @Override
    public void onStart() {
        Utils.INSTANCE.showLoading(mActivity);
        if (!NetStateUtils.INSTANCE.isNetworkConnected(mActivity)) {
            Utils.INSTANCE.showToast(mActivity, "请检查网络是否连接");
        }
    }

    @Override
    public void onCompleted() {
        Utils.INSTANCE.hideLoading();
    }

    @Override
    public void onError(Throwable e) {
        Utils.INSTANCE.hideLoading();
        if (onError != null) onError.call(e);
    }

    @Override
    public void onNext(T t) {
        BaseLog.INSTANCE.i(t.toString());
        if (t.status.isSuccess())
            onNext.call(t);
        else
            Utils.INSTANCE.showToast(mActivity, t.status.msg);
    }
}

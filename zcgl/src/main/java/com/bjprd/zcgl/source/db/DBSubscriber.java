package com.bjprd.zcgl.source.db;

import android.app.Activity;

import com.bjprd.zcgl.utils.Utils;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by 王少岩 on 2016/9/28.
 */

public class DBSubscriber<T> extends Subscriber<T> {

    final Activity mActivity;
    final Action1<? super T> onNext;
    final Action1<Throwable> onError;

    public DBSubscriber(Activity activity, Action1<? super T> onNext) {
        this.mActivity = activity;
        this.onNext = onNext;
        this.onError = null;
    }

    public DBSubscriber(Activity activity, Action1<? super T> onNext, Action1<Throwable> onError) {
        mActivity = activity;
        this.onNext = onNext;
        this.onError = onError;
    }

    @Override
    public void onStart() {
        Utils.INSTANCE.showLoading(mActivity);
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
        onNext.call(t);
    }
}

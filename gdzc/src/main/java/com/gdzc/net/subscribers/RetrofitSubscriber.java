package com.gdzc.net.subscribers;

import com.gdzc.app.App;
import com.gdzc.base.BaseBean;
import com.gdzc.net.progress.ProgressCancelListener;
import com.gdzc.net.progress.ProgressDialogHandler;
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
public class RetrofitSubscriber<T extends BaseBean> extends Subscriber<T> implements ProgressCancelListener {
    final Action1<? super T> onNext;//接口成功返回处理动作
    final Action1<Throwable> onError;//接口失败返回处理动作

    private ProgressDialogHandler mProgressDialogHandler;

    public RetrofitSubscriber(Action1<? super T> onNext, Action1<Throwable> onError) {
        this.onNext = onNext;
        this.onError = onError;

        mProgressDialogHandler = new ProgressDialogHandler(this, true);
    }

    public RetrofitSubscriber(Action1<? super T> onNext) {
        this.onNext = onNext;
        this.onError = null;
        mProgressDialogHandler = new ProgressDialogHandler(this, true);
    }

    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
        if (!NetStateUtils.isNetworkConnected(App.getAppContext())) {
            Utils.showToast("请检查网络是否连接");
        }
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Utils.showToast("请求超时,请重试");
        } else if (e instanceof JsonSyntaxException) {
            Utils.showToast("数据解析错误");
        }
        if (onError != null) onError.call(e);
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        if (t.status.isSuccess())
            onNext.call(t);
        else
            Utils.showToast(t.status.msg);
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}

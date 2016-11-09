package com.bjprd.zcgl.login;

import android.app.Activity;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.binding.base.ViewModel;
import com.binding.command.ReplyCommand;
import com.bjprd.zcgl.App;
import com.bjprd.zcgl.R;
import com.bjprd.zcgl.main.MainActivity;
import com.bjprd.zcgl.source.db.DBSubscriber;
import com.bjprd.zcgl.source.db.LoginDao;
import com.bjprd.zcgl.source.db.bean.LoginBean;
import com.bjprd.zcgl.utils.NavigateUtils;
import com.bjprd.zcgl.utils.SPUtils;
import com.bjprd.zcgl.utils.Utils;

import java.sql.SQLException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 王少岩 on 2016/10/20.
 */

public class LoginViewModel implements ViewModel {
    private Activity mActivity;

    public final ObservableField<String> username = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();

    public LoginViewModel(Activity activity) {
        mActivity = activity;
        username.set(SPUtils.getUserAccount());
        password.set(SPUtils.getUserPassword());
    }

    public ReplyCommand loginCommand = new ReplyCommand(() -> {
        if (TextUtils.isEmpty(username.get())) {
            Utils.showToast(App.getAppContext(), App.getAppContext().getString(R.string.empty_username));
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            Utils.showToast(App.getAppContext(), App.getAppContext().getString(R.string.empty_password));
            return;
        }

        //根据输入的用户名和密码查询数据库
        login().subscribe(new DBSubscriber<>(mActivity, bean -> {
            if (bean != null) {
                SPUtils.setLoginStatus(true);
                SPUtils.setUserAccount(bean.getUserName());
                SPUtils.setUserPassword(bean.getPassWord());
                NavigateUtils.startActivity(mActivity, MainActivity.class);
                mActivity.finish();
            } else
                Utils.showToast(mActivity, "登录失败");
        }));
    });

    private Observable<LoginBean> login() {
        return Observable.create(new Observable.OnSubscribe<LoginBean>() {
            @Override
            public void call(Subscriber<? super LoginBean> subscriber) {
                try {
                    LoginBean bean = new LoginDao(mActivity).getData(username.get(), password.get());
                    subscriber.onNext(bean);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

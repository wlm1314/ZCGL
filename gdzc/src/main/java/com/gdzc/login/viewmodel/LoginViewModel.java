package com.gdzc.login.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.binding.command.ReplyCommand;
import com.gdzc.app.App;
import com.gdzc.login.model.LoginBean;
import com.gdzc.main.view.MainActivity;
import com.gdzc.net.http.HttpParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.ProgressSubscriber;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.SPUtils;
import com.gdzc.utils.Utils;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class LoginViewModel {
    public final ObservableField<String> username = new ObservableField();
    public final ObservableField<String> password = new ObservableField();

    public ReplyCommand loginCommand = new ReplyCommand(() -> {
        if (TextUtils.isEmpty(username.get())) {
            Utils.showToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            Utils.showToast("请输入密码");
            return;
        }

        HttpRequest.Login(HttpParams.login(username.get(), password.get()))
                .subscribe(new ProgressSubscriber<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        SPUtils.onLogin(loginBean.id, loginBean.username, loginBean.xingming, loginBean.dwbh);
                        Utils.showToast("登录成功");
                        NavigateUtils.startActivity(App.getAppContext().getCurrentActivity(), MainActivity.class);
                        App.getAppContext().getCurrentActivity().finish();
                    }
                });
    });
}

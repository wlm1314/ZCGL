package com.gdzc.login.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.binding.command.ReplyCommand;
import com.gdzc.base.App;
import com.gdzc.main.view.MainActivity;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
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
            Utils.showToast(App.getAppContext(), "请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            Utils.showToast(App.getAppContext(), "请输入密码");
            return;
        }

        HttpRequest.Login(HttpPostParams.login(username.get(), password.get()))
                .subscribe(new RetrofitSubscriber<>(loginBean -> {
                    SPUtils.onLogin(username.get(), loginBean.data.dwbh);
                    Utils.showToast(App.getAppContext(), "登录成功");
                    NavigateUtils.startActivity(App.getAppContext().getCurrentActivity(), MainActivity.class);
                    App.getAppContext().getCurrentActivity().finish();
                }));
    });
}

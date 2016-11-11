package com.bjprd.zcgl.login;

import android.app.Activity;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.binding.base.ViewModel;
import com.binding.command.ReplyCommand;
import com.bjprd.zcgl.App;
import com.bjprd.zcgl.R;
import com.bjprd.zcgl.main.MainActivity;
import com.bjprd.zcgl.source.DataManager;
import com.bjprd.zcgl.source.db.DBSubscriber;
import com.bjprd.zcgl.utils.NavigateUtils;
import com.bjprd.zcgl.utils.SPUtils;
import com.bjprd.zcgl.utils.Utils;

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
        DataManager.login(username.get(), password.get()).subscribe(new DBSubscriber<>(mActivity, status -> {
            if (status) {
                SPUtils.setLoginStatus(true);
                SPUtils.setUserAccount(username.get());
                SPUtils.setUserPassword(password.get());
                NavigateUtils.startActivity(mActivity, MainActivity.class);
                mActivity.finish();
            } else
                Utils.showToast(mActivity, "登录失败");
        }));
    });
}

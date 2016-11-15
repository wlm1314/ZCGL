package com.bjprd.zcgl.main;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;

import com.binding.command.ReplyCommand;
import com.bjprd.zcgl.App;
import com.bjprd.zcgl.login.LoginActivity;
import com.bjprd.zcgl.utils.SPUtils;
import com.bjprd.zcgl.zcbg.ZcbgActivity;
import com.bjprd.zcgl.zccx.ZccxActivity;
import com.bjprd.zcgl.zcdj.ZcdjActivity;
import com.bjprd.zcgl.zcqc.ZcqcActivity;

/**
 * Created by 王少岩 on 2016/10/19.
 */

public class MainViewModel {
    private Activity mActivity;

    public final ObservableField<String> username = new ObservableField<>();
    public final ObservableField<String> company = new ObservableField<>();

    public ReplyCommand logoutCommon = new ReplyCommand(() -> {
        SPUtils.setLoginStatus(false);
        mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
        mActivity.finish();
    });

    public ReplyCommand zcdjCommon = new ReplyCommand(() -> {
        mActivity.startActivity(new Intent(mActivity, ZcdjActivity.class));
    });

    public ReplyCommand zccxCommon = new ReplyCommand(() -> {
        mActivity.startActivity(new Intent(mActivity, ZccxActivity.class));
    });

    public ReplyCommand zcbgCommon = new ReplyCommand(() -> {
        mActivity.startActivity(new Intent(mActivity, ZcbgActivity.class));
    });

    public ReplyCommand zcqcCommon = new ReplyCommand(() -> {
        mActivity.startActivity(new Intent(mActivity, ZcqcActivity.class));
    });

    public MainViewModel() {
        this.mActivity = App.getAppContext().getCurrentActivity();
        username.set(SPUtils.getUserAccount());
        company.set(SPUtils.getUserAccount());
    }
}

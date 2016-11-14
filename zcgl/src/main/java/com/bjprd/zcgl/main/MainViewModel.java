package com.bjprd.zcgl.main;

import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;

import com.binding.command.ReplyCommand;
import com.bjprd.zcgl.login.LoginActivity;
import com.bjprd.zcgl.utils.SPUtils;
import com.bjprd.zcgl.zcdj.ZcdjActivity;

/**
 * Created by 王少岩 on 2016/10/19.
 */

public class MainViewModel {
    private AppCompatActivity mActivity;

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
    });

    public ReplyCommand zcbgCommon = new ReplyCommand(() -> {
    });

    public ReplyCommand zcqcCommon = new ReplyCommand(() -> {
    });

    public MainViewModel(AppCompatActivity activity) {
        this.mActivity = activity;
        username.set(SPUtils.getUserAccount());
        company.set(SPUtils.getUserAccount());
    }
}

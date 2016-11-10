package com.bjprd.zcgl.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bjprd.zcgl.R;
import com.bjprd.zcgl.base.BaseActivity;
import com.bjprd.zcgl.databinding.ActivityLoginBinding;

/**
 * Created by 王少岩 on 2016/10/19.
 */

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("登录");
        mBinding.setViewModel(new LoginViewModel(this));
    }

    @Override
    public void initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

}

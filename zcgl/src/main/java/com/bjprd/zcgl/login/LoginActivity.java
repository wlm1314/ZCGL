package com.bjprd.zcgl.login;

import com.bjprd.zcgl.R;
import com.bjprd.zcgl.base.BaseActivity;
import com.bjprd.zcgl.databinding.ActivityLoginBinding;

/**
 * Created by 王少岩 on 2016/10/19.
 */

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setViewModel() {
        getMBinding().setViewModel(new LoginViewModel(this));
    }

    @Override
    protected void init() {

    }
}

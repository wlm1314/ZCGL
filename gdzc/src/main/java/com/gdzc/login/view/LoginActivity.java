package com.gdzc.login.view;

import com.gdzc.R;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityLoginBinding;
import com.gdzc.login.viewmodel.LoginViewModel;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setViewModel() {
        mBinding.setViewModel(new LoginViewModel());
    }
}

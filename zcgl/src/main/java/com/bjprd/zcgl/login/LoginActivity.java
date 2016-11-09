package com.bjprd.zcgl.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bjprd.zcgl.R;
import com.bjprd.zcgl.base.AppBarViewModel;
import com.bjprd.zcgl.databinding.ActivityLoginBinding;

/**
 * Created by 王少岩 on 2016/10/19.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        AppBarViewModel appBarViewModel = new AppBarViewModel(this);
        appBarViewModel.setTitle("登录");
        binding.setAppbar(appBarViewModel);
        binding.setVariable(com.bjprd.zcgl.BR.viewModel, new LoginViewModel(this));
    }

}

package com.gdzc.splash.view;

import com.gdzc.R;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivitySplashBinding;
import com.gdzc.splash.viewmodel.SplashViewModel;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setViewModel() {
        mBinding.setViewModel(new SplashViewModel());
    }
}

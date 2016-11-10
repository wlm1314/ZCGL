package com.bjprd.zcgl.splash;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;

import com.bjprd.zcgl.R;
import com.bjprd.zcgl.base.BaseActivity;
import com.bjprd.zcgl.databinding.ActivitySplashBinding;

/**
 * Created by 王少岩 on 2016/8/5.
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.setViewModel(new SplashViewModel(this));
    }

    @Override
    public void initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    //屏蔽返回键的代码:
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_HOME:
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_CALL:
            case KeyEvent.KEYCODE_SYM:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_STAR:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

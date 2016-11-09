package com.bjprd.zcgl.splash;

import android.app.Activity;
import android.databinding.ObservableField;
import android.os.CountDownTimer;

import com.binding.base.ViewModel;
import com.binding.command.ReplyCommand;
import com.bjprd.zcgl.login.LoginActivity;
import com.bjprd.zcgl.main.MainActivity;
import com.bjprd.zcgl.utils.NavigateUtils;
import com.bjprd.zcgl.utils.SPUtils;

/**
 * Created by 王少岩 on 2016/10/19.
 */

public class SplashViewModel implements ViewModel {
    private Activity mActivity;
    private MyCount mCount;
    public final ObservableField<String> seconds = new ObservableField<>();

    public SplashViewModel(Activity activity) {
        this.mActivity = activity;
        mCount = new MyCount(3000, 1000);
        mCount.start();
    }

    public ReplyCommand clickCommand = new ReplyCommand(() -> {
        mCount.cancel();
        if (SPUtils.isLogin())
            startMainActivity();
        else
            startLoginActivity();
    });

    private void startMainActivity() {
        NavigateUtils.startActivity(mActivity, MainActivity.class);
        mActivity.finish();
    }

    private void startLoginActivity() {
        NavigateUtils.startActivity(mActivity, LoginActivity.class);
        mActivity.finish();
    }

    public class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            seconds.set("跳过0s");
            if (SPUtils.isLogin())
                startMainActivity();
            else
                startLoginActivity();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            seconds.set("跳过" + millisUntilFinished / 1000 + "s");
        }
    }
}

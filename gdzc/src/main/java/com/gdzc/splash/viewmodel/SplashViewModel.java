package com.gdzc.splash.viewmodel;

import android.os.CountDownTimer;

import com.binding.base.ViewModel;
import com.gdzc.base.App;
import com.gdzc.login.view.LoginActivity;
import com.gdzc.main.view.MainActivity;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.SPUtils;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class SplashViewModel implements ViewModel {
    private MyCount mCount;

    public SplashViewModel() {
        mCount = new MyCount(3000, 1000);
        mCount.start();
    }

    public class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (SPUtils.getBoolean(SPUtils.kUser_login, false))
                NavigateUtils.startActivity(App.getAppContext().getCurrentActivity(), MainActivity.class);
            else
                NavigateUtils.startActivity(App.getAppContext().getCurrentActivity(), LoginActivity.class);
            App.getAppContext().getCurrentActivity().finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }
    }
}

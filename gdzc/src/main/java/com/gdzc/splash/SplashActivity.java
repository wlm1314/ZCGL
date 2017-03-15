package com.gdzc.splash;

import com.gdzc.R;
import com.gdzc.app.App;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivitySplashBinding;
import com.gdzc.login.view.LoginActivity;
import com.gdzc.main.view.MainActivity;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.RxHelper;
import com.gdzc.utils.SPUtils;

import rx.Subscriber;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    private boolean mIsSkip = false;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        mBinding.tagSkip.setTagClickListener((pos, text, mode) -> doSkip());
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        RxHelper.countdown(3)
                .compose(this.<Integer>bindToLife())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        doSkip();
                    }

                    @Override
                    public void onError(Throwable e) {
                        doSkip();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mBinding.tagSkip.setText("跳过 " + integer);
                    }
                });
    }

    private void doSkip() {
        if (!mIsSkip) {
            mIsSkip = true;
            if (SPUtils.getBoolean(SPUtils.kUser_login, false))
                NavigateUtils.startActivity(App.getAppContext().getCurrentActivity(), MainActivity.class);
            else
                NavigateUtils.startActivity(App.getAppContext().getCurrentActivity(), LoginActivity.class);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        // 不响应后退键
        return;
    }
}

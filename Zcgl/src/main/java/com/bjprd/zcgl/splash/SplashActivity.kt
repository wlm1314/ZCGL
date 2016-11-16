package com.bjprd.zcgl.splash

import android.view.KeyEvent

import com.bjprd.zcgl.R
import com.bjprd.zcgl.base.BaseActivity
import com.bjprd.zcgl.databinding.ActivitySplashBinding

/**
 * Created by 王少岩 on 2016/8/5.
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun setViewModel() {
        mBinding!!.viewModel = SplashViewModel()
    }

    override fun init() {

    }

    //屏蔽返回键的代码:
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_HOME, KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_CALL, KeyEvent.KEYCODE_SYM, KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_STAR -> return true
        }
        return super.onKeyDown(keyCode, event)
    }
}

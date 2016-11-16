package com.bjprd.zcgl.splash

import android.databinding.ObservableField
import android.os.CountDownTimer
import com.binding.base.ViewModel
import com.binding.command.ReplyCommand
import com.bjprd.zcgl.App
import com.bjprd.zcgl.login.LoginActivity
import com.bjprd.zcgl.main.MainActivity
import com.bjprd.zcgl.utils.NavigateUtils
import com.bjprd.zcgl.utils.SPUtils
import rx.functions.Action0

/**
 * Created by 王少岩 on 2016/10/19.
 */

class SplashViewModel() : ViewModel {
    private val mCount: MyCount
    val seconds = ObservableField<String>()

    init {
        mCount = MyCount(3000, 1000)
        mCount.start()
    }

    var clickCommand = ReplyCommand<Any>(Action0 {
        mCount.cancel()
        if (SPUtils.isLogin)
            startMainActivity()
        else
            startLoginActivity()
    })

    private fun startMainActivity() {
        NavigateUtils.startActivity(App.appContext?.currentActivity!!, MainActivity::class.java)
        App.appContext?.currentActivity?.finish()
    }

    private fun startLoginActivity() {
        NavigateUtils.startActivity(App.appContext?.currentActivity!!, LoginActivity::class.java)
        App.appContext?.currentActivity?.finish()
    }

    inner class MyCount(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            seconds.set("跳过0s")
            if (SPUtils.isLogin)
                startMainActivity()
            else
                startLoginActivity()
        }

        override fun onTick(millisUntilFinished: Long) {
            seconds.set("跳过" + millisUntilFinished / 1000 + "s")
        }
    }
}

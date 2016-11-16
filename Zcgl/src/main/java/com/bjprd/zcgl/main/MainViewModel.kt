package com.bjprd.zcgl.main

import android.app.Activity
import android.content.Intent
import android.databinding.ObservableField

import com.binding.command.ReplyCommand
import com.bjprd.zcgl.App
import com.bjprd.zcgl.login.LoginActivity
import com.bjprd.zcgl.utils.SPUtils
import com.bjprd.zcgl.zcbg.ZcbgActivity
import com.bjprd.zcgl.zccx.ZccxActivity
import com.bjprd.zcgl.zcdj.ZcdjActivity
import com.bjprd.zcgl.zcqc.ZcqcActivity
import rx.functions.Action0

/**
 * Created by 王少岩 on 2016/10/19.
 */

class MainViewModel {
    private val mActivity: Activity = App.appContext?.currentActivity!!

    val username = ObservableField<String>()
    val company = ObservableField<String>()

    var logoutCommon = ReplyCommand<Any>(Action0 {
        SPUtils.setLoginStatus(false)
        mActivity.startActivity(Intent(mActivity, LoginActivity::class.java))
        mActivity.finish()
    })

    var zcdjCommon = ReplyCommand<Any>(Action0 { mActivity.startActivity(Intent(mActivity, ZcdjActivity::class.java)) })

    var zccxCommon = ReplyCommand<Any>(Action0 { mActivity.startActivity(Intent(mActivity, ZccxActivity::class.java)) })

    var zcbgCommon = ReplyCommand<Any>(Action0 { mActivity.startActivity(Intent(mActivity, ZcbgActivity::class.java)) })

    var zcqcCommon = ReplyCommand<Any>(Action0 { mActivity.startActivity(Intent(mActivity, ZcqcActivity::class.java)) })

    init {
        username.set(SPUtils.userAccount)
        company.set(SPUtils.userAccount)
    }
}

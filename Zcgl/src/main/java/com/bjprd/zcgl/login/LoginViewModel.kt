package com.bjprd.zcgl.login

import android.app.Activity
import android.databinding.ObservableField
import android.text.TextUtils

import com.binding.base.ViewModel
import com.binding.command.ReplyCommand
import com.bjprd.zcgl.App
import com.bjprd.zcgl.R
import com.bjprd.zcgl.main.MainActivity
import com.bjprd.zcgl.source.DataManager
import com.bjprd.zcgl.source.db.DBSubscriber
import com.bjprd.zcgl.utils.NavigateUtils
import com.bjprd.zcgl.utils.SPUtils
import com.bjprd.zcgl.utils.Utils
import rx.functions.Action0
import rx.functions.Action1

/**
 * Created by 王少岩 on 2016/10/20.
 */

class LoginViewModel(private val mActivity: Activity) : ViewModel {

    val username = ObservableField<String>()
    val password = ObservableField<String>()

    init {
        username.set(SPUtils.getString(SPUtils.kUser_account))
        password.set(SPUtils.getString(SPUtils.kUser_password))
    }

    var loginCommand = ReplyCommand<Any>(Action0 {
        when {
            TextUtils.isEmpty(username.get().trim()) -> Utils.showToast(App.appContext!!, App.appContext!!.getString(R.string.empty_username))
            TextUtils.isEmpty(password.get().trim()) -> Utils.showToast(App.appContext!!, App.appContext!!.getString(R.string.empty_password))
            else ->
                //根据输入的用户名和密码查询数据库
                DataManager.login(username.get(), password.get()).subscribe(DBSubscriber<Boolean>(mActivity, Action1{ status ->
                    if (status!!) {
                        SPUtils.onLogin("", username.get(), password.get(), true)
                        NavigateUtils.startActivity(mActivity, MainActivity::class.java)
                        mActivity.finish()
                    } else
                        Utils.showToast(mActivity, "登录失败")
                }))
        }


    })
}

package com.bjprd.zcgl.utils


import com.bjprd.zcgl.App

/**
 * Created by 王少岩 on 2016/8/22.
 */
object SPUtils {
    val kUser_login = "login_flag"
    val kUser_token = "token"
    val kUser_account = "account"
    val kUser_password = "password"
    val kUser_remember = "remember"

    val isLogin: Boolean
        get() = App.appContext!!.userPreference!!.getBoolean(kUser_login, false)

    fun setLoginStatus(isLogin: Boolean) {
        App.appContext!!.userPreference!!.edit().putBoolean(kUser_login, isLogin).commit()
    }

    var userToken: String
        get() = App.appContext!!.userPreference!!.getString(kUser_token, "")
        set(userToken) {
            App.appContext!!.userPreference!!.edit().putString(kUser_token, userToken).commit()
        }

    var userAccount: String
        get() = App.appContext!!.userPreference!!.getString(kUser_account, "")
        set(account) {
            App.appContext!!.userPreference!!.edit().putString(kUser_account, account).commit()
        }

    var userPassword: String
        get() = App.appContext!!.userPreference!!.getString(kUser_password, "")
        set(password) {
            App.appContext!!.userPreference!!.edit().putString(kUser_password, password).commit()
        }

    var isRemember: Boolean
        get() = App.appContext!!.userPreference!!.getBoolean(kUser_remember, false)
        set(remember) {
            App.appContext!!.userPreference!!.edit().putBoolean(kUser_remember, remember).commit()
        }

    /**
     * 用户登录，保持必要的数据
     */
    fun onLogin(token: String, account: String, password: String, remember: Boolean) {
        userToken = token
        userAccount = account
        userPassword = password
        isRemember = remember
        setLoginStatus(true)
    }
}

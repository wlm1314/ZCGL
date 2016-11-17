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

    /**
     * 用户登录，保持必要的数据
     */
    fun onLogin(token: String, account: String, password: String, remember: Boolean) {
        save(kUser_token, token)
        save(kUser_account, account)
        save(kUser_password, password)
        save(kUser_remember, remember)
        save(kUser_login, true)
    }

    fun save(key: String, value: Any) {
        val editor = App.appContext!!.userPreference!!.edit()
        when (value) {
            is Int -> editor.putInt(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is String -> editor.putString(key, value)
            else -> throw Exception("not supported type")
        }
        editor.apply()
    }

    fun getString(key: String) :String = App.appContext!!.userPreference!!.getString(key, "")
    fun getInt(key: String) :Int = App.appContext!!.userPreference!!.getInt(key, 0)
    fun getBoolean(key: String) :Boolean = App.appContext!!.userPreference!!.getBoolean(key, false)
}

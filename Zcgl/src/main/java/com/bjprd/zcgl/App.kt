package com.bjprd.zcgl

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.bjprd.zcgl.utils.DB_Copy

/**
 * Created by 王少岩 on 2016/8/16.
 */
class App : Application() {
    var preferences: SharedPreferences? = null
        private set
    var userPreference: SharedPreferences? = null
        private set
    var currentActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()
        appContext = this
        preferences = openPrefences()
        userPreference = openUserPrefences()

        /**
         * 复制数据库
         */
        copyDB()
    }

    fun openPrefences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    fun openUserPrefences(): SharedPreferences = getSharedPreferences("gdzc_user_pref", Context.MODE_PRIVATE)

    /**
     * 复制数据库
     */
    private fun copyDB() {
        val db_copy = DB_Copy(this)
        db_copy.copyDataBase()
    }

    companion object {
        var appContext: App? = null
            private set
    }
}

package com.bjprd.zcgl.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 * Created by 王少岩 on 2016/10/20.
 */

object NavigateUtils {

    /**
     * 普通跳转
     * @param activity
     * *
     * @param activityClass
     */
    fun startActivity(activity: Activity, activityClass: Class<out Activity>) {
        val intent = Intent(activity, activityClass)
        activity.startActivity(intent)
    }

    fun startActivity(activity: Activity, activityClass: Class<out Activity>, bundle: Bundle) {
        val intent = Intent(activity, activityClass)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }

    /**
     * 带返回值的跳转
     * @param activity
     * *
     * @param activityClass
     * *
     * @param requestCode
     */
    fun startActivityForResult(activity: Activity, activityClass: Class<out Activity>, requestCode: Int) {
        val intent = Intent(activity, activityClass)
        activity.startActivityForResult(intent, requestCode)
    }

    fun startActivityForResult(activity: Activity, activityClass: Class<out Activity>, requestCode: Int, bundle: Bundle) {
        val intent = Intent(activity, activityClass)
        intent.putExtras(bundle)
        activity.startActivityForResult(intent, requestCode)
    }
}

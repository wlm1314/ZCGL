package com.bjprd.zcgl.utils

import android.util.Log

import com.bjprd.zcgl.BuildConfig

object BaseLog {

    var TAG_Prefix = "BASEUI-"
    var TAG_HTTP = TAG_Prefix + "HTTP"
    var TAG_DEBUG = TAG_Prefix + "DEBUG"
    var TAG_INFO = TAG_Prefix + "INFO"
    var TAG_ERROR = TAG_Prefix + "ERROR"

    var ON_ALL = BuildConfig.DEBUG

    var ON_DEBUG = true
    var ON_INFO = true
    var ON_ERROR = true

    fun d(msg: Any) {
        if (!ON_ALL) return
        if (ON_DEBUG) {
            Log.d(TAG_DEBUG, " --- " + msg + "")
        }
    }

    fun i(msg: Any) {
        if (!ON_ALL) return
        if (ON_INFO) {
            Log.i(TAG_INFO, " --- " + msg + "")
        }
    }

    fun e(msg: Any) {
        if (!ON_ALL) return
        if (ON_ERROR) {
            Log.e(TAG_ERROR, " --- " + msg + "")
        }
    }
}

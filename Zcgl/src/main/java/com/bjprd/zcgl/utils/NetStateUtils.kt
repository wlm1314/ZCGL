package com.bjprd.zcgl.utils

import android.content.Context
import android.net.ConnectivityManager

object NetStateUtils {
    //判断是否有网络连接
    fun isNetworkConnected(context: Context?): Boolean {
        val mConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfo = mConnectivityManager?.activeNetworkInfo
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable
        } else
            return false
    }

    //判断wifi是否可用
    fun isWifiConnected(context: Context?): Boolean {
        val mConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mWiFiNetworkInfo = mConnectivityManager?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isAvailable
        } else
            return false
    }

    //判断MOBILE网络是否可用
    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable
            }
        }
        return false
    }

    //获取当前网络连接类型信息
    fun getConnectedType(context: Context?): Int {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null && mNetworkInfo.isAvailable) {
                return mNetworkInfo.type
            }
        }
        return -1
    }
}

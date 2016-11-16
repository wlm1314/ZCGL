package com.bjprd.zcgl.base

import android.app.Activity
import android.databinding.ObservableField
import android.text.TextUtils
import android.view.View

import com.binding.command.ReplyCommand
import com.bjprd.zcgl.App
import com.bjprd.zcgl.R
import rx.functions.Action0

/**
 * Created by 王少岩 on 2016/10/31.
 */

class AppBarViewModel(str_title: String, showLeft: Boolean) {
    private val mActivity: Activity = App.appContext?.currentActivity!!
    val title = ObservableField<String>()
    val navigation = ObservableField<Int>()
    val logo = ObservableField<Int>()
    val isShow = ObservableField<Int>()

    var naviCommon = ReplyCommand<Action0>(Action0 { mActivity.finish() })

    init {
        setTitle(str_title)
        setNavigation(if (showLeft) R.mipmap.icon_back else 0)
        setLogo(0)
    }

    fun setTitle(t: String) {
        title.set(t)
        setToolBarShow(if (TextUtils.isEmpty(t)) false else true)
    }

    fun setNavigation(resId: Int) {
        navigation.set(resId)
    }

    fun setLogo(resId: Int) {
        logo.set(resId)
    }

    fun setToolBarShow(show: Boolean) {
        isShow.set(if (show) View.VISIBLE else View.GONE)
    }

}

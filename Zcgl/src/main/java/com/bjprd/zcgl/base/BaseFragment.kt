package com.bjprd.zcgl.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by 王少岩 on 2016/11/9.
 */

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    protected var mBinding: T? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate<T>(inflater!!, layoutId, container, false)
        setViewModel()
        return (mBinding as T).root
    }

    /**
     * 该抽象方法就是 onCreate中需要的layoutID

     * @return
     */
    protected abstract val layoutId: Int

    /**
     * 绑定ViewModel
     */
    protected abstract fun setViewModel()
}

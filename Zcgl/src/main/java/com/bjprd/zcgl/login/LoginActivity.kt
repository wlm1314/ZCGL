package com.bjprd.zcgl.login

import com.bjprd.zcgl.R
import com.bjprd.zcgl.base.BaseActivity
import com.bjprd.zcgl.databinding.ActivityLoginBinding

/**
 * Created by 王少岩 on 2016/10/19.
 */

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_login

    override fun setViewModel() {
        mBinding!!.viewModel = LoginViewModel(this)
    }

    override fun init() {

    }
}

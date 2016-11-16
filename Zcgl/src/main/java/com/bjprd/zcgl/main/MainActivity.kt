package com.bjprd.zcgl.main

import com.bjprd.zcgl.R
import com.bjprd.zcgl.base.AppBarViewModel
import com.bjprd.zcgl.base.BaseActivity
import com.bjprd.zcgl.databinding.ActivityMainBinding

/**
 * Created by 王少岩 on 2016/10/19.
 */

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun setViewModel() {
        mBinding!!.viewModel = MainViewModel()
        mBinding!!.appbar = AppBarViewModel("固定资产管理系统", false)
    }

    override fun init() {
        mBinding!!.drawerLayout.findViewById(R.id.tv_home).setOnClickListener { v -> mBinding!!.drawerLayout.closeDrawers() }
    }
}

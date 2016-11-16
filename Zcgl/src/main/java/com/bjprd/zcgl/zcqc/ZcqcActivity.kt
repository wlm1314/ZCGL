package com.bjprd.zcgl.zcqc

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.bjprd.zcgl.R
import com.bjprd.zcgl.base.AppBarViewModel
import com.bjprd.zcgl.base.BaseActivity
import com.bjprd.zcgl.databinding.ActivityZcdjBinding
import com.bjprd.zcgl.source.db.bean.TsxxBean
import com.bjprd.zcgl.widget.recycleview.BindingTool
import com.bjprd.zcgl.zcdj.TsxxViewModel
import java.util.*

/**
 * Created by 王少岩 on 2016/11/14.
 */

class ZcqcActivity : BaseActivity<ActivityZcdjBinding>() {
    private val layoutManager: LinearLayoutManager? = null
    internal var map = HashMap<String, BindingTool>()
    private val mList = ArrayList<TsxxBean>()

    override val layoutId: Int
        get() = R.layout.activity_zcdj

    override fun setViewModel() {
        mBinding!!.appbar = AppBarViewModel("资产清查", true)
        mBinding!!.viewModel = TsxxViewModel()
        setSupportActionBar(mBinding!!.root.findViewById(R.id.toolbar) as Toolbar)
    }

    override fun init() {
        initView()
        initData()
    }

    private fun initView() {
    }

    private fun initData() {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.meun, menu)
        menu.findItem(R.id.action_right).title = "确定"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_right -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

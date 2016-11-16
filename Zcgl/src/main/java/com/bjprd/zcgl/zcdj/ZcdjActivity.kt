package com.bjprd.zcgl.zcdj

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.bjprd.zcgl.BR
import com.bjprd.zcgl.R
import com.bjprd.zcgl.base.AppBarViewModel
import com.bjprd.zcgl.base.BaseActivity
import com.bjprd.zcgl.databinding.ActivityZcdjBinding
import com.bjprd.zcgl.source.DataManager
import com.bjprd.zcgl.source.db.DBSubscriber
import com.bjprd.zcgl.source.db.bean.TsxxBean
import com.bjprd.zcgl.utils.Utils
import com.bjprd.zcgl.widget.recycleview.BindingAdapter
import com.bjprd.zcgl.widget.recycleview.BindingTool
import java.util.*

/**
 * Created by 王少岩 on 2016/11/14.
 */

class ZcdjActivity : BaseActivity<ActivityZcdjBinding>() {
    private var layoutManager: LinearLayoutManager? = null
    private var mBindingTool: BindingTool? = null
    private val mList = ArrayList<TsxxBean>()

    override val layoutId: Int
        get() = R.layout.activity_zcdj

    override fun setViewModel() {
        mBinding!!.appbar = AppBarViewModel("资产登记", true)
        mBinding!!.viewModel = TsxxViewModel()
        setSupportActionBar(mBinding!!.root.findViewById(R.id.toolbar) as Toolbar)
    }

    override fun init() {
        initView()
        initData()
    }

    private fun initView() {
        layoutManager = LinearLayoutManager(this)
        layoutManager!!.orientation = LinearLayoutManager.VERTICAL
        mBinding!!.recycler.layoutManager = layoutManager
        mBinding!!.recycler.setHasFixedSize(true)
    }

    private fun initData() {
        mBindingTool = BindingTool(R.layout.item_zcdj, BR.data)
        DataManager.getZcdj().subscribe(DBSubscriber<List<TsxxBean>>(this) { list ->
            mList.addAll(list)
            val adapter = BindingAdapter(mBindingTool, mList)
            mBinding!!.recycler.adapter = adapter
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.meun, menu)
        menu.findItem(R.id.action_right).title = "保存"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_right -> for (bean in mList) {
                if (TsxxBean.isNull(bean.isNull) && TextUtils.isEmpty(bean.editText.get())) {
                    Utils.showToast(this, bean.tsnr)
                    break
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

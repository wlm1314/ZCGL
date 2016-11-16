package com.bjprd.zcgl.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View

import com.bjprd.zcgl.App
import com.bjprd.zcgl.utils.Utils

/**
 * Created by 王少岩 on 2016/11/9.
 */

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    protected var mBinding: T? = null
        private set
    private var mClickTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appContext?.currentActivity = this
        mBinding = DataBindingUtil.setContentView<T>(this, layoutId)
        setViewModel()
        init()
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

    /**
     * 初始化页面
     */
    protected abstract fun init()

    fun showSnackbar(view: View, text: CharSequence) {
        Utils.showSnackBar(view, text)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
            else ->
                //对没有处理的事件，交给父类来处理
                return super.onOptionsItemSelected(item)
        }

        return true
    }

    private val isRoot: Boolean
        get() = isTaskRoot || parent != null && parent.isTaskRoot

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {

        if (isRoot && event.keyCode == KeyEvent.KEYCODE_BACK) {
            val second = System.currentTimeMillis()
            if (second - mClickTime < EXIT_TIMEOUT) {
                finish()
                return true
            } else {
                mClickTime = second
                Utils.showToast(this, "再按一次返回键退出")
                return true
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    companion object {
        private val EXIT_TIMEOUT = 2500
    }
}

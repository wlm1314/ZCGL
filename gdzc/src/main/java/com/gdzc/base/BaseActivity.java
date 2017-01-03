package com.gdzc.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.gdzc.utils.Utils;

/**
 * Created by 王少岩 on 2016/11/9.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {
    protected T mBinding;
    private long mClickTime = 0l;
    private static int EXIT_TIMEOUT = 2500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppContext().setCurrentActivity(this);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        App.getAppContext().setCurrentBinding(mBinding);
        setViewModel();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.getAppContext().setCurrentActivity(this);
    }

    /**
     * 该抽象方法就是 onCreate中需要的layoutID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 绑定ViewModel
     */
    protected abstract void setViewModel();

    /**
     * 初始化view及data
     */
    protected void init() {
    }

    /**
     * Toast
     *
     * @param content
     */
    protected void showToast(String content) {
        Utils.showToast(content);
    }

    /**
     * 显示加载dialog
     */
    protected void showLoading() {
        Utils.showLoading(this);
    }

    /**
     * 隐藏加载dialog
     */
    protected void hideLoading() {
        Utils.hideLoading();
    }

    /**
     * 右上角功能菜单采用menu的形式
     * 此方法为右上角点击事件，需要注意加上setSupportActionBar(toolbar);
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                //对没有处理的事件，交给父类来处理
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    /**
     * 判断是否是栈底
     *
     * @return
     */
    private boolean isRoot() {
        return isTaskRoot() || (getParent() != null && getParent().isTaskRoot());
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (isRoot() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            long second = System.currentTimeMillis();
            if (second - mClickTime < EXIT_TIMEOUT) {
                finish();
                return true;
            } else {
                mClickTime = second;
                Utils.showToast("再按一次返回键退出");
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }


}

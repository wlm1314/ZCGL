package com.gdzc.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.gdzc.app.App;
import com.gdzc.utils.Utils;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by 王少岩 on 2016/11/9.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends RxAppCompatActivity {
    protected T mBinding;
    private long mClickTime = 0l;
    private static int EXIT_TIMEOUT = 2500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppContext().setCurrentActivity(this);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        initViews();
        updateViews(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.getAppContext().setCurrentActivity(this);
    }

    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    /**
     * 该抽象方法就是 onCreate中需要的layoutID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 更新视图控件
     */
    protected abstract void updateViews(boolean isRefresh);

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

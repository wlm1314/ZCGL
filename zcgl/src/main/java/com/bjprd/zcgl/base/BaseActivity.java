package com.bjprd.zcgl.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.bjprd.zcgl.utils.Utils;

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
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        setViewModel();
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

    public void showSnackbar(View view, CharSequence text) {
        Utils.showSnackBar(view, text);
    }

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

    private boolean isRoot() {
        return isTaskRoot() || (getParent() != null && getParent().isTaskRoot());
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (isRoot() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            long second = System.currentTimeMillis();
            if (second - mClickTime < EXIT_TIMEOUT) {
                finish();
                return true;
            } else {
                mClickTime = second;
                Utils.showToast(this, "再按一次返回键退出");
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}

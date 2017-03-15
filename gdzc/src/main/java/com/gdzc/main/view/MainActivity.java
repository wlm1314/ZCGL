package com.gdzc.main.view;

import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityMainBinding;
import com.gdzc.login.view.LoginActivity;
import com.gdzc.main.viewmodel.MainViewModel;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.SPUtils;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mBinding.setAppbar(new AppBar("固定资产管理系统", R.mipmap.icon_menu, () -> mBinding.drawerLayout.openDrawer(Gravity.START)));
        mBinding.setViewModel(new MainViewModel());
        mBinding.navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    mBinding.drawerLayout.closeDrawer(Gravity.START);
                    break;
                case R.id.nav_logout:
                    mBinding.drawerLayout.closeDrawer(Gravity.START);
                    SPUtils.onLoginOut();
                    NavigateUtils.startActivity(MainActivity.this, LoginActivity.class);
                    finish();
                    break;
            }
            return true;
        });
        ((TextView)mBinding.navView.getHeaderView(0).findViewById(R.id.name)).setText(SPUtils.getString(SPUtils.kUser_nickname, ""));
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mBinding.drawerLayout.isDrawerOpen(Gravity.START)) {
            mBinding.drawerLayout.closeDrawer(Gravity.START);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}

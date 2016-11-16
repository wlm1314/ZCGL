package com.bjprd.zcgl.main;

import com.bjprd.zcgl.R;
import com.bjprd.zcgl.base.AppBarViewModel;
import com.bjprd.zcgl.base.BaseActivity;
import com.bjprd.zcgl.databinding.ActivityMainBinding;

/**
 * Created by 王少岩 on 2016/10/19.
 */

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setViewModel() {
        getMBinding().setViewModel(new MainViewModel());
        getMBinding().setAppbar(new AppBarViewModel("固定资产管理系统", false));
    }

    @Override
    protected void init() {
        getMBinding().drawerLayout.findViewById(R.id.tv_home).setOnClickListener(v -> getMBinding().drawerLayout.closeDrawers());
    }
}

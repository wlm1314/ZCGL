package com.gdzc.main.view;

import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityMainBinding;
import com.gdzc.main.viewmodel.MainViewModel;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setViewModel() {
        mBinding.setAppbar(new AppBar("固定资产管理系统", false));
        mBinding.setViewModel(new MainViewModel());
    }

    @Override
    protected void init() {
        mBinding.getRoot().findViewById(R.id.tv_home).setOnClickListener(v -> mBinding.drawerLayout.closeDrawers());
    }
}

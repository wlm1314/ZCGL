package com.bjprd.zcgl.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bjprd.zcgl.R;
import com.bjprd.zcgl.base.BaseActivity;
import com.bjprd.zcgl.databinding.ActivityMainBinding;

/**
 * Created by 王少岩 on 2016/10/19.
 */

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("固定资产管理系统");
        mBinding.setViewModel(new MainViewModel(this));
    }

    @Override
    public void initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}

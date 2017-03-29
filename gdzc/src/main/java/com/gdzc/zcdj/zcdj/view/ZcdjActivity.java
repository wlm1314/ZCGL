package com.gdzc.zcdj.zcdj.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.common.viewpager.MyFragmentPagerAdapter;
import com.gdzc.databinding.ActivityZcdjBinding;
import com.gdzc.utils.NavBarUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 王少岩 on 2016/12/20.
 * 容器类，包含登记、修改
 */

public class ZcdjActivity extends BaseActivity<ActivityZcdjBinding> {
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj;
    }

    @Override
    protected void initViews() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("资产登记", true));
        List<String> list = new ArrayList<>();
        list.add("登记");
        list.add("修改");
        mFragments.add(new ZcdjFragment());
        mFragments.add(new ZcxgFragment());
        mBinding.viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), list, mFragments));
        NavBarUtils.setTabs(mBinding.magicIndicator, list, mBinding.viewPager);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        mFragments.get(mBinding.viewPager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
    }

}

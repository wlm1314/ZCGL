package com.gdzc.zccx.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.common.viewpager.MyFragmentPagerAdapter;
import com.gdzc.databinding.ActivityZccxBinding;
import com.gdzc.utils.NavBarUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王少岩 on 2017/1/19.
 */

public class ZccxActivity extends BaseActivity<ActivityZccxBinding> {
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zccx;
    }

    @Override
    protected void initViews() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        List<String> list = new ArrayList<>();
        list.add("个人资产");
        list.add("领用人");
        list.add("领用单位");
        list.add("其他");
        mBinding.setAppbar(new AppBar("资产查询", true));
        mFragments.add(new OtherFragment());
        mFragments.add(new OtherFragment());
        mFragments.add(new OtherFragment());
        mFragments.add(new OtherFragment());
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

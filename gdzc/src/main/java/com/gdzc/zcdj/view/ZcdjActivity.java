package com.gdzc.zcdj.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;

import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZcdjBinding;
import com.gdzc.utils.NavBarUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 王少岩 on 2016/12/20.
 */

public class ZcdjActivity extends BaseActivity<ActivityZcdjBinding> {
    private ZcdjFragment mZcdjFragment;
    private ZcxgFragment mZcxgFragment;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj;
    }

    @Override
    protected void setViewModel() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("资产登记", true));
    }

    @Override
    protected void init() {
        mZcdjFragment = new ZcdjFragment();
        mZcxgFragment = new ZcxgFragment();
        mFragments.add(mZcdjFragment);
        mFragments.add(mZcxgFragment);
        mBinding.viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        List<String> list = new ArrayList<>();
        list.add("登记");
        list.add("修改");
        NavBarUtils.setTabs(mBinding.magicIndicator, list, mBinding.viewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (mBinding.viewPager.getCurrentItem()) {
            case 0:
                mZcdjFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case 1:
                mZcxgFragment.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public final int COUNT = 2;
        private String[] titles = new String[]{"登记", "修改"};

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}

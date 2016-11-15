package com.bjprd.zcgl.zcqc;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bjprd.zcgl.R;
import com.bjprd.zcgl.base.AppBarViewModel;
import com.bjprd.zcgl.base.BaseActivity;
import com.bjprd.zcgl.databinding.ActivityZcdjBinding;
import com.bjprd.zcgl.source.db.bean.TsxxBean;
import com.bjprd.zcgl.widget.recycleview.BindingTool;
import com.bjprd.zcgl.zcdj.TsxxViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 王少岩 on 2016/11/14.
 */

public class ZcqcActivity extends BaseActivity<ActivityZcdjBinding> {
    private LinearLayoutManager layoutManager;
    HashMap<String, BindingTool> map = new HashMap<>();
    private List<TsxxBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj;
    }

    @Override
    protected void setViewModel() {
        mBinding.setAppbar(new AppBarViewModel("资产清查", true));
        mBinding.setViewModel(new TsxxViewModel(this));
        setSupportActionBar((Toolbar) mBinding.getRoot().findViewById(R.id.toolbar));
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initView() {
    }

    private void initData() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meun, menu);
        menu.findItem(R.id.action_right).setTitle("确定");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_right:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

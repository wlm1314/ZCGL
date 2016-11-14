package com.bjprd.zcgl.zcdj;

import android.support.v7.widget.LinearLayoutManager;

import com.bjprd.zcgl.BR;
import com.bjprd.zcgl.R;
import com.bjprd.zcgl.base.AppBarViewModel;
import com.bjprd.zcgl.base.BaseActivity;
import com.bjprd.zcgl.databinding.ActivityZcdjBinding;
import com.bjprd.zcgl.source.DataManager;
import com.bjprd.zcgl.source.db.DBSubscriber;
import com.bjprd.zcgl.source.db.bean.ZcdjBean;
import com.bjprd.zcgl.widget.recycleview.BindingAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 王少岩 on 2016/11/14.
 */

public class ZcdjActivity extends BaseActivity<ActivityZcdjBinding> {
    private LinearLayoutManager layoutManager;
    HashMap<String, BindingAdapter.BindingTool> map = new HashMap<>();
    private List<ZcdjBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj;
    }

    @Override
    protected void setViewModel() {
        mBinding.setAppbar(new AppBarViewModel(this, "资产登记", true));
        mBinding.setViewModel(new ZcdjViewModel(this));
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.rvZcdj.setLayoutManager(layoutManager);
        mBinding.rvZcdj.setHasFixedSize(true);
    }

    private void initData() {
        map.put(ZcdjBean.class.getSimpleName(), new BindingAdapter.BindingTool(R.layout.item_zcdj, BR.data));
        DataManager.getZcdj().subscribe(new DBSubscriber<>(this, list -> {
            mList.addAll(list);
            BindingAdapter<ZcdjBean> adapter = new BindingAdapter<>(map, mList);
            mBinding.rvZcdj.setAdapter(adapter);
        }));
    }


}

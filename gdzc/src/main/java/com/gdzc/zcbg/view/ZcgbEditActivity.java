package com.gdzc.zcbg.view;

import android.support.v7.widget.LinearLayoutManager;

import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZcdjEditBinding;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.zcbg.model.ZcbgBean;
import com.gdzc.zcdj.model.ZcdjBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王少岩 on 2017/1/5.
 */

public class ZcgbEditActivity extends BaseActivity<ActivityZcdjEditBinding> {
    private List<ZcdjBean.Zcdj> mList = new ArrayList<>();
    private BindingAdapter<ZcdjBean.Zcdj> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj_edit;
    }

    @Override
    protected void setViewModel() {
        mBinding.setAppbar(new AppBar("资产变更", true));
    }

    @Override
    protected void init() {
        initView();
        getData();
    }

    private void initView() {
        mBinding.rvZcbg.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvZcbg.setHasFixedSize(true);
        mAdapter = new BindingAdapter<>(new BindingTool(R.layout.adapter_zcdj_item, BR.data), mList);
        mBinding.rvZcbg.setAdapter(mAdapter);
    }

    private void getData() {
        ZcbgBean.ListBean zcbg = (ZcbgBean.ListBean) getIntent().getExtras().getSerializable("zcbg");
        HttpRequest.GetTsxx(HttpPostParams.paramGetTsxx(zcbg.分类号, zcbg.单价)).subscribe(new RetrofitSubscriber<>(zcdjBean -> {
        }));
    }
}

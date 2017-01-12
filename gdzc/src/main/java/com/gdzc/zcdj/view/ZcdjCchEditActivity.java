package com.gdzc.zcdj.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZcdjCchEditBinding;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.zcdj.model.CchBean;
import com.gdzc.zcdj.model.ZcxgBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by 王少岩 on 2017/1/5.
 */

public class ZcdjCchEditActivity extends BaseActivity<ActivityZcdjCchEditBinding> {
    private List<CchBean.Cch> mList = new ArrayList<>();
    private BindingAdapter<CchBean.Cch> mAdapter;
    private ZcxgBean.ListBean zcbg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj_cch_edit;
    }

    @Override
    protected void setViewModel() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("出厂号", true));
    }

    @Override
    protected void init() {
        initView();
        getData();
        setListener();
    }

    private void initView() {
        mBinding.rvCch.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvCch.setHasFixedSize(true);
        mAdapter = new BindingAdapter<>(new BindingTool(R.layout.adapter_cch_item, BR.data), mList);
        mBinding.rvCch.setAdapter(mAdapter);
    }

    private void setListener() {
    }

    private void getData() {
        String yqbh = getIntent().getExtras().getString("yqbh");
        HttpRequest.SelectCchByYqbh(HttpPostParams.paramselectCchByYqbh(yqbh))
                .subscribe(new RetrofitSubscriber<>(cchBean -> {
                    Observable.from(cchBean.data.list).subscribe(listBean -> mList.add(CchBean.Cch.castToCch(listBean)));
                    mAdapter.notifyDataSetChanged();
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
    }
}

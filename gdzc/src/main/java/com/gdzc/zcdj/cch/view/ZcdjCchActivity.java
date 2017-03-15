package com.gdzc.zcdj.cch.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZcdjCchBinding;
import com.gdzc.net.http.HttpParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.ProgressSubscriber;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.zcdj.cch.model.CchBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by 王少岩 on 2017/1/5.
 */

public class ZcdjCchActivity extends BaseActivity<ActivityZcdjCchBinding> {
    private List<CchBean.Cch> mList = new ArrayList<>();
    private BindingAdapter<CchBean.Cch> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj_cch;
    }


    @Override
    protected void initViews() {
        initView();
        getData();
        setListener();
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private void initView() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("出厂号", true));
        mBinding.rvCch.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvCch.setHasFixedSize(true);
        mAdapter = new BindingAdapter<>(new BindingTool(R.layout.adapter_cch_item, BR.data), mList);
        mBinding.rvCch.setAdapter(mAdapter);
    }

    private void setListener() {
        mAdapter.setItemClickLister((view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("Cch", mList.get(position));
            NavigateUtils.startActivityForResult(ZcdjCchActivity.this, ZcdjCchEditActivity.class, 1000, bundle);
        });

    }

    private void getData() {
        String yqbh = getIntent().getExtras().getString("yqbh");
        HttpRequest.SelectCchByYqbh(HttpParams.paramselectCchByYqbh(yqbh))
                .subscribe(new ProgressSubscriber<CchBean>() {
                    @Override
                    public void onNext(CchBean cchBean) {
                        Observable.from(cchBean.list).subscribe(listBean -> mList.add(CchBean.Cch.castToCch(listBean)));
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        mList.clear();
        getData();
    }
}

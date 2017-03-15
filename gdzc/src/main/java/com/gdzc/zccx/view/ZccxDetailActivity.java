package com.gdzc.zccx.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZcdjEditBinding;
import com.gdzc.net.http.HttpParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.RetrofitSubscriber;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.zccx.model.ZccxBean;
import com.gdzc.zcdj.zcdj.model.ZcxgBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by 王少岩 on 2017/1/5.
 */

public class ZccxDetailActivity extends BaseActivity<ActivityZcdjEditBinding> {
    private List<ZccxBean.ZccxDetail> mList = new ArrayList<>();
    private BindingAdapter<ZccxBean.ZccxDetail> mAdapter;
    private ZcxgBean.Zcxg zcxg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj_edit;
    }

    @Override
    protected void initViews() {
        initView();
        getData();
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private void initView() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("资产详情", true));
        mBinding.rvZcbg.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvZcbg.setHasFixedSize(true);
        mAdapter = new BindingAdapter<>(new BindingTool(R.layout.adapter_zccx_detail_item, BR.data), mList);
        mBinding.rvZcbg.setAdapter(mAdapter);
    }

    private void getData() {
        zcxg = (ZcxgBean.Zcxg) getIntent().getExtras().getSerializable("zcxg");
        HttpRequest.SelectPoolById(HttpParams.paramSelectPoolById(zcxg.id))
                .subscribe(new RetrofitSubscriber<>(zccxBean -> {
                    Observable.from(zccxBean.data.list).subscribe(dataBean -> mList.add(ZccxBean.ZccxDetail.castToZccx(dataBean)));
                    mAdapter.notifyDataSetChanged();
                }));
    }
}

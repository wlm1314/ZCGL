package com.gdzc.zccx.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZccxBinding;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.zccx.viewmodel.ZccxViewModel;
import com.gdzc.zcdj.zcdj.adapter.ZcxgAdapter;
import com.gdzc.zcdj.zcdj.model.ZcxgBean;
import com.pulltofresh.PullToRefreshBase;
import com.pulltofresh.extras.CustomNestedScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王少岩 on 2017/1/19.
 */

public class ZccxActivity extends BaseActivity<ActivityZccxBinding> {
    private ZcxgAdapter mAdapter;
    private List<ZcxgBean.Zcxg> mList = new ArrayList<>();
    private ZccxViewModel mViewModel;
    private int pageNo = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zccx;
    }

    @Override
    protected void initViews() {
        initView();
        setListener();
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private void initView() {
        mBinding.setAppbar(new AppBar("资产查询", true));
        mViewModel = new ZccxViewModel();
        mBinding.setViewModel(mViewModel);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setHasFixedSize(true);
        mAdapter = new ZcxgAdapter(mList);
        mAdapter.setDataType("zccx");
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    private void setListener() {
        mAdapter.setOnItemClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("zcxg", mList.get(position));
            NavigateUtils.startActivity(ZccxActivity.this, ZccxDetailActivity.class, bundle);
        });
        mBinding.ptrRv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<CustomNestedScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<CustomNestedScrollView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<CustomNestedScrollView> refreshView) {
                pageNo++;
                mViewModel.getData(pageNo);
            }
        });
    }

    public void setData(ZcxgBean data) {
        if (data.data.isFirstPage)
            mList.clear();
        if (data.data.isLastPage)
            mBinding.ptrRv.setMode(PullToRefreshBase.Mode.DISABLED);
        else
            mBinding.ptrRv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mList.addAll(data.data.list);
        mAdapter.notifyDataSetChanged();
        mBinding.tvMsg.setText("台件：" + data.total.totalCount + "      条数：" + data.total.totalRow + "     金额（元）：" + data.total.totalMoney);
        mBinding.tvMsg.setVisibility(View.VISIBLE);
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void complete() {
        mBinding.ptrRv.onRefreshComplete();
    }
}

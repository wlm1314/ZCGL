package com.gdzc.zcbg.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZcbgBinding;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.zcbg.adapter.ZcbgAdapter;
import com.gdzc.zcbg.model.ZcbgBean;
import com.gdzc.zcbg.viewmodel.ZcbgViewModel;
import com.pulltofresh.PullToRefreshBase;
import com.pulltofresh.extras.CustomNestedScrollView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 王少岩 on 2017/1/5.
 */

public class ZcbgActivity extends BaseActivity<ActivityZcbgBinding> {
    private ZcbgAdapter mAdapter;
    private List<ZcbgBean.ListBean> mList = new ArrayList<>();
    private ZcbgViewModel mViewModel;
    private int pageNo = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcbg;
    }

    @Override
    protected void setViewModel() {
        mBinding.setAppbar(new AppBar("资产变更", true));
        mViewModel = new ZcbgViewModel();
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        initView();
        setListener();
    }

    private void initView() {
        mBinding.rvZcbg.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvZcbg.setHasFixedSize(true);
        mAdapter = new ZcbgAdapter(new BindingTool(R.layout.adapter_zcbg_item, BR.data), mList);
        mBinding.rvZcbg.setAdapter(mAdapter);
    }

    private void setListener() {
        mBinding.pullView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<CustomNestedScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<CustomNestedScrollView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<CustomNestedScrollView> refreshView) {
                mViewModel.getData(pageNo++);
            }
        });
        mAdapter.setItemClickLister((view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("zcbg", mList.get(position));
            NavigateUtils.startActivityForResult(this, ZcgbEditActivity.class, 1000, bundle);
        });
    }

    public void setData(ZcbgBean.DataBean data) {
        if (data.isFirstPage)
            mList.clear();
        if (data.isLastPage)
            mBinding.pullView.setMode(PullToRefreshBase.Mode.DISABLED);
        else
            mBinding.pullView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mList.addAll(data.list);
        mAdapter.notifyDataSetChanged();
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == 1000) {
            boolean flag = data.getExtras().getBoolean("update");
            if (flag)
                mViewModel.getData(pageNo);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

package com.gdzc.zcdj.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.base.BaseFragment;
import com.gdzc.databinding.FragmentZcxgBinding;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.widget.recycleview.BindingTool;
import com.gdzc.zcdj.adapter.ZcbgAdapter;
import com.gdzc.zcdj.model.ZcxgBean;
import com.gdzc.zcdj.viewmodel.ZcxgViewModel;
import com.pulltofresh.PullToRefreshBase;
import com.pulltofresh.extras.CustomNestedScrollView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 王少岩 on 2017/1/12.
 */

public class ZcxgFragment extends BaseFragment<FragmentZcxgBinding> {
    private ZcbgAdapter mAdapter;
    private List<ZcxgBean.ListBean> mList = new ArrayList<>();
    private ZcxgViewModel mViewModel;
    private int pageNo = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zcxg;
    }

    @Override
    protected void setViewModel() {
        mViewModel = new ZcxgViewModel(this);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        initView();
        setListener();
    }

    private void initView() {
        mBinding.rvZcbg.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvZcbg.setHasFixedSize(true);
        mAdapter = new ZcbgAdapter(new BindingTool(R.layout.adapter_zcxg_item, BR.data), mList);
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
            ZcxgBean.ListBean bean = mList.get(position);
                startZcdjEditActivity(bean);
        });
    }

    private void startZcdjEditActivity(ZcxgBean.ListBean bean){
        Bundle bundle = new Bundle();
        bundle.putSerializable("zcbg", bean);
        NavigateUtils.startActivityForResult(getActivity(), ZcdjEditActivity.class, 1000, bundle);
    }

    public void setData(ZcxgBean.DataBean data) {
        if (data.isFirstPage)
            mList.clear();
        if (data.isLastPage)
            mBinding.pullView.setMode(PullToRefreshBase.Mode.DISABLED);
        else
            mBinding.pullView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mList.addAll(data.list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == 1000) {
            boolean flag = data.getExtras().getBoolean("update");
            if (flag)
                mViewModel.getData(pageNo);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
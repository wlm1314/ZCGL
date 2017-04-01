package com.gdzc.zccx.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gdzc.R;
import com.gdzc.base.BaseFragment;
import com.gdzc.databinding.FragmentZccxGrzcBinding;
import com.gdzc.net.http.HttpParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.ProgressSubscriber;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.zcdj.zcdj.adapter.ZcxgAdapter;
import com.gdzc.zcdj.zcdj.model.ZcxgBean;
import com.pulltofresh.PullToRefreshBase;
import com.pulltofresh.extras.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 王少岩 在 2017/3/29 创建了它
 */

public class GrzcFragment extends BaseFragment<FragmentZccxGrzcBinding> {
    private ZcxgAdapter mAdapter;
    private List<ZcxgBean.Zcxg> mZcxgList = new ArrayList<>();

    private int pageNo = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zccx_grzc;
    }

    @Override
    protected void setViewModel() {
    }

    @Override
    protected void init() {
        initView();
        setListener();
        getData(pageNo);
    }

    private void initView() {
        mAdapter = new ZcxgAdapter(mZcxgList);
        mAdapter.setDataType("zccx");
        mBinding.pullView.getRefreshableView().setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.pullView.getRefreshableView().setHasFixedSize(true);
        mBinding.pullView.getRefreshableView().setAdapter(mAdapter);
    }

    private void setListener() {
        mBinding.pullView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerViewEmptySupport>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerViewEmptySupport> refreshView) {
                pageNo = 1;
                getData(pageNo);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerViewEmptySupport> refreshView) {
                pageNo++;
                getData(pageNo);
            }
        });

        mAdapter.setOnItemClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("zcxg", mZcxgList.get(position));
            NavigateUtils.startActivity(getActivity(), ZccxDetailActivity.class, bundle);
        });
    }

    public void getData(int pageNo) {
        HttpRequest.underMyNameData(HttpParams.paramList(pageNo + ""))
                .subscribe(new ProgressSubscriber<ZcxgBean>() {
                    @Override
                    public void onNext(ZcxgBean data) {
                        mBinding.pullView.onRefreshComplete();
                        if (data.data.isFirstPage)
                            mZcxgList.clear();
                        if (data.data.isLastPage)
                            mBinding.pullView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        else
                            mBinding.pullView.setMode(PullToRefreshBase.Mode.BOTH);
                        mZcxgList.addAll(data.data.list);
                        mAdapter.notifyDataSetChanged();
                        if (mZcxgList.size() == 0)
                            mBinding.tvEmpty.setVisibility(View.VISIBLE);
                        else
                            mBinding.tvEmpty.setVisibility(View.GONE);

                        mBinding.tvMsg.setText("条数:" + data.total.totalRow + "  台件:" + data.total.totalCount + "  金额（万元）:" + data.total.totalMoney);
                        mBinding.tvMsg.setVisibility(View.VISIBLE);
                    }
                });
    }
}

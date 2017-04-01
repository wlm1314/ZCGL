package com.gdzc.zccx.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gdzc.R;
import com.gdzc.base.BaseFragment;
import com.gdzc.databinding.FragmentZccxLyrBinding;
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

public class LyrFragment extends BaseFragment<FragmentZccxLyrBinding> {
    private ZcxgAdapter mAdapter;
    private List<ZcxgBean.Zcxg> mZcxgList = new ArrayList<>();
    private int pageNo = 1;
    private String who = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zccx_lyr;
    }

    @Override
    protected void setViewModel() {

    }

    @Override
    protected void init() {
        initView();
        setListener();
        getData(who, pageNo);
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
                getData(who, pageNo);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerViewEmptySupport> refreshView) {
                pageNo++;
                getData(who, pageNo);
            }
        });

        mAdapter.setOnItemClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("zcxg", mZcxgList.get(position));
            NavigateUtils.startActivity(getActivity(), ZccxDetailActivity.class, bundle);
        });

        mBinding.tvSearch.setOnClickListener(v -> {
            pageNo = 1;
            who = mBinding.etPerson.getText().toString();
            getData(who, pageNo);
        });
    }

    public void getData(String name, int pageNo) {
        HttpRequest.recipientsWho(HttpParams.paramLyr(name, pageNo + ""))
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

package com.gdzc.zccx.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gdzc.R;
import com.gdzc.app.App;
import com.gdzc.base.BaseFragment;
import com.gdzc.databinding.FragmentZccxLydwBinding;
import com.gdzc.net.http.HttpParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.ProgressSubscriber;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.zcdj.mk.model.DwBean;
import com.gdzc.zcdj.mk.view.MKActivity;
import com.gdzc.zcdj.zcdj.adapter.ZcxgAdapter;
import com.gdzc.zcdj.zcdj.model.ZcxgBean;
import com.pulltofresh.PullToRefreshBase;
import com.pulltofresh.extras.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 王少岩 在 2017/3/29 创建了它
 */

public class LydwFragment extends BaseFragment<FragmentZccxLydwBinding> {
    private ZcxgAdapter mAdapter;
    private List<ZcxgBean.Zcxg> mZcxgList = new ArrayList<>();
    private int pageNo = 1;
    private String dwid = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zccx_lydw;
    }

    @Override
    protected void setViewModel() {

    }

    @Override
    protected void init() {
        initView();
        setListener();
        getData(dwid, pageNo);
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
                getData(dwid, pageNo);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerViewEmptySupport> refreshView) {
                pageNo++;
                getData(dwid, pageNo);
            }
        });

        mAdapter.setOnItemClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("zcxg", mZcxgList.get(position));
            NavigateUtils.startActivity(getActivity(), ZccxDetailActivity.class, bundle);
        });

        mBinding.tvDept.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("columName", "领用单位");
            bundle.putString("dwId", "");
            NavigateUtils.startActivityForResult(App.getAppContext().getCurrentActivity(), MKActivity.class, 1000, bundle);
        });

        mBinding.tvSearch.setOnClickListener(v -> {
            pageNo = 1;
            dwid = mBinding.tvDept.getText().toString();
            getData(dwid, pageNo);
        });
    }

    public void getData(String dwid, int pageNo) {
        HttpRequest.selectDataByDept(HttpParams.paramLydw(dwid, pageNo + ""))
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

                        mBinding.tvMsg.setText("台件：" + data.total.totalCount + "      条数：" + data.total.totalRow + "     金额（元）：" + data.total.totalMoney);
                        mBinding.tvMsg.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        DwBean.Dw mDw = (DwBean.Dw) data.getExtras().getSerializable("Mk");
        mBinding.tvDept.setText(mDw.dwName);
        dwid = mDw.dwId;
    }
}

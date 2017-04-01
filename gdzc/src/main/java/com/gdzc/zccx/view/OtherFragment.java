package com.gdzc.zccx.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gdzc.R;
import com.gdzc.base.BaseFragment;
import com.gdzc.databinding.FragmentZccxOtherBinding;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.Utils;
import com.gdzc.zccx.viewmodel.ZccxOtherViewModel;
import com.gdzc.zcdj.zcdj.adapter.ZcxgAdapter;
import com.gdzc.zcdj.zcdj.model.ZcxgBean;
import com.pulltofresh.PullToRefreshBase;
import com.pulltofresh.extras.CustomNestedScrollView;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 王少岩 在 2017/3/29 创建了它
 */

public class OtherFragment extends BaseFragment<FragmentZccxOtherBinding> {
    private ZcxgAdapter mAdapter;
    private List<ZcxgBean.Zcxg> mList = new ArrayList<>();
    private ZccxOtherViewModel mViewModel;
    private int pageNo = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zccx_other;
    }

    @Override
    protected void setViewModel() {
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        initView();
        setListener();
    }

    private void initView() {
        mViewModel = new ZccxOtherViewModel(this);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setHasFixedSize(true);
        mAdapter = new ZcxgAdapter(mList);
        mAdapter.setDataType("zccx");
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    private void setListener() {
        mAdapter.setOnItemClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("zcxg", mList.get(position));
            NavigateUtils.startActivity(getActivity(), ZccxDetailActivity.class, bundle);
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
        mBinding.ivScan.setOnClickListener(v -> NavigateUtils.startScanActivity(111));
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
        mBinding.tvMsg.setText("条数:" + data.total.totalRow + "  台件:" + data.total.totalCount + "  金额（万元）:" + data.total.totalMoney);
        mBinding.tvMsg.setVisibility(View.VISIBLE);
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void complete() {
        mBinding.ptrRv.onRefreshComplete();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 111) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                NavigateUtils.startScanActivity(111);
            } else {
                Utils.showToast("请在应用管理中打开“相机”访问权限！");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == 111) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    if (!TextUtils.isEmpty(result) && "SJRQD".contains(result.substring(result.length() - 1)))
                    mViewModel.zcbh.set(result);
//                    else
//                        Utils.showToast("请扫描有效的条形码");
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Utils.showToast("解析失败");
                }
            }
        }
    }
}

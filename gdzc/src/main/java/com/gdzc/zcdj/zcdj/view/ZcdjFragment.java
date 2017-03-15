package com.gdzc.zcdj.zcdj.view;

import android.content.Intent;

import com.gdzc.R;
import com.gdzc.base.BaseFragment;
import com.gdzc.common.recyclerview.InitRecyclerView;
import com.gdzc.databinding.FragmentZcdjBinding;
import com.gdzc.zcdj.zcdj.viewmodel.ZcdjViewModel;

/**
 * 王少岩 在 2017/1/12 创建了它
 */

public class ZcdjFragment extends BaseFragment<FragmentZcdjBinding> {
    private ZcdjViewModel mViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zcdj;
    }

    @Override
    protected void setViewModel() {
        mViewModel = new ZcdjViewModel();
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        InitRecyclerView.initLinearLayoutVERTICAL(getActivity(), mBinding.rvZcdj);
        mBinding.rvZcdj.setAdapter(mViewModel.mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }
}

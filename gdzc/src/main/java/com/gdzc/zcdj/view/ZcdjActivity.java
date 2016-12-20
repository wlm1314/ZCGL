package com.gdzc.zcdj.view;

import android.content.Intent;

import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZcdjBinding;
import com.gdzc.flh.model.FlhBean;
import com.gdzc.zcdj.viewmodel.ZcdjViewModel;

/**
 * Created by 王少岩 on 2016/12/20.
 */

public class ZcdjActivity extends BaseActivity<ActivityZcdjBinding> {
    private ZcdjViewModel mViewModel;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj;
    }

    @Override
    protected void setViewModel() {
        mBinding.setAppbar(new AppBar("资产登记", true));
        mViewModel = new ZcdjViewModel();
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode !=RESULT_OK) return;
        if (requestCode == 1000) {
            FlhBean.Flh flh = (FlhBean.Flh) data.getExtras().getSerializable("Flh");
            mViewModel.flh.set(flh.flh);
        }
    }
}

package com.gdzc.zcdj.view;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityZcdjBinding;
import com.gdzc.flh.model.FlhBean;
import com.gdzc.lydw.model.LydwBean;
import com.gdzc.syfx.model.SyfxBean;
import com.gdzc.utils.Utils;
import com.gdzc.zcdj.viewmodel.ZcdjViewModel;

import rx.Observable;

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
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("资产登记", true));
        mViewModel = new ZcdjViewModel();
        mBinding.setViewModel(mViewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meun, menu);
        menu.findItem(R.id.action_right).setTitle("保存");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Observable.from(mViewModel.mZcdjList)
                .filter(zcdj -> TextUtils.isEmpty(zcdj.editText.get()))
                .first()
                .subscribe(zcdj -> Utils.showToast(this, zcdj.tsnr));
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case 1000:
                FlhBean.Flh flh = (FlhBean.Flh) data.getExtras().getSerializable("Flh");
                mViewModel.flh.set(flh);
                break;
            case 1001:
                Observable.from(mViewModel.mZcdjList)
                        .filter(zcdj -> zcdj.columEng.equals("lydwh"))
                        .subscribe(zcdj -> zcdj.editText.set(((LydwBean.Lydw) data.getExtras().getSerializable("Lydw")).dwName));
                break;
            case 1002:
                Observable.from(mViewModel.mZcdjList)
                        .filter(zcdj -> zcdj.columEng.equals("syfx"))
                        .subscribe(zcdj -> zcdj.editText.set(((SyfxBean.Syfx) data.getExtras().getSerializable("Syfx")).校名称));
                break;
        }
    }
}

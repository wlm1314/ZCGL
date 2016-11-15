package com.bjprd.zcgl.zcdj;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.bjprd.zcgl.BR;
import com.bjprd.zcgl.R;
import com.bjprd.zcgl.base.AppBarViewModel;
import com.bjprd.zcgl.base.BaseActivity;
import com.bjprd.zcgl.databinding.ActivityZcdjBinding;
import com.bjprd.zcgl.source.DataManager;
import com.bjprd.zcgl.source.db.DBSubscriber;
import com.bjprd.zcgl.source.db.bean.TsxxBean;
import com.bjprd.zcgl.utils.Utils;
import com.bjprd.zcgl.widget.recycleview.BindingAdapter;
import com.bjprd.zcgl.widget.recycleview.BindingTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王少岩 on 2016/11/14.
 */

public class ZcdjActivity extends BaseActivity<ActivityZcdjBinding> {
    private LinearLayoutManager layoutManager;
    private BindingTool mBindingTool;
    private List<TsxxBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zcdj;
    }

    @Override
    protected void setViewModel() {
        mBinding.setAppbar(new AppBarViewModel("资产登记", true));
        mBinding.setViewModel(new TsxxViewModel(this));
        setSupportActionBar((Toolbar) mBinding.getRoot().findViewById(R.id.toolbar));
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.recycler.setLayoutManager(layoutManager);
        mBinding.recycler.setHasFixedSize(true);
    }

    private void initData() {
        mBindingTool = new BindingTool(R.layout.item_zcdj, BR.data);
        DataManager.getZcdj().subscribe(new DBSubscriber<>(this, list -> {
            mList.addAll(list);
            BindingAdapter adapter = new BindingAdapter(mBindingTool, mList);
            mBinding.recycler.setAdapter(adapter);
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meun, menu);
        menu.findItem(R.id.action_right).setTitle("保存");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_right:
                for (TsxxBean bean : mList) {
                    if (TsxxBean.isNull(bean.isNull) && TextUtils.isEmpty(bean.editText.get())) {
                        Utils.showToast(this, bean.tsnr);
                        break;
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

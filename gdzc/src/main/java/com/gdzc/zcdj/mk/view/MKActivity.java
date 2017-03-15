package com.gdzc.zcdj.mk.view;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.binding.messenger.Messenger;
import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.common.recyclerview.InitRecyclerView;
import com.gdzc.databinding.ActivityCustomBinding;
import com.gdzc.zcdj.mk.viewmodel.MKViewModel;
import com.pulltofresh.PullToRefreshBase;
import com.pulltofresh.extras.RecyclerViewEmptySupport;

/**
 * Created by 王少岩 on 2017/2/8.
 */

public class MKActivity extends BaseActivity<ActivityCustomBinding> {
    private MKViewModel mViewModel;
    private String columName, dwId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom;
    }

    @Override
    protected void initViews() {
        initView();
        initMessenger();
        setListener();
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private void initView() {
        columName = getIntent().getExtras().getString("columName");
        dwId = getIntent().getExtras().getString("dwId");
        mViewModel = new MKViewModel(columName, dwId);
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar(columName, true));
        mBinding.setViewModel(mViewModel);
        InitRecyclerView.initLinearLayoutVERTICAL(this, mBinding.pullView.getRefreshableView());
        mBinding.pullView.getRefreshableView().setAdapter(mViewModel.mAdapter);
    }

    private void initMessenger() {
        Messenger.getDefault().register(this, "complete", () -> mBinding.pullView.onRefreshComplete());
    }

    private void setListener() {
        mBinding.pullView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerViewEmptySupport>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerViewEmptySupport> refreshView) {
                mViewModel.getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerViewEmptySupport> refreshView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meun, menu);
        menu.findItem(R.id.action_right).setTitle("确定");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mViewModel.confirm();
        return super.onOptionsItemSelected(item);
    }
}

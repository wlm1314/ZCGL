package com.gdzc.zcdj.flh.view;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.binding.messenger.Messenger;
import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.common.recyclerview.InitRecyclerView;
import com.gdzc.databinding.ActivityFlhBinding;
import com.gdzc.zcdj.flh.viewmodel.FlhViewModel;
import com.pulltofresh.PullToRefreshBase;
import com.pulltofresh.extras.RecyclerViewEmptySupport;

/**
 * Created by 王少岩 on 2017/2/7.
 */

public class FlhActivity extends BaseActivity<ActivityFlhBinding> {
    private FlhViewModel mFlhViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flh;
    }

    @Override
    protected void initViews() {
        initView();
        setListener();
        initMessenger();
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private void initView() {
        mFlhViewModel = new FlhViewModel();
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("分类号", true));
        mBinding.setViewmodel(mFlhViewModel);
        InitRecyclerView.initLinearLayoutVERTICAL(this, mBinding.pullView.getRefreshableView());
        mBinding.pullView.getRefreshableView().setAdapter(mFlhViewModel.mAdapter);
    }

    private void setListener() {
        mBinding.pullView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerViewEmptySupport>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerViewEmptySupport> refreshView) {
                mFlhViewModel.refresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerViewEmptySupport> refreshView) {
                mFlhViewModel.loadMore();
            }
        });
    }

    private void initMessenger() {
        Messenger.getDefault().register(this, "complete", () -> {
            mBinding.pullView.setMode(PullToRefreshBase.Mode.BOTH);
            mBinding.pullView.onRefreshComplete();
        });
        Messenger.getDefault().register(this, "nomore", () -> {
            mBinding.pullView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            mBinding.pullView.onRefreshComplete();
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
        mFlhViewModel.confirm();
        return super.onOptionsItemSelected(item);
    }
}

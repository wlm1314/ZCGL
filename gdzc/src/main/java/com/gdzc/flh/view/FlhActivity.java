package com.gdzc.flh.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.gdzc.R;
import com.gdzc.base.App;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityFlhBinding;
import com.gdzc.flh.model.FlhBean;
import com.gdzc.flh.viewmodel.FlhViewModel;
import com.gdzc.utils.Utils;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.pulltofresh.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;


/**
 * Created by 王少岩 on 2016/12/20.
 */

public class FlhActivity extends BaseActivity<ActivityFlhBinding> {
    private FlhViewModel mViewModel;
    private LinearLayoutManager layoutManager;
    private BindingAdapter mAdapter;
    private List<FlhBean.Flh> mFlhList = new ArrayList<>();
    private FlhBean.Flh mFlh;
    private int pageNo = 1;
    public String flh = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flh;
    }

    @Override
    protected void setViewModel() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("分类号", true));
        mViewModel = new FlhViewModel();
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        initView();
        setListener();
        mViewModel.getFlh(flh, pageNo++);
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.ptrRv.getRefreshableView().setLayoutManager(layoutManager);
        mBinding.ptrRv.getRefreshableView().setHasFixedSize(true);
        mAdapter = new BindingAdapter(new BindingTool(R.layout.adapter_flh_item, com.gdzc.BR.data), mFlhList);
        mBinding.ptrRv.getRefreshableView().setAdapter(mAdapter);
    }

    private void setListener() {
        mBinding.ptrRv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mViewModel.getFlh(flh, pageNo++);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNo = 1;
                mViewModel.getFlh(flh, pageNo++);
            }
        });
        mAdapter.setOnViewClickListener((view, position) -> {
            Observable.from(mFlhList).subscribe(flh -> flh.checked.set(false));
            mFlh = mFlhList.get(position);
            mFlh.checked.set(true);
        }, R.id.cb_flh);
    }

    public void setFlh(String flh) {
        this.flh = flh;
    }

    public void setFlhList(List<FlhBean.Flh> list) {
        if (pageNo == 1)
            mFlhList.clear();
        mFlhList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meun, menu);
        menu.findItem(R.id.action_right).setTitle("确定");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mFlh == null)
            Utils.showToast(App.getAppContext(), "请选择分类");
        else {
            Intent data = new Intent();
            data.putExtra("Flh", mFlh);
            setResult(RESULT_OK, data);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

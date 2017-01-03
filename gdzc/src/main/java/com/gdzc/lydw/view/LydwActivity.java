package com.gdzc.lydw.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityCustomBinding;
import com.gdzc.lydw.model.LydwBean;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
import com.gdzc.utils.SPUtils;
import com.gdzc.utils.Utils;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.pulltofresh.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by 王少岩 on 2016/12/21.
 */

public class LydwActivity extends BaseActivity<ActivityCustomBinding> {
    private BindingAdapter mAdapter;
    private List<LydwBean.Lydw> mList = new ArrayList<>();
    private LydwBean.Lydw mLydw;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom;
    }

    @Override
    protected void setViewModel() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("领用单位", true));
    }

    @Override
    protected void init() {
        initView();
        setListener();
        getDw();
    }

    private void initView() {
        mBinding.ptrRv.getRefreshableView().setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.ptrRv.getRefreshableView().setHasFixedSize(true);
        mAdapter = new BindingAdapter(new BindingTool(R.layout.adapter_lydw_item, BR.data), mList);
        mBinding.ptrRv.getRefreshableView().setAdapter(mAdapter);
    }

    private void setListener() {
        mBinding.ptrRv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                getDw();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {

            }
        });
        mAdapter.setOnViewClickListener((view, position) -> {
            Observable.from(mList).subscribe(lydw -> lydw.checked.set(false));
            mLydw = mList.get(position);
            mLydw.checked.set(true);
        }, R.id.cb_flh);
    }

    private void getDw() {
        HttpRequest.GetDwList(HttpPostParams.paramGetDwList(SPUtils.getString(SPUtils.kUser_dwbh, "00")))
                .subscribe(new RetrofitSubscriber<>(lydwBean -> {
                    mBinding.ptrRv.onRefreshComplete();
                    mList.clear();
                    mList.addAll(lydwBean.data.list);
                    mAdapter.notifyDataSetChanged();
                }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meun, menu);
        menu.findItem(R.id.action_right).setTitle("确定");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mLydw == null)
            Utils.showToast("请选择单位");
        else {
            Intent data = new Intent();
            data.putExtra("Lydw", mLydw);
            setResult(RESULT_OK, data);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

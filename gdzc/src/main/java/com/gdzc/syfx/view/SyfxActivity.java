package com.gdzc.syfx.view;

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
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
import com.gdzc.syfx.model.SyfxBean;
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

public class SyfxActivity extends BaseActivity<ActivityCustomBinding> {
    private BindingAdapter mAdapter;
    private List<SyfxBean.Syfx> mList = new ArrayList<>();
    private SyfxBean.Syfx mSyfx;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom;
    }

    @Override
    protected void setViewModel() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("使用方向", true));
    }

    @Override
    protected void init() {
        initView();
        setListener();
        getSyfx();
    }

    private void initView() {
        mBinding.ptrRv.getRefreshableView().setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.ptrRv.getRefreshableView().setHasFixedSize(true);
        mAdapter = new BindingAdapter(new BindingTool(R.layout.adapter_syfx_item, BR.data), mList);
        mBinding.ptrRv.getRefreshableView().setAdapter(mAdapter);
    }

    private void setListener() {
        mBinding.ptrRv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                getSyfx();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {

            }
        });
        mAdapter.setOnViewClickListener((view, position) -> {
            Observable.from(mList).subscribe(syfx -> syfx.checked.set(false));
            mSyfx = mList.get(position);
            mSyfx.checked.set(true);
        }, R.id.cb_flh);
    }

    private void getSyfx() {
        HttpRequest.GetMkList(HttpPostParams.paramGetMkList("使用方向", "1"))
                .subscribe(new RetrofitSubscriber<>(syfxBean -> {
                    mBinding.ptrRv.onRefreshComplete();
                    mList.clear();
                    mList.addAll(syfxBean.data.list);
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
        if (mSyfx == null)
            Utils.showToast("请选择使用方向");
        else {
            Intent data = new Intent();
            data.putExtra("Syfx", mSyfx);
            setResult(RESULT_OK, data);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

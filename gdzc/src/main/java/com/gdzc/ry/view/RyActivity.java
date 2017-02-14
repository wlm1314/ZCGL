package com.gdzc.ry.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gdzc.BR;
import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityCustomBinding;
import com.gdzc.net.http.HttpPostParams;
import com.gdzc.net.http.HttpRequest;
import com.gdzc.net.subscribers.RetrofitSubscriber;
import com.gdzc.ry.model.RyBean;
import com.gdzc.utils.Utils;
import com.gdzc.widget.recycleview.BindingAdapter;
import com.gdzc.widget.recycleview.BindingTool;
import com.pulltofresh.PullToRefreshBase;
import com.pulltofresh.extras.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by 王少岩 on 2017/1/20.
 */

public class RyActivity extends BaseActivity<ActivityCustomBinding> {
    private BindingAdapter mAdapter;
    private List<RyBean.Ry> mList = new ArrayList<>();
    private String title, dwid, realName, rybh;
    private RyBean.Ry mRy;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom;
    }

    @Override
    protected void setViewModel() {
        title = getIntent().getExtras().getString("title");
        dwid = getIntent().getExtras().getString("dwid");
        realName = getIntent().getExtras().getString("realName");
        rybh = getIntent().getExtras().getString("rybh");
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar(title, true));
    }@Override
    protected void init() {
        initView();
        setListener();
        getData();
    }

    private void initView() {
        mBinding.ptrRv.getRefreshableView().setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.ptrRv.getRefreshableView().setHasFixedSize(true);
        mAdapter = new BindingAdapter(new BindingTool(R.layout.adapter_ry_item, BR.data), mList);
        mBinding.ptrRv.getRefreshableView().setAdapter(mAdapter);
    }

    private void setListener() {
        mBinding.ptrRv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerViewEmptySupport>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerViewEmptySupport> refreshView) {
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerViewEmptySupport> refreshView) {

            }
        });
        mAdapter.setOnViewClickListener((view, position) -> {
            Observable.from(mList).subscribe(ry -> ry.checked.set(false));
            mRy = mList.get(position);
            mRy.checked.set(true);
        }, R.id.cb_flh);
    }

    private void getData() {
        HttpRequest.GetRy(HttpPostParams.paramgetRyk(dwid, realName, rybh))
                .subscribe(new RetrofitSubscriber<>(cfdBean -> {
                    mBinding.ptrRv.onRefreshComplete();
                    mList.clear();
                    mList.addAll(cfdBean.data.list);
                    mAdapter.notifyDataSetChanged();
                    if (mList.size() > 0)
                        mBinding.emptyView.setVisibility(View.GONE);
                    else
                        mBinding.emptyView.setVisibility(View.VISIBLE);
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
        if (mRy == null)
            Utils.showToast("请选择" + title);
        else {
            Intent data = new Intent();
            data.putExtra("data", mRy);
            setResult(RESULT_OK, data);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.gdzc.flh.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.gdzc.R;
import com.gdzc.base.AppBar;
import com.gdzc.base.BaseActivity;
import com.gdzc.databinding.ActivityFlhBinding;
import com.gdzc.flh.model.FlhBean;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
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
    private BindingAdapter mAdapter;
    private List<FlhBean.Flh> mFlhList = new ArrayList<>();
    private FlhBean.Flh mFlh;
    private int pageNo = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flh;
    }

    @Override
    protected void setViewModel() {
        setSupportActionBar((Toolbar) mBinding.layoutAppbar.getRoot().findViewById(R.id.toolbar));
        mBinding.setAppbar(new AppBar("分类号", true));
    }

    @Override
    protected void init() {
        initView();
        setListener();
        getFlh(pageNo++);
    }

    private void initView() {
        mBinding.ptrRv.getRefreshableView().setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.ptrRv.getRefreshableView().setHasFixedSize(true);
        mAdapter = new BindingAdapter(new BindingTool(R.layout.adapter_flh_item, com.gdzc.BR.data), mFlhList);
        mBinding.ptrRv.getRefreshableView().setAdapter(mAdapter);
    }

    private void setListener() {
        mBinding.ptrRv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNo = 1;
                getFlh(pageNo++);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                getFlh(pageNo++);
            }
        });
        mAdapter.setOnViewClickListener((view, position) -> {
            Observable.from(mFlhList).subscribe(flh -> flh.checked.set(false));
            mFlh = mFlhList.get(position);
            mFlh.checked.set(true);
        }, R.id.cb_flh);

        mBinding.tvSearch.setOnClickListener(v -> {
            pageNo = 1;
            getFlh(pageNo++);
        });
    }


    public void getFlh(int pageNo) {
        HttpRequest.GetFlh(HttpPostParams.paramGetFlh(mBinding.etFlh.getText().toString(), "", pageNo + "", "10"))
                .subscribe(new RetrofitSubscriber<>(
                        flhBean -> {
                            mBinding.ptrRv.onRefreshComplete();
                            if (flhBean.data.isLastPage)
                                mBinding.ptrRv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            else
                                mBinding.ptrRv.setMode(PullToRefreshBase.Mode.BOTH);
                            if (flhBean.data.isFirstPage)
                                mFlhList.clear();
                            mFlhList.addAll(flhBean.data.list);
                            mAdapter.notifyDataSetChanged();
                        }
                ));
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
            Utils.showToast("请选择分类");
        else {
            Intent data = new Intent();
            data.putExtra("Flh", mFlh);
            setResult(RESULT_OK, data);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.gdzc.zcdj.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.gdzc.R;
import com.gdzc.base.BaseFragment;
import com.gdzc.databinding.FragmentZcxgBinding;
import com.gdzc.net.HttpPostParams;
import com.gdzc.net.HttpRequest;
import com.gdzc.net.RetrofitSubscriber;
import com.gdzc.utils.NavigateUtils;
import com.gdzc.utils.Utils;
import com.gdzc.widget.MessageDialog;
import com.gdzc.zcdj.adapter.ZcxgAdapter;
import com.gdzc.zcdj.model.ZcxgBean;
import com.gdzc.zcdj.viewmodel.ZcxgViewModel;
import com.pulltofresh.PullToRefreshBase;
import com.pulltofresh.extras.CustomNestedScrollView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 王少岩 on 2017/1/12.
 */

public class ZcxgFragment extends BaseFragment<FragmentZcxgBinding> {
    private ZcxgAdapter mAdapter;
    private List<ZcxgBean.Zcxg> mList = new ArrayList<>();
    private ZcxgViewModel mViewModel;
    private int pageNo = 1;

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = (swipeLeftMenu, swipeRightMenu, viewType) -> {

        SwipeMenuItem deleteItem = new SwipeMenuItem(getContext())
                .setImage(R.mipmap.icon_delete) // 图标。
                .setText("删除") // 文字。
                .setTextColor(getResources().getColor(R.color.blue_primary)) // 文字颜色。
                .setTextSize(14) // 文字大小。
                .setWidth(200)
                .setHeight(Utils.dip2px(160));
        swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。.

        // 上面的菜单哪边不要菜单就不要添加。
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zcxg;
    }

    @Override
    protected void setViewModel() {
        mViewModel = new ZcxgViewModel(this);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        initView();
        setListener();
    }

    private void initView() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setHasFixedSize(true);
        // 设置菜单创建器。
        mBinding.recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mAdapter = new ZcxgAdapter(mList);
        mAdapter.setDataType("zcxg");
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    private void setListener() {
        mBinding.pullView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<CustomNestedScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<CustomNestedScrollView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<CustomNestedScrollView> refreshView) {
                mViewModel.getData(pageNo++);
            }
        });
        // 设置菜单Item点击监听。
        mBinding.recyclerView.setSwipeMenuItemClickListener((closeable, adapterPosition, menuPosition, direction) -> {
            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                MessageDialog messageDialog = new MessageDialog(getActivity(), "确认删除吗？");
                messageDialog.setTitle("提示");
                messageDialog.setDialogFinishListener(new MessageDialog.OnDialogFinishListener() {
                    @Override
                    public void onFinish() {
                        HttpRequest.DeleteZjById(HttpPostParams.paramDeleteZjById(mList.get(adapterPosition).id))
                                .subscribe(new RetrofitSubscriber<>(baseBean -> {
                                    if (baseBean.status.isSuccess()) {
                                        messageDialog.dismiss();
                                        Utils.showToast("删除成功");
                                        mList.remove(adapterPosition);
                                        mAdapter.notifyItemRemoved(adapterPosition);
                                    }
                                }));
                    }

                    @Override
                    public void onCancel() {
                        messageDialog.dismiss();
                    }
                });
                messageDialog.show();
            }
        });
        mAdapter.setOnItemClickListener(position -> startZcdjEditActivity(mList.get(position)));
    }

    private void startZcdjEditActivity(ZcxgBean.Zcxg zcxg) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("zcxg", zcxg);
        NavigateUtils.startActivityForResult(getActivity(), ZcdjEditActivity.class, 1000, bundle);
    }

    public void setData(ZcxgBean.DataBean data) {
        if (data.isFirstPage)
            mList.clear();
        if (data.isLastPage)
            mBinding.pullView.setMode(PullToRefreshBase.Mode.DISABLED);
        else
            mBinding.pullView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mList.addAll(data.list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == 1000) {
            boolean flag = data.getExtras().getBoolean("update");
            if (flag)
                mViewModel.getData(pageNo);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
